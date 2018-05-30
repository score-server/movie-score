package ch.felix.moviedbapi.controller;

import ch.felix.moviedbapi.data.repository.MovieRepository;
import ch.felix.moviedbapi.service.SettingsService;
import ch.felix.moviedbapi.service.importer.MovieImportService;
import ch.felix.moviedbapi.service.json.MovieJsonService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

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
    public String getMovies(Model model) {
        try {

            model.addAttribute("response", movieJsonService.getMovieList(movieRepository.findAll()));
        } catch (NullPointerException e) {
            model.addAttribute("response", "{\"response\":\"204\"}");//No Movies found
        }
        return "json";
    }

    @GetMapping(value = "/{movieId}", produces = "application/json")
    public String getOneMovie(@PathVariable("movieId") String movieId,
                              Model model) {
        try {
            model.addAttribute("response", movieJsonService.getMovie(
                    movieRepository.findMovieById(Long.valueOf(movieId))));
        } catch (NullPointerException e) {
            model.addAttribute("response", "{\"response\":\"204\"}");//Movie not found
        } catch (NumberFormatException e) {
            model.addAttribute("response", "{\"response\":\"205\"}");//Error with input
        }
        return "json";
    }

    @GetMapping(value = "/search/{search}", produces = "application/json")
    public String searchMovie(@PathVariable("search") String search,
                              Model model) {
        try {
            model.addAttribute("response", movieJsonService.getMovieList(
                    movieRepository.findMoviesByTitleContaining(search)));
        } catch (NullPointerException e) {
            e.printStackTrace();
            model.addAttribute("response", "{\"response\":\"204\"}");//No movie Found
        } catch (NumberFormatException e) {
            e.printStackTrace();
            model.addAttribute("response", "{\"response\":\"205\"}");//Error with input
        }
        return "json";
    }

    @PostMapping(value = "/add", produces = "application/json")
    public String importMovies(Model model) {
        movieImportService.importFile("moviePath");
        model.addAttribute("response", "{\"response\":\"101\"}");//Added
        return "json";
    }

    @PostMapping(value = "/add/path", produces = "application/json")
    public String setImportpathPath(@RequestParam("path") String pathParam, Model model) {
        settingsService.setValue("moviePath", pathParam);
        model.addAttribute("response", "{\"response\":\"102\"}");//Updated
        return "json";
    }

    @GetMapping(value = "/add/path", produces = "application/json")
    public String getImportpathPath(Model model) {
        model.addAttribute("response", "{\"path\":\"" + settingsService.getKey("moviePath") + "\"}");
        return "json";
    }


}
