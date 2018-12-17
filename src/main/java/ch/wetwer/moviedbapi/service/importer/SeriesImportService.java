package ch.wetwer.moviedbapi.service.importer;

import ch.wetwer.moviedbapi.data.dao.UpdateLogDao;
import ch.wetwer.moviedbapi.data.entity.Episode;
import ch.wetwer.moviedbapi.data.entity.Season;
import ch.wetwer.moviedbapi.data.entity.Serie;
import ch.wetwer.moviedbapi.data.entity.UpdateLog;
import ch.wetwer.moviedbapi.data.repository.EpisodeRepository;
import ch.wetwer.moviedbapi.data.repository.SeasonRepository;
import ch.wetwer.moviedbapi.data.repository.SerieRepository;
import ch.wetwer.moviedbapi.model.tmdb.SerieJson;
import ch.wetwer.moviedbapi.service.ImportLogService;
import ch.wetwer.moviedbapi.service.SettingsService;
import ch.wetwer.moviedbapi.service.auth.UserAuthService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.List;

@Service
public class SeriesImportService extends ImportServiceFactory {

    final Logger LOG = LoggerFactory.getLogger(UserAuthService.class);
    private SerieRepository serieRepository;
    private EpisodeRepository episodeRepository;
    private SeasonRepository seasonRepository;
    private SettingsService settingsService;
    private SearchMovieService searchMovieService;
    private ImportLogService importLogService;
    private GenreImportService genreImportService;
    private UpdateLogDao updateLogDao;

    protected SeriesImportService(SerieRepository serieRepository, EpisodeRepository episodeRepository,
                                  SeasonRepository seasonRepository, SettingsService settingsService,
                                  SearchMovieService searchMovieService, ImportLogService importLogService,
                                  GenreImportService genreImportService, UpdateLogDao updateLogDao) {
        super(settingsService);
        this.serieRepository = serieRepository;
        this.episodeRepository = episodeRepository;
        this.seasonRepository = seasonRepository;
        this.settingsService = settingsService;
        this.searchMovieService = searchMovieService;
        this.importLogService = importLogService;
        this.genreImportService = genreImportService;
        this.updateLogDao = updateLogDao;
    }

    @Override
    public void importAll() {
        UpdateLog updateLog = updateLogDao.addLog("SI");
        startImport();
        File file = new File(settingsService.getKey("seriePath"));

        File[] files = file.listFiles();
        int currentFileIndex = 0;
        int allFiles = files.length;


        for (File movieFile : files) {
            currentFileIndex++;
            settingsService.setValue("importProgress",
                    String.valueOf(round(getPercent(currentFileIndex, allFiles), 1)));
            if (movieFile.getName().endsWith(".mp4")
                    || movieFile.getName().endsWith(".avi")
                    || movieFile.getName().endsWith(".mkv")) {
                importFile(movieFile);
            }
        }
        stopImport();
        updateLogDao.completeLog(updateLog);
    }

    @Override
    public void updateAll() {
        UpdateLog updateLog = updateLogDao.addLog("SU");
        startImport();
        List<Serie> serieList = serieRepository.findSerieByOrderByTitle();

        int currentFileIndex = 0;
        int allFiles = serieList.size();

        for (Serie serie : serieList) {
            updateFile(serie);
            setImportProgress(getPercent(currentFileIndex, allFiles));
            currentFileIndex++;
        }
        stopImport();
        updateLogDao.completeLog(updateLog);
    }

