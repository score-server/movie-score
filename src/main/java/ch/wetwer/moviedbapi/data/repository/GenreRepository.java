package ch.wetwer.moviedbapi.data.repository;

import ch.wetwer.moviedbapi.data.entity.Genre;
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

    List<Genre> findGenreByOrderByName();

    List<Genre> findGenresByMovieNotNullOrderByName();

    List<Genre> findGenresBySerieNotNullOrderByName();

}
