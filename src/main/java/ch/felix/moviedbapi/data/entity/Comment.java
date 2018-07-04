package ch.felix.moviedbapi.data.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;

/**
 * @author Felix
 * @date 05.06.2018
 * <p>
 * Project: movie-db-api
 * Package: ch.felix.moviedbapi.data.entity
 **/

@Data
@Entity
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JsonIgnore
    @ManyToOne
    private Movie movie;

    @JsonIgnore
    @ManyToOne
    private Episode episode;

    @NotNull
    @ManyToOne
    private User user;

    @Lob
    @NotNull
    private String comment;


}
