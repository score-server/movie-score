package ch.felix.moviedbapi.data.entity;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
public class ListMovie {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Movie movie;

    @ManyToOne
    private Timeline timeline;

    private Integer place;
}
