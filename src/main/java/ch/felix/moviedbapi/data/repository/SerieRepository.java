package ch.felix.moviedbapi.data.repository;

import ch.felix.moviedbapi.data.entity.Serie;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author Wetwer
 * @project movie-db
 */
@Repository
public interface SerieRepository extends JpaRepository<Serie, Long> {

    Serie findSerieById(Long serieId);

    Serie findSerieByTitle(String title);

    List<Serie> findSeriesByTitleContainingOrderByTitle(String search);

    List<Serie> findTop25ByTitleContainingOrderByTitle(String search);
}
