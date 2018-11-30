package ch.wetwer.moviedbapi.data.repository;

import ch.wetwer.moviedbapi.data.entity.Episode;
import ch.wetwer.moviedbapi.data.entity.Movie;
import ch.wetwer.moviedbapi.data.entity.Time;
import ch.wetwer.moviedbapi.data.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TimeRepository extends JpaRepository<Time, Long> {

    Time findTimeByUserAndMovie(User user, Movie movie);

    Time findTimeByUserAndEpisode(User user, Episode episode);

    List<Time> findTimesByUserOrderByTimestampDesc(User user);

}
