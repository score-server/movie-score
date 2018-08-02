package ch.felix.moviedbapi.data.entity;

import lombok.Data;

import javax.persistence.*;
import java.util.List;

/**
 * @author Wetwer
 * @project movie-db
 */
@Data
@Entity
public class Timeline {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String title;

    @Lob
    @Column
    private String description;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "timeline")
    private List<ListMovie> listMovies;

}
