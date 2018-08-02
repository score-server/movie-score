package ch.felix.moviedbapi.data.entity;

import lombok.*;

import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.OneToMany;

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

    @Column(unique = true)
    private String videoPath;
    private String backgroundImg;
    private String caseImg;

    @Lob
    @Column
    private String descript;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "movie")
    private List<Genre> genres;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "movie")
    private List<Comment> comments;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "movie")
    private List<ListMovie> listMovies;

    private String year;
    private String quality;
    private Integer runtime;
    private Double popularity;
    private Double voteAverage;


}
