package ch.wetwer.moviedbapi.data.episode;

import ch.wetwer.moviedbapi.data.DaoInterface;
import ch.wetwer.moviedbapi.data.season.Season;
import ch.wetwer.moviedbapi.data.serie.Serie;
import ch.wetwer.moviedbapi.data.serie.SerieDao;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Wetwer
 * @project movie-score
 */

@Service
public class EpisodeDao implements DaoInterface<Episode> {

    private EpisodeRepository episodeRepository;

    private SerieDao serieDao;

    public EpisodeDao(EpisodeRepository episodeRepository, SerieDao serieDao) {
        this.episodeRepository = episodeRepository;
        this.serieDao = serieDao;
    }

    @Override
    public Episode getById(Long id) {
        return episodeRepository.findEpisodeById(id);
    }

    @Override
    public List<Episode> getAll() {
        return episodeRepository.findAll();
    }

    @Override
    public void save(Episode episode) {
        episodeRepository.save(episode);
    }

    public List<Episode> getBySeason(Season season) {
        return episodeRepository.findEpisodesBySeasonOrderByEpisode(season);
    }

    public List<Episode> getOrderByPercentage() {
        return episodeRepository.findAllByOrderByConvertPercentageDesc();
    }

    public List<Episode> getEpisodesToConvert() {
        List<Episode> episodesToConvert = new ArrayList<>();

        for (Serie serie : serieDao.getAllOrderByName()) {
            for (Season season : serie.getSeasons()) {
                for (Episode episode : season.getEpisodes()) {
                    if (episode.getConvertPercentage() == null) {
                        if (episode.getMime().equals("video/x-matroska")
                                || episode.getMime().equals("video/x-msvideo")) {
                            episodesToConvert.add(episode);
                        }
                    }
                }
            }
        }
        return episodesToConvert;
    }

    public List<Episode> getEpisodesCurrentlyConverting() {
        List<Episode> episodeList = new ArrayList<>();
        for (Episode episode : episodeRepository.findAllByOrderByConvertPercentageDesc()) {
            if (episode.getConvertPercentage() != null) {
                episodeList.add(episode);
            }
        }
        return episodeList;
    }

}
