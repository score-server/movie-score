package ch.felix.moviedbapi.data.repository;

import ch.felix.moviedbapi.data.entity.Episode;
import ch.felix.moviedbapi.data.entity.Season;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author Wetwer
 * @project movie-db
 */
@Repository
public interface EpisodeRepository extends JpaRepository<Episode, Long> {

    List<Episode> findEpisodesBySeason(Season season);

    Episode findEpisodeById(Long episodeId);

    Episode findEpisodeBySeasonAndEpisode(Season season, Integer episode);

}
