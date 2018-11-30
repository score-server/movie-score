package ch.wetwer.moviedbapi.data.repository;

import ch.wetwer.moviedbapi.data.entity.Episode;
import ch.wetwer.moviedbapi.data.entity.Season;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author Wetwer
 * @project movie-db
 */
@Repository
public interface EpisodeRepository extends JpaRepository<Episode, Long> {

    List<Episode> findEpisodesBySeasonOrderByEpisode(Season season);

    Episode findEpisodeById(Long episodeId);

    Episode findEpisodeBySeasonAndEpisode(Season season, Integer episode);

}
