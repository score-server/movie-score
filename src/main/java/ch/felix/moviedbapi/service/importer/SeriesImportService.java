package ch.felix.moviedbapi.service.importer;

import ch.felix.moviedbapi.data.entity.Episode;
import ch.felix.moviedbapi.data.entity.Season;
import ch.felix.moviedbapi.data.entity.Serie;
import ch.felix.moviedbapi.data.repository.EpisodeRepository;
import ch.felix.moviedbapi.data.repository.SeasonRepository;
import ch.felix.moviedbapi.data.repository.SerieRepository;
import ch.felix.moviedbapi.jsonmodel.tmdb.SerieJson;
import ch.felix.moviedbapi.service.ImportLogService;
import ch.felix.moviedbapi.service.SettingsService;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.List;

@Service
public class SeriesImportService extends ImportServiceFactory {

    private SerieRepository serieRepository;
    private EpisodeRepository episodeRepository;
    private SeasonRepository seasonRepository;

    private SettingsService settingsService;
    private SearchMovieService searchMovieService;
    private ImportLogService importLogService;
    private GenreImportService genreImportService;

    protected SeriesImportService(SerieRepository serieRepository, EpisodeRepository episodeRepository, SeasonRepository seasonRepository, SettingsService settingsService,
                                  SearchMovieService searchMovieService, ImportLogService importLogService, GenreImportService genreImportService) {
        super(settingsService);
        this.serieRepository = serieRepository;
        this.episodeRepository = episodeRepository;
        this.seasonRepository = seasonRepository;
        this.settingsService = settingsService;
        this.searchMovieService = searchMovieService;
        this.importLogService = importLogService;
        this.genreImportService = genreImportService;
    }

    @Override
    public void importAll() {
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
    }

    @Override
    public void updateAll() {
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
            int serieId = searchMovieService.findSeriesId(getName(seriesName), getYear(seriesName));
            SerieJson serieJson = searchMovieService.getSerieInfo(serieId);
            sleep(250);
            try {
                serie.setDescript(serieJson.getOverview());
                serie.setCaseImg("https://image.tmdb.org/t/p/original" + serieJson.getPosterPath());
                serie.setBackgroundImg("https://image.tmdb.org/t/p/original" + serieJson.getBackdropPath());
                serie.setVoteAverage(serieJson.getVoteAverage());
                serie.setPopularity(serieJson.getPopularity());
            } catch (NullPointerException e) {
                e.printStackTrace();
            }
            importLogService.importLog("Saved Series: " + getName(seriesName));
            serieRepository.save(serie);
            try {
                genreImportService.setGenre(serieRepository.findSerieByTitle(getName(seriesName)),
                        serieJson.getGenres());
                sleep(250);
            } catch (NullPointerException e) {
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
            importLogService.importLog("Saved Season: Season " + getSeason(seriesName));
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
            importLogService.importLog("Saved Episode: " + getName(seriesName) + " Season " + getSeason(seriesName)
                    + " Episode " + getEpisode(seriesName));
        }
    }

    @Override
    public void updateFile(File file) {

    }

    public void updateFile(Serie serie) {
        int serieId = searchMovieService.findSeriesId(serie.getTitle());
        SerieJson serieJson = searchMovieService.getSerieInfo(serieId);

        try {
            serie.setDescript(serieJson.getOverview());
            serie.setVoteAverage(serieJson.getVoteAverage());
            serie.setPopularity(serieJson.getPopularity());
        } catch (NullPointerException e) {
            e.printStackTrace();
            importLogService.errorLog("No json found for " + serie.getTitle());
        }

        importLogService.importLog("Updated Series: " + serie.getTitle());
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
