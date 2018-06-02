package ch.felix.moviedbapi.data.repository;

import ch.felix.moviedbapi.data.entity.Episode;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author Felix
 * @date 30.05.2018
 * <p>
 * Project: movie-db-api
 * Package: ch.felix.moviedbapi.data.repository
 **/
@Repository
public interface EpisodeRepository extends JpaRepository<Episode, Long> {

    List<Episode> findEpisodesBySeasonFk(Long seasonId);

    Episode findEpisodeById(Long episodeId);

    Episode findEpisodeBySeasonFkAndEpisode(Long seasonFk, Integer episode);

}
