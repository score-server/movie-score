package ch.wetwer.moviedbapi.data.serie;

import ch.wetwer.moviedbapi.data.genre.Genre;
import ch.wetwer.moviedbapi.data.season.Season;
import lombok.Data;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
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
public class Serie {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String title;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "serie")
    private List<Season> seasons;

    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL, mappedBy = "serie")
    private List<Genre> genres;

    @Lob
    @Column
    private String descript;

    private String caseImg;
    private String backgroundImg;
    private Double popularity;
    private Double voteAverage;
    private Integer tmdbId;

}