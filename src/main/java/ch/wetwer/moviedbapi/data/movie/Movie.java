package ch.wetwer.moviedbapi.data.movie;

import ch.wetwer.moviedbapi.data.comment.Comment;
import ch.wetwer.moviedbapi.data.genre.Genre;
import ch.wetwer.moviedbapi.data.importlog.ImportLog;
import ch.wetwer.moviedbapi.data.likes.Likes;
import ch.wetwer.moviedbapi.data.listmovie.ListMovie;
import ch.wetwer.moviedbapi.data.subtitle.Subtitle;
import ch.wetwer.moviedbapi.data.time.Time;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.List;

/**
 * @author Wetwer
 * @project movie-score
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Movie {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String title;

    @JsonIgnore
    @Column(unique = true)
    private String videoPath;

    private String backgroundImg;
    private String caseImg;
    private String trailerKey;
    private Integer tmdbId;
    private String year;
    private String quality;
    private Integer runtime;
    private Double popularity;
    private Double voteAverage;
    private String filetype;
    private String previewPath;
    private Timestamp timestamp;
    private Boolean recommended;
    private Integer convertPercentage;

    @Lob
    @Column
    private String descript;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "movie")
    private List<Genre> genres;

    @JsonIgnore
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "movie")
    private List<Comment> comments;

    @JsonIgnore
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "movie")
    private List<ListMovie> listMovies;

    @JsonIgnore
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "movie")
    private List<ImportLog> importLog;

    @JsonIgnore
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "movie")
    private List<Likes> likes;

    @JsonIgnore
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "movie")
    private List<Time> times;

    @JsonIgnore
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "movie")
    private List<Subtitle> subtitles;

    public boolean isBroken() {
        return getQuality().contains(" ");
    }

    public String  getMime() {
        if (videoPath.endsWith(".mkv")) {
            return "video/x-matroska";
        } else if (videoPath.endsWith(".mp4")) {
            return "video/mp4";
        } else if (videoPath.endsWith(".avi")) {
            return "video/x-msvideo";
        } else {
            return "";
        }
    }
}
