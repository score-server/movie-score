package ch.felix.moviedbapi.data.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;

/**
 * @author Wetwer
 * @project movie-db-api
 * @package ch.felix.moviedbapi.data.entity
 * @created 26.11.2018
 **/

@Data
@Entity
public class Subtitle {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String language;

    @Lob
    @JsonIgnore
    private byte[] file;

    @ManyToOne
    private Movie movie;

    @ManyToOne
    private Episode episode;

    @ManyToOne
    private User user;

}
