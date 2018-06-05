package ch.felix.moviedbapi.controller.movieside;

import ch.felix.moviedbapi.data.entity.Genre;
import ch.felix.moviedbapi.data.entity.Movie;
import ch.felix.moviedbapi.data.repository.GenreRepository;
import ch.felix.moviedbapi.data.repository.MovieRepository;
import ch.felix.moviedbapi.service.SettingsService;
import ch.felix.moviedbapi.service.importer.MovieImportService;
import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author Felix
 * @date 23.05.2018
 * <p>
 * Project: movie-db-api
 * Package: ch.felix.moviedbapi.controller
 **/

@Controller
@RequestMapping("movie")
public class MovieController {

    private MovieRepository movieRepository;
    private GenreRepository genreRepository;

    private MovieImportService movieImportService;
    private SettingsService settingsService;

    public MovieController(MovieRepository movieRepository, MovieImportService movieImportService,
                           SettingsService settingsService, GenreRepository genreRepository) {
        this.movieRepository = movieRepository;
        this.movieImportService = movieImportService;
        this.settingsService = settingsService;
        this.genreRepository = genreRepository;
    }

    @GetMapping(produces = "application/json")
    public @ResponseBody
    List<Movie> getMovies() {
        try {
            return movieRepository.findAll();
        } catch (NullPointerException e) {
            return null;//No Movies found
        }
    }

    @GetMapping(value = "/{movieId}", produces = "application/json")
    public @ResponseBody
    Movie getOneMovie(@PathVariable("movieId") String movieId) {
        try {
            return movieRepository.findMovieById(Long.valueOf(movieId));
        } catch (NullPointerException | NumberFormatException e) {
            e.printStackTrace();
            return null;
        }
    }

    @GetMapping(value = "/search/{search}", produces = "application/json")
    public @ResponseBody
    List<Movie> searchMovie(@PathVariable("search") String search) {
        try {
            return movieRepository.findMoviesByTitleContaining(search);
        } catch (NumberFormatException | NullPointerException e) {
            e.printStackTrace();
            return null;
        }
    }

    @PostMapping(value = "/add/path", produces = "application/json")
    public @ResponseBody
    String setImportpathPath(@RequestParam("path") String pathParam) {
        settingsService.setValue("moviePath", pathParam);
        return "102";
    }

    @GetMapping(value = "/add/path", produces = "application/json")
    public @ResponseBody
    String getImportpathPath() {
        return settingsService.getKey("moviePath");
    }

    @GetMapping(value = "/genre/{genre}", produces = "application/json")
    public @ResponseBody
    List<Movie> getMoviesForGenre(@PathVariable("genre") String genreParam) {
        List<Genre> genres = genreRepository.findGenresByName(genreParam);

        List<Movie> movieList = new ArrayList<>();
        for (Genre genre : genres) {
            movieList.add(genre.getMovie());
        }

        return movieList;
    }
}
