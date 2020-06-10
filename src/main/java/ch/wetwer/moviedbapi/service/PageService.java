package ch.wetwer.moviedbapi.service;

import ch.wetwer.moviedbapi.data.movie.Movie;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Wetwer
 * @project movie-score
 */

@Service
public class PageService {

    public List<Movie> getPage(List<Movie> movies, Integer page) {

        List<Movie> pagedMovies = new ArrayList<>();
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

    public int getPagesAmount(List<Movie> movies) {
        int pages = 1;
        int moviesOnPage = 0;
        for (Movie movie : movies) {
            if (moviesOnPage == 24) {
                pages++;
                moviesOnPage = 1;
            } else {
                moviesOnPage++;
            }
        }
        return pages;
    }
}
