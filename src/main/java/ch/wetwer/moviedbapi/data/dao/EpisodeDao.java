package ch.wetwer.moviedbapi.data.dao;

import ch.wetwer.moviedbapi.data.entity.Episode;
import ch.wetwer.moviedbapi.data.entity.Season;
import ch.wetwer.moviedbapi.data.repository.EpisodeRepository;
import org.springframework.stereotype.Service;

import java.util.List;

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
}
