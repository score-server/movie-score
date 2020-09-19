package ch.wetwer.moviedbapi.data.subtitle;

import ch.wetwer.moviedbapi.data.episode.Episode;
import ch.wetwer.moviedbapi.data.movie.Movie;
import ch.wetwer.moviedbapi.data.user.User;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.persistence.*;

/**
 * @author Wetwer
 * @project movie-score
 * @package ch.wetwer.moviedbapi.data.entity
 * @created 26.11.2018
 **/

@Data
@Entity
public class Subtitle {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String language;

    @Lob
    @JsonIgnore
    private byte[] file;

    @ManyToOne
    private Movie movie;

    @ManyToOne
    private Episode episode;

    @ManyToOne
    private User user;

    public String getLanguage() {
        switch (this.language.toLowerCase()) {
            case "en":
                return "English";
            case "de":
                return "Deutsch";
            case "fr":
                return "French";
            case "es":
                return "Spanish";
            default:
                return "Unknown";
        }
    }
}
