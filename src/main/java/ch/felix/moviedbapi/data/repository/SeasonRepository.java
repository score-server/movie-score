package ch.felix.moviedbapi.data.repository;

import ch.felix.moviedbapi.data.entity.Season;
import ch.felix.moviedbapi.data.entity.Serie;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author Wetwer
 * @project movie-db
 */
@Repository
public interface SeasonRepository extends JpaRepository<Season, Long> {

    Season findSeasonById(Long seasonId);

    Season findSeasonBySerieAndSeason(Serie serie, Integer season);

    List<Season> findSeasonsBySerieOrderBySeason(Serie serie);

}
