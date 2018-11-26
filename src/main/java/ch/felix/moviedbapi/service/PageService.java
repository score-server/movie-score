package ch.felix.moviedbapi.service;

import ch.felix.moviedbapi.data.entity.Movie;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Wetwer
 * @project movie-db
 */

@Service
public class PageService {

    public List<Movie> getPage(List<Movie> movies, Integer page) {
        final List<Movie> pagedMovies = new ArrayList<>();
        int allowed = (page - 1) * 24;

        try {
            for (int currentMovie = 0; currentMovie != allowed + 24; currentMovie++) {
                if (currentMovie >= allowed) {
                    pagedMovies.add(movies.get(currentMovie));
                }
            }
        } catch (IndexOutOfBoundsException e) {
            return pagedMovies;
        }

        return pagedMovies;
    }
}
