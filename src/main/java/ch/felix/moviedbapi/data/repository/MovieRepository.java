package ch.felix.moviedbapi.data.repository;

import ch.felix.moviedbapi.data.entity.Movie;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author Wetwer
 * @project movie-db
 */
@Repository
public interface MovieRepository extends JpaRepository<Movie, Long> {

    Movie findMovieById(Long id);

    Movie findMovieByTitle(String title);

    List<Movie> findTop24ByTitleContainingOrderByTitle(String title);

    List<Movie> findTop24ByTitleContainingOrderByVoteAverageDesc(String title);

    List<Movie> findTop24ByTitleContainingOrderByYearDesc(String title);

    List<Movie> findMoviesByTitleContainingOrderByTitle(String title);

    List<Movie> findMoviesByTitleContainingOrderByVoteAverageDesc(String title);

    List<Movie> findMoviesByTitleContainingOrderByYearDesc(String title);

}

