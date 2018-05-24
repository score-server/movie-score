package ch.felix.moviedbapi.controller;

import ch.felix.moviedbapi.data.repository.MovieRepository;
import ch.felix.moviedbapi.service.JsonService;
import ch.felix.moviedbapi.service.MovieImportService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

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

    public MovieController(MovieRepository movieRepository, JsonService jsonService, MovieImportService movieImportService) {
        this.movieRepository = movieRepository;
        this.jsonService = jsonService;
        this.movieImportService = movieImportService;
    }

    @GetMapping(produces = "application/json")
    public String getMovies(Model model) {
        model.addAttribute("response", jsonService.getMovieList(movieRepository.findAll()));
        return "json";
    }

    @GetMapping(value = "/{movieId}", produces = "application/json")
    public String getOneMovie(@PathVariable("movieId") String movieId,
                              Model model) {
        try {
            model.addAttribute("response", jsonService.getMovie(movieRepository.findMovieById(Long.valueOf(movieId))));
        } catch (NullPointerException e){
            model.addAttribute("response", "{\"response\":\"1\"}");//Movie not found
        }
        return "json";
    }

    @GetMapping(value = "/search/{search}", produces = "application/json")
    public String searchMovie(@PathVariable("search") String search,
                              Model model) {
        model.addAttribute("response", jsonService.getMovieList(movieRepository.findMoviesByTitleContaining(search)));
        return "json";
    }

    @PostMapping(value = "/add", produces = "application/json")
    public String importMovies(Model model) {
        movieImportService.startImport();
        model.addAttribute("response", "{\"response\":\"1\"}");//Added
        return "json";
    }


}
