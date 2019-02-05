package ch.wetwer.moviedbapi.data.episode;

import ch.wetwer.moviedbapi.data.comment.Comment;
import ch.wetwer.moviedbapi.data.season.Season;
import ch.wetwer.moviedbapi.data.subtitle.Subtitle;
import ch.wetwer.moviedbapi.data.time.Time;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import java.util.List;

/**
 * @author Wetwer
 * @project movie-db
 */
@Data
@Entity
public class Episode {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JsonIgnore
    @ManyToOne
    private Season season;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "episode")
    private List<Comment> comments;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "episode")
    private List<Time> times;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "episode")
    private List<Subtitle> subtitles;

    private String path;
    private String quality;
    private Integer episode;

    public String getMime() {
        if (path.endsWith(".mkv")) {
            return "video/x-matroska";
        } else if (path.endsWith(".mp4")) {
            return "video/mp4";
        } else if (path.endsWith(".avi")) {
            return "video/x-msvideo";
        } else {
            return "";
        }
    }

    public String getFullTitle() {
        return season.getSerie().getTitle() + " S" + season.getSeason() + "E" + getEpisode();
    }
}
