package ch.wetwer.moviedbapi.data.genre;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author Wetwer
 * @project movie-score
 */
@Repository
public interface GenreRepository extends JpaRepository<Genre, Long> {

    List<Genre> findGenresByName(String name);

    List<Genre> findGenreByOrderByName();

    List<Genre> findGenresByMovieNotNullOrderByName();

    List<Genre> findGenresBySerieNotNullOrderByName();

}
