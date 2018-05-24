package ch.felix.moviedbapi.controller;

import ch.felix.moviedbapi.data.repository.MovieRepository;
import ch.felix.moviedbapi.service.JsonService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
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

    public MovieController(MovieRepository movieRepository, JsonService jsonService) {
        this.movieRepository = movieRepository;
        this.jsonService = jsonService;
    }

    @GetMapping(produces = "application/json")
    public String getMovies(Model model) {
        model.addAttribute("response", jsonService.getMovieList(movieRepository.findAll()));
        return "json";
    }

    @GetMapping(value = "/{movieId}", produces = "application/json")
    public String getOneMovie(@PathVariable("movieId") String movieId,
                              Model model) {
        model.addAttribute("response", jsonService.getMovie(movieRepository.findMovieById(Long.valueOf(movieId))));
        return "json";
    }

    @GetMapping(value = "/search/{search}", produces = "application/json")
    public String searchMovie(@PathVariable("search") String search,
                              Model model) {
        model.addAttribute("response", jsonService.getMovieList(movieRepository.findMoviesByTitleContaining(search)));
        return "json";
    }


}
