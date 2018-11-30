package ch.wetwer.moviedbapi.data.entity;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import java.sql.Timestamp;

/**
 * @author Wetwer
 * @project movie-db
 */
@Data
@Entity
public class ImportLog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String log;

    @ManyToOne
    private Movie movie;

    private String type;

    private Timestamp timestamp;
}
