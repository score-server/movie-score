package ch.felix.moviedbapi.controller;

import ch.felix.moviedbapi.data.entity.Movie;
import ch.felix.moviedbapi.data.repository.MovieRepository;
import ch.felix.moviedbapi.service.SettingsService;
import ch.felix.moviedbapi.service.importer.MovieImportService;
import ch.felix.moviedbapi.service.json.MovieJsonService;
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

    private MovieImportService movieImportService;
    private SettingsService settingsService;
    private MovieJsonService movieJsonService;

    public MovieController(MovieRepository movieRepository, MovieImportService movieImportService,
                           SettingsService settingsService, MovieJsonService movieJsonService) {
        this.movieRepository = movieRepository;
        this.movieImportService = movieImportService;
        this.settingsService = settingsService;
        this.movieJsonService = movieJsonService;
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


}
