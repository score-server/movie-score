package ch.felix.moviedbapi.data.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

/**
 * @author Felix
 * @date 30.05.2018
 * <p>
 * Project: movie-db-api
 * Package: ch.felix.moviedbapi.data.entity
 **/

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
