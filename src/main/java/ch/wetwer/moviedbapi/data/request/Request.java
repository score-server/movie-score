package ch.wetwer.moviedbapi.data.request;

import ch.wetwer.moviedbapi.data.user.User;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;

/**
 * @author Wetwer
 * @project movie-score
 */
@Data
@Entity
public class Request {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Lob
    @Column
    @NotNull
    private String request;

    @NotNull
    private String active;

    @NotNull
    @ManyToOne
    private User user;


}
