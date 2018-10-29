package ch.felix.moviedbapi.data.repository;

import ch.felix.moviedbapi.data.entity.Episode;
import ch.felix.moviedbapi.data.entity.Movie;
import ch.felix.moviedbapi.data.entity.Time;
import ch.felix.moviedbapi.data.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TimeRepository extends JpaRepository<Time, Long> {

    Time findTimeByUserAndMovie(User user, Movie movie);

    Time findTimeByUserAndEpisode(User user, Episode episode);

    List<Time> findTimesByUserOrderByTimestampDesc(User user);

}
