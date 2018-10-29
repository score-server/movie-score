package ch.felix.moviedbapi.model;

import ch.felix.moviedbapi.data.entity.Movie;
import lombok.Data;

@Data
public class StartedMovie {

    private Movie movie;
    private float progress;

}
