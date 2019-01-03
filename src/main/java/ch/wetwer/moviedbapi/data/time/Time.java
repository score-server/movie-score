package ch.wetwer.moviedbapi.data.time;

import ch.wetwer.moviedbapi.data.user.User;
import ch.wetwer.moviedbapi.data.episode.Episode;
import ch.wetwer.moviedbapi.data.movie.Movie;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import java.sql.Timestamp;

@Data
@Entity
public class Time {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private User user;

    @ManyToOne
    private Movie movie;

    @ManyToOne
    private Episode episode;

    private Float time;
    private Timestamp timestamp;

}
