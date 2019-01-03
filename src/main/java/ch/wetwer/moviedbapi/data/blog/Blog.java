package ch.wetwer.moviedbapi.data.blog;

import ch.wetwer.moviedbapi.data.user.User;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;
import java.sql.Timestamp;

/**
 * @author Wetwer
 * @project movie-db
 */
@Data
@Entity
public class Blog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @ManyToOne
    private User user;

    @Lob
    @NotNull
    private String title;

    @Lob
    @NotNull
    private String text;

    private Timestamp timestamp;


}
