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

    private String title;

    @Lob
    @Column
    private String description;

    @ManyToOne
    private User user;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "timeline")
    private List<ListMovie> listMovies;

}
