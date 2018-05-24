package ch.felix.moviedbapi.data.repository;

import ch.felix.moviedbapi.data.entity.Movie;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MovieRepository extends JpaRepository<Movie, Long> {

    Movie findMovieById(Long id);

    List<Movie> findMoviesByTitleContaining(String title);

}

