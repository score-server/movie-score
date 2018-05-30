package ch.felix.moviedbapi.controller;

import ch.felix.moviedbapi.data.repository.MovieRepository;
import ch.felix.moviedbapi.service.JsonService;
import ch.felix.moviedbapi.service.importer.MovieImportService;
import ch.felix.moviedbapi.service.SettingsService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

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

    private JsonService jsonService;
    private MovieImportService movieImportService;
    private SettingsService settingsService;

    public MovieController(MovieRepository movieRepository, JsonService jsonService,
                           MovieImportService movieImportService, SettingsService settingsService) {
        this.movieRepository = movieRepository;
        this.jsonService = jsonService;
        this.movieImportService = movieImportService;
        this.settingsService = settingsService;
    }

    @GetMapping(produces = "application/json")
    public String getMovies(Model model) {
        try {

            model.addAttribute("response", jsonService.getMovieList(movieRepository.findAll()));
        } catch (NullPointerException e) {
            model.addAttribute("response", "{\"response\":\"204\"}");//No Movies found
        }
        return "json";
    }

    @GetMapping(value = "/{movieId}", produces = "application/json")
    public String getOneMovie(@PathVariable("movieId") String movieId,
                              Model model) {
        try {
            model.addAttribute("response", jsonService.getMovie(movieRepository.findMovieById(Long.valueOf(movieId))));
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
            model.addAttribute("response", jsonService.getMovieList(movieRepository.findMoviesByTitleContaining(search)));
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
        movieImportService.startImport();
        model.addAttribute("response", "{\"response\":\"101\"}");//Added
        return "json";
    }

    @PostMapping(value = "/add/path", produces = "application/json")
    public String setImportpathPath(@RequestParam("path") String pathParam, Model model) {
        settingsService.setValue("path", pathParam);
        model.addAttribute("response", "{\"response\":\"102\"}");//Updated
        return "json";
    }

    @GetMapping(value = "/add/path", produces = "application/json")
    public String getImportpathPath(Model model) {
        model.addAttribute("response", "{\"path\":\"" + settingsService.getKey("path") + "\"}");
        return "json";
    }


}
