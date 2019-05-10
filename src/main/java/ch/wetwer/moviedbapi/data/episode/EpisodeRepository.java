package ch.wetwer.moviedbapi.data.episode;

import ch.wetwer.moviedbapi.data.season.Season;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author Wetwer
 * @project movie-score
 */
@Repository
public interface EpisodeRepository extends JpaRepository<Episode, Long> {

    List<Episode> findEpisodesBySeasonOrderByEpisode(Season season);

    Episode findEpisodeById(Long episodeId);

    Episode findEpisodeBySeasonAndEpisode(Season season, Integer episode);

    List<Episode> findAllByOrderByConvertPercentageDesc();

}
