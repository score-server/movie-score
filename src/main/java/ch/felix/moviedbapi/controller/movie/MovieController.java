package ch.felix.moviedbapi.controller.movie;

import ch.felix.moviedbapi.data.entity.Genre;
import ch.felix.moviedbapi.data.entity.Movie;
import ch.felix.moviedbapi.data.repository.GenreRepository;
import ch.felix.moviedbapi.data.repository.MovieRepository;
import java.util.ArrayList;
import java.util.List;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Felix
 * @date 23.05.2018
 * <p>
 * Project: movie-db-api
 * Package: ch.felix.moviedbapi.controller
 **/

@RestController
@RequestMapping("movie")
public class MovieController {

    private MovieRepository movieRepository;
    private GenreRepository genreRepository;

    public MovieController(MovieRepository movieRepository, GenreRepository genreRepository) {
        this.movieRepository = movieRepository;
        this.genreRepository = genreRepository;
    }

    @GetMapping(produces = "application/json")
    public List<Movie> getMovies() {
        try {
            return movieRepository.findAll();
        } catch (NullPointerException e) {
            return null;//No Movies found
        }
    }

    @GetMapping(value = "/{movieId}", produces = "application/json")
    public Movie getOneMovie(@PathVariable("movieId") String movieId) {
        try {
            return movieRepository.findMovieById(Long.valueOf(movieId));
        } catch (NullPointerException | NumberFormatException e) {
            e.printStackTrace();
            return null;
        }
    }

    @GetMapping(value = "/search/{search}", produces = "application/json")
    public List<Movie> searchMovie(@PathVariable("search") String search) {
        try {
            return movieRepository.findMoviesByTitleContaining(search);
        } catch (NumberFormatException | NullPointerException e) {
            e.printStackTrace();
            return null;
        }
    }

    @GetMapping(value = "/genre/{genre}", produces = "application/json")
    public List<Movie> getMoviesForGenre(@PathVariable("genre") String genreParam) {
        List<Genre> genres = genreRepository.findGenresByName(genreParam);

        List<Movie> movieList = new ArrayList<>();
        for (Genre genre : genres) {
            movieList.add(genre.getMovie());
        }

        return movieList;
    }
}