    @Override
    public void importFile(File file) {
        String seriesName = file.getName()
                .replace(".mp4", "")
                .replace(".avi", "")
                .replace(".mkv", "");


        Serie serie = serieRepository.findSerieByTitle(getName(seriesName));

        if (serie == null) {
            serie = new Serie();
            serie.setTitle(getName(seriesName));
            serie.setTmdbId(searchMovieService.findSeriesId(getName(seriesName), getYear(seriesName)));
            SerieJson serieJson = searchMovieService.getSerieInfo(serie.getTmdbId());
            sleep(250);
            try {
                serie.setDescript(serieJson.getOverview());
                serie.setCaseImg("https://image.tmdb.org/t/p/original" + serieJson.getPosterPath());
                serie.setBackgroundImg("https://image.tmdb.org/t/p/original" + serieJson.getBackdropPath());
                serie.setVoteAverage(serieJson.getVoteAverage());
                serie.setPopularity(serieJson.getPopularity());
            } catch (NullPointerException e) {
                LOG.error(seriesName);
                e.printStackTrace();
            }
            importLogService.importLog("<i class=\"fas fa-angle-double-down\" style=\"color: green;\"></i> " +
                    "Saved Series: " + getName(seriesName));
            serieRepository.save(serie);
            try {
                genreImportService.setGenre(serieRepository.findSerieByTitle(getName(seriesName)),
                        serieJson.getGenres());
                sleep(250);
            } catch (NullPointerException e) {
                LOG.error(seriesName);
                e.printStackTrace();
            }
        }

        Season season = seasonRepository.findSeasonBySerieAndSeason(
                serieRepository.findSerieByTitle(getName(seriesName)),
                Integer.valueOf(getSeason(seriesName)));
        if (season == null) {
            season = new Season();
            season.setSerie(serieRepository.findSerieByTitle(getName(seriesName)));
            season.setSeason(Integer.valueOf(getSeason(seriesName)));
            season.setYear(getYear(seriesName));
            importLogService.importLog("<i class=\"fas fa-angle-double-down\" style=\"color: green;\"></i> " +
                    "Saved Season: Season " + getSeason(seriesName));
            seasonRepository.save(season);
        }


        Episode episode = episodeRepository.findEpisodeBySeasonAndEpisode(
                seasonRepository.findSeasonBySerieAndSeason(
                        serieRepository.findSerieByTitle(getName(seriesName)), Integer.valueOf(getSeason(seriesName))),
                Integer.valueOf(getEpisode(seriesName)));

        if (episode == null) {
            episode = new Episode();
            episode.setSeason(seasonRepository.findSeasonById(season.getId()));
            episode.setPath(file.getPath());
            episode.setEpisode(Integer.valueOf(getEpisode(seriesName)));
            episode.setQuality(getQuality(seriesName));
            episodeRepository.save(episode);
            importLogService.importLog("<i class=\"fas fa-angle-double-down\" style=\"color: green;\"></i> " +
                    "Saved Episode: " + getName(seriesName) + " Season " + getSeason(seriesName)
                    + " Episode " + getEpisode(seriesName));
        }
    }

    @Override
    public void updateFile(File file) {

    }

    private void updateFile(Serie serie) {
        if (serie.getTmdbId() == null) {
            serie.setTmdbId(searchMovieService.findSeriesId(serie.getTitle()));
        }
        SerieJson serieJson = searchMovieService.getSerieInfo(serie.getTmdbId());

        try {
            serie.setDescript(serieJson.getOverview());
            serie.setVoteAverage(serieJson.getVoteAverage());
            serie.setPopularity(serieJson.getPopularity());
        } catch (NullPointerException e) {
            LOG.error(serie.getTitle());
            e.printStackTrace();
            importLogService.errorLog("<i class=\"fas fa-times\" style=\"color: red;\"></i> " +
                    "No json found for " + serie.getTitle());
        }

        importLogService.importLog("<i class=\"fas fa-angle-down\" style=\"color: blue;\"></i> " +
                "Updated Series: " + serie.getTitle());
        serieRepository.save(serie);
        sleep(300);
    }

    private String getName(String fileName) {
        return fileName.replace(" " + getEpisodeStr(fileName) + " " + getYear(fileName) + " "
                + getQuality(fileName), "");
    }

    private String getEpisodeStr(String s) {
        String[] splits = s.split(" ");
        return splits[splits.length - 3];
    }

    private String getQuality(String s) {
        String[] splits = s.split(" ");
        return splits[splits.length - 1];
    }

    private String getYear(String s) {
        String[] splits = s.split(" ");
        return splits[splits.length - 2];
    }

    private String getSeason(String s) {
        return getEpisodeStr(s).toLowerCase().split("e")[0].replace("s", "");
    }

    private String getEpisode(String s) {
        return getEpisodeStr(s).toLowerCase().split("e")[1];
    }

}
