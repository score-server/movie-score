package ch.felix.moviedbapi.service.importer;

import ch.felix.moviedbapi.data.entity.Episode;
import ch.felix.moviedbapi.data.entity.Season;
import ch.felix.moviedbapi.data.entity.Serie;
import ch.felix.moviedbapi.data.repository.EpisodeRepository;
import ch.felix.moviedbapi.data.repository.MovieRepository;
import ch.felix.moviedbapi.data.repository.SeasonRepository;
import ch.felix.moviedbapi.data.repository.SerieRepository;
import ch.felix.moviedbapi.jsonmodel.tmdb.SerieJson;
import ch.felix.moviedbapi.service.ImportLogService;
import ch.felix.moviedbapi.service.SettingsService;
import org.springframework.stereotype.Service;

import java.io.File;

/**
 * @author Wetwer
 * @project movie-db
 */

@Service
public class SeriesImportService extends ImportService {

    private SerieRepository serieRepository;
    private SeasonRepository seasonRepository;
    private EpisodeRepository episodeRepository;
    private MovieRepository movieRepository;

    private GenreImportService genreImportService;
    private ImportLogService importLogService;
    private SearchMovieService searchMovieService;

    protected SeriesImportService(SettingsService settingsService, SerieRepository serieRepository,
                                  SeasonRepository seasonRepository, EpisodeRepository episodeRepository,
                                  MovieRepository movieRepository, GenreImportService genreImportService,
                                  ImportLogService importLogService, SearchMovieService searchMovieService) {
        super(settingsService, movieRepository);
        this.serieRepository = serieRepository;
        this.seasonRepository = seasonRepository;
        this.episodeRepository = episodeRepository;
        this.movieRepository = movieRepository;
        this.genreImportService = genreImportService;
        this.importLogService = importLogService;
        this.searchMovieService = searchMovieService;
    }

    @Override
    public void filterFile(File movieFile) {
        String seriesName = movieFile.getName()
                .replace(".mp4", "")
                .replace(".avi", "")
                .replace(".mkv", "");


        Serie serie = serieRepository.findSerieByTitle(getName(seriesName));

        if (serie == null) {
            serie = new Serie();
            serie.setTitle(getName(seriesName));
            int serieId = searchMovieService.findSeriesId(getName(seriesName), getYear(seriesName));
            SerieJson serieJson = searchMovieService.getSerieInfo(serieId);
            sleep();
            try {
                serie.setVoteAverage(serieJson.getVoteAverage());
                serie.setDescript(serieJson.getOverview());
                serie.setCaseImg("https://image.tmdb.org/t/p/original" + serieJson.getPosterPath());
                serie.setBackgroundImg("https://image.tmdb.org/t/p/original" + serieJson.getBackdropPath());
                serie.setPopularity(serieJson.getPopularity());
            } catch (NullPointerException e) {
                e.printStackTrace();
            }
            importLogService.importLog("Saved Series: " + getName(seriesName));
            serieRepository.save(serie);
            try {
                genreImportService.setGenre(serieRepository.findSerieByTitle(getName(seriesName)),
                        serieJson.getGenres());
                sleep();
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
            episode.setPath(movieFile.getPath());
            episode.setEpisode(Integer.valueOf(getEpisode(seriesName)));
            episode.setQuality(getQuality(seriesName));
            episodeRepository.save(episode);
            importLogService.importLog("Saved Episode: " + getName(seriesName) + " Season " + getSeason(seriesName)
                    + " Episode " + getEpisode(seriesName));
        }
    }

    @Override
    public void filterUpdateFile(File movieFile) {
        String seriesName = movieFile.getName()
                .replace(".mp4", "")
                .replace(".avi", "")
                .replace(".mkv", "");

        int serieId = searchMovieService.findSeriesId(getName(seriesName), getYear(seriesName));
        SerieJson serieJson = searchMovieService.getSerieInfo(serieId);

        Serie serie = serieRepository.findSerieByTitle(getName(seriesName));

        try {
            serie.setDescript(serieJson.getOverview());
            serie.setPopularity(serieJson.getPopularity());
        } catch (NullPointerException e) {
            e.printStackTrace();
            importLogService.errorLog("No json found for " + getName(seriesName));
        }

        importLogService.importLog("Updated Series: " + getName(seriesName));
        serieRepository.save(serie);
        sleep();
    }

    private void sleep() {
        try {
            Thread.sleep(300);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
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
