package ch.wetwer.moviedbapi.data.season;

import ch.wetwer.moviedbapi.data.serie.Serie;
import ch.wetwer.moviedbapi.data.episode.Episode;
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
public class Season {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JsonIgnore
    @ManyToOne
    private Serie serie;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "season")
    private List<Episode> episodes;

    private Integer season;
    private String year;

}
