package ch.felix.moviedbapi.data.repository;

import ch.felix.moviedbapi.data.entity.Movie;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MovieRepository extends JpaRepository<Movie, Long> {

    Movie findMovieById(Long id);

    Movie findMoviesByTitle(String title);

    List<Movie> findMoviesByTitleContaining(String title);

}

