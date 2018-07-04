package ch.felix.moviedbapi.data.entity;

import lombok.*;

import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.OneToMany;

/**
 * @author Felix
 * @date 30.05.2018
 * <p>
 * Project: movie-db-api
 * Package: ch.felix.moviedbapi.data.entity
 **/

@Data
@Entity
public class Serie {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "serie")
    private List<Season> seasons;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "serie")
    private List<Genre> genres;

    @Lob
    @Column
    private String descript;

    private String caseImg;
    private String backgroundImg;
    private Double popularity;


}