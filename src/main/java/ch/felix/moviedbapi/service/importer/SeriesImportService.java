package ch.felix.moviedbapi.service.importer;

import ch.felix.moviedbapi.data.entity.Episode;
import ch.felix.moviedbapi.data.entity.Season;
import ch.felix.moviedbapi.data.entity.Serie;
import ch.felix.moviedbapi.data.repository.EpisodeRepository;
import ch.felix.moviedbapi.data.repository.SeasonRepository;
import ch.felix.moviedbapi.data.repository.SerieRepository;
import ch.felix.moviedbapi.jsonmodel.SerieJson;
import ch.felix.moviedbapi.service.SettingsService;

import java.io.File;

import org.springframework.stereotype.Service;

/**
 * @author Felix
 * @date 30.05.2018
 * <p>
 * Project: movie-db-api
 * Package: ch.felix.moviedbapi.service.importer
 **/

@Service
public class SeriesImportService extends ImportService {

    private SerieRepository serieRepository;
    private SeasonRepository seasonRepository;
    private EpisodeRepository episodeRepository;

    private GenreImportService genreImportService;

    protected SeriesImportService(SettingsService settingsService, SerieRepository serieRepository,
                                  SeasonRepository seasonRepository, EpisodeRepository episodeRepository,
                                  GenreImportService genreImportService) {
        super(settingsService);
        this.serieRepository = serieRepository;
        this.seasonRepository = seasonRepository;
        this.episodeRepository = episodeRepository;
        this.genreImportService = genreImportService;
    }

    @Override
    public void filterFile(File movieFile) {
        String seriesName = movieFile.getName().replace(".mp4", "");

        SearchMovieService searchMovieService = new SearchMovieService();
        int serieId = searchMovieService.findSeriesId(getName(seriesName), getYear(seriesName));
        SerieJson serieJson = searchMovieService.getSerieInfo(serieId);


        Serie serie = serieRepository.findSerieByTitle(getName(seriesName));
        if (serie == null) {
            serie = new Serie();
            serie.setTitle(getName(seriesName));
            serie.setDescript(serieJson.getOverview());
            serie.setCaseImg(serieJson.getPosterPath());
            serie.setPopularity(serieJson.getPopularity());
            System.out.println("Saved Series: " + seriesName);
            serieRepository.save(serie);
            genreImportService.setGenre(
                    Integer.valueOf(String.valueOf(serieRepository.findSerieByTitle(getName(seriesName)).getId())),
                    serieJson.getGenres());
        }


        Season season = seasonRepository.findSeasonBySerieFkAndSeason(
                serieRepository.findSerieByTitle(getName(seriesName)).getId(),
                Integer.valueOf(getSeason(seriesName)));
        if (season == null) {
            season = new Season();
            season.setSerieFk(serieRepository.findSerieByTitle(getName(seriesName)).getId());
            season.setSeason(Integer.valueOf(getSeason(seriesName)));
            season.setYear(getYear(seriesName));
            System.out.println("Saved Season: " + seriesName);
            seasonRepository.save(season);
        }


        Episode episode = episodeRepository.findEpisodeBySeasonFkAndEpisode(
                seasonRepository.findSeasonBySerieFkAndSeason(
                        serieRepository.findSerieByTitle(getName(seriesName)).getId(),
                        Integer.valueOf(getSeason(seriesName))).getId(),
                Integer.valueOf(getEpisode(seriesName)));

        if (episode == null) {
            episode = new Episode();
            episode.setSeasonFk(season.getId());
            episode.setPath(movieFile.getPath());
            episode.setEpisode(Integer.valueOf(getEpisode(seriesName)));
            episode.setQuality(getQuality(seriesName));
            episodeRepository.save(episode);
            System.out.println("Saved Episode: " + seriesName);
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
        return getEpisodeStr(s).split("e")[0].replace("s", "");
    }

    private String getEpisode(String s) {
        return getEpisodeStr(s).split("e")[1];
    }
}
