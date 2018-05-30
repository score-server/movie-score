package ch.felix.moviedbapi.data.repository;

import ch.felix.moviedbapi.data.entity.Season;
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
public interface SeasonRepository extends JpaRepository<Season, Long> {

    Season findSeasonById(Long seasonId);

    Season findSeasonBySerieFkAndSeason(Long seriesFk, Integer season);

    List<Season> findSeasonsBySerieFk(Long seriesFk);

}
