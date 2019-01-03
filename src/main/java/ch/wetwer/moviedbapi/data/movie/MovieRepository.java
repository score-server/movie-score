package ch.wetwer.moviedbapi.data.movie;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author Wetwer
 * @project movie-db
 */
@Repository
public interface MovieRepository extends JpaRepository<Movie, Long> {

    Movie findMovieById(Long id);

    Movie findMovieByTitle(String title);

    List<Movie> findMoviesByTitleContainingOrderByTitle(String title);

    List<Movie> findTop24ByTitleContainingOrderByTitle(String title);

    List<Movie> findMoviesByTitleContainingOrderByYearDesc(String title);

    List<Movie> findTop24ByTitleContainingOrderByYearDesc(String title);

    List<Movie> findTop24ByTitleContainingOrderByTimestampDesc(String title);

    List<Movie> findMoviesByTitleContainingOrderByVoteAverageDesc(String title);

    List<Movie> findTop24ByTitleContainingOrderByVoteAverageDesc(String title);

    List<Movie> findMoviesByTitleContainingOrderByPopularityDesc(String title);

    List<Movie> findTop10ByTitleContainingOrderByPopularityDesc(String title);

    List<Movie> findTop24ByTitleContainingOrderByPopularityDesc(String title);

    List<Movie> findMoviesByOrderByTitle();

    List<Movie> findTop3ByOrderByTimestampDesc();

}

