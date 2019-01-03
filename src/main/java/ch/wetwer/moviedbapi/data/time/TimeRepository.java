package ch.wetwer.moviedbapi.data.time;

import ch.wetwer.moviedbapi.data.user.User;
import ch.wetwer.moviedbapi.data.episode.Episode;
import ch.wetwer.moviedbapi.data.movie.Movie;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TimeRepository extends JpaRepository<Time, Long> {

    Time findTimeByUserAndMovie(User user, Movie movie);

    Time findTimeByUserAndEpisode(User user, Episode episode);

    List<Time> findTimesByUserOrderByTimestampDesc(User user);

}
