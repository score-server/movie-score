package ch.felix.moviedbapi.data.repository;

import ch.felix.moviedbapi.data.entity.Serie;
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
public interface SerieRepository extends JpaRepository<Serie, Long> {

    Serie findSerieById(Long serieId);

}
