package ch.wetwer.moviedbapi.data.timeline;

import ch.wetwer.moviedbapi.data.user.User;
import ch.wetwer.moviedbapi.data.listmovie.ListMovie;
import lombok.Data;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
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
