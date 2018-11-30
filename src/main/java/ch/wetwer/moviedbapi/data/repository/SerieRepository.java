package ch.wetwer.moviedbapi.data.repository;

import ch.wetwer.moviedbapi.data.entity.Serie;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author Wetwer
 * @project movie-db
 */
@Repository
public interface SerieRepository extends JpaRepository<Serie, Long> {

    Serie findSerieById(Long serieId);

    Serie findSerieByTitle(String title);

    List<Serie> findSeriesByTitleContainingOrderByPopularityDesc(String search);

    List<Serie> findTop24ByTitleContainingOrderByPopularityDesc(String search);

    List<Serie> findSerieByOrderByTitle();

}
