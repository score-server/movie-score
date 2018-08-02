package ch.felix.moviedbapi.data.entity;

import lombok.Data;

import javax.persistence.*;
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
