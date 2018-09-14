package ch.felix.moviedbapi.controller.api;

import ch.felix.moviedbapi.data.entity.Movie;
import ch.felix.moviedbapi.data.repository.MovieRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("api/movie")
public class MovieApiController {

    private MovieRepository movieRepository;

    public MovieApiController(MovieRepository movieRepository) {
        this.movieRepository = movieRepository;
    }

    @GetMapping(produces = "application/json")
    public List<Movie> getUserList(@RequestParam(name = "name", required = false, defaultValue = "") String name) {
        return movieRepository.findTop10ByTitleContainingOrderByPopularityDesc(name);
    }


}
