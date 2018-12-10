package ch.wetwer.moviedbapi.data.entity;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.sql.Timestamp;

/**
 * @author Wetwer
 * @project movie-db-api
 * @package ch.wetwer.moviedbapi.data.entity
 * @created 28.11.2018
 **/
@Data
@Entity
public class UpdateLog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String type;

    private Timestamp timestamp;

    private Boolean completed;

    public String getTypeLong() {
        switch (type) {
            case "MI":
                return "Movie Import";
            case "MU":
                return "Movie Update";
            case "SI":
                return "Serie Import";
            case "SU":
                return "Serie Update";
            default:
                return null;
        }
    }

}
