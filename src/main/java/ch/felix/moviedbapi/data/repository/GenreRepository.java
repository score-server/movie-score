package ch.felix.moviedbapi.data.repository;

import ch.felix.moviedbapi.data.entity.Genre;
import ch.felix.moviedbapi.data.entity.Serie;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author Wetwer
 * @project movie-db
 */
@Repository
public interface GenreRepository extends JpaRepository<Genre, Long> {

    List<Genre> findGenresByName(String name);

    List<Genre> findAllByOrderByName();

}
