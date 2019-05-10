package ch.wetwer.moviedbapi.data.episode;

import ch.wetwer.moviedbapi.data.DaoInterface;
import ch.wetwer.moviedbapi.data.season.Season;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author Wetwer
 * @project movie-score
 */

@Service
public class EpisodeDao implements DaoInterface<Episode> {

    private EpisodeRepository episodeRepository;

    public EpisodeDao(EpisodeRepository episodeRepository) {
        this.episodeRepository = episodeRepository;
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
}
