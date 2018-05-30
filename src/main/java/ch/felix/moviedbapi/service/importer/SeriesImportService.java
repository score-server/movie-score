package ch.felix.moviedbapi.service.importer;

import ch.felix.moviedbapi.data.entity.Episode;
import ch.felix.moviedbapi.data.entity.Season;
import ch.felix.moviedbapi.data.entity.Serie;
import ch.felix.moviedbapi.data.repository.EpisodeRepository;
import ch.felix.moviedbapi.data.repository.SeasonRepository;
import ch.felix.moviedbapi.data.repository.SerieRepository;
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

    protected SeriesImportService(SettingsService settingsService, SerieRepository serieRepository,
                                  SeasonRepository seasonRepository, EpisodeRepository episodeRepository) {
        super(settingsService);
        this.serieRepository = serieRepository;
        this.seasonRepository = seasonRepository;
        this.episodeRepository = episodeRepository;
    }

    @Override
    public void filterFile(File movieFile) {
        String seriesName = movieFile.getName().replace(".mp4", "");

        Serie serie;

        try {
            serie = serieRepository.findSerieByTitle(getName(seriesName));
            serie.getId();
        } catch (NullPointerException e) {
            serie = new Serie();
            serie.setTitle(getName(seriesName));
//            serie.setDescript();
//            serie.setCaseImg();
//            serie.setPopularity();
            serieRepository.save(serie);

        }


        Season season;
        try {
            season = seasonRepository.findSeasonBySerieFkAndSeason(
                    serieRepository.findSerieByTitle(seriesName).getId(), Integer.valueOf(getSeason(seriesName)));
            season.getId();
        } catch (NullPointerException e) {
            season = new Season();
            season.setSerieFk(serieRepository.findSerieByTitle(getName(seriesName)).getId());
            season.setSeason(Integer.valueOf(getSeason(seriesName)));
            season.setYear(getYear(seriesName));
            seasonRepository.save(season);
        }

        Season season1 = seasonRepository.findSeasonBySerieFkAndSeason(
                serieRepository.findSerieByTitle(getName(seriesName)).getId(),
                Integer.valueOf(getSeason(seriesName)));
        Episode episode;
        try {

            episode = episodeRepository.findEpisodesBySeasonFkAndEpisode(season1.getId(),
                    Integer.valueOf(getEpisode(seriesName))).get(0);
            episode.getId();
        } catch (NullPointerException | IndexOutOfBoundsException e) {
            episode = new Episode();
            episode.setSeasonFk(season1.getId());
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
        return getEpisodeStr(s).substring(1, 2);
    }

    private String getEpisode(String s) {
        return getEpisodeStr(s).substring(3, 4);
    }

}
