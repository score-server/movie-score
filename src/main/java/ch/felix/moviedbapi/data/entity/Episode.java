package ch.felix.moviedbapi.data.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 * @author Felix
 * @date 30.05.2018
 * <p>
 * Project: movie-db-api
 * Package: ch.felix.moviedbapi.data.entity
 **/

@Entity
public class Episode {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private Long seasonFk;
    private String path;
    private String quality;
    private Integer episode;

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getSeasonFk() {
        return seasonFk;
    }

    public void setSeasonFk(Long seasonFk) {
        this.seasonFk = seasonFk;
    }

    public String getQuality() {
        return quality;
    }

    public void setQuality(String quality) {
        this.quality = quality;
    }

    public void setEpisode(Integer episode) {
        this.episode = episode;
    }

    public Integer getEpisode() {
        return episode;
    }
}
