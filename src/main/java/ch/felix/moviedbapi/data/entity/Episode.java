package ch.felix.moviedbapi.data.entity;

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

    private String path;
    private String quality;
    private Integer episode;

    public String getMime() {
        if (path.endsWith(".mkv")) {
            return "video/x-matroska; codecs=\"a_ac3, avc\"";
        } else if (path.endsWith(".mp4")) {
            return "video/mp4";
        } else {
            return "";
        }
    }

}
