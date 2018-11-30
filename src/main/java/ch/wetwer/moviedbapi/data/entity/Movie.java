package ch.wetwer.moviedbapi.data.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.OneToMany;
import java.util.List;

/**
 * @author Wetwer
 * @project movie-db
 */
@Data
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


}
