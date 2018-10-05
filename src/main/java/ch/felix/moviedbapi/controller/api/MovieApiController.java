package ch.felix.moviedbapi.controller.api;

import ch.felix.moviedbapi.data.dto.MovieDto;
import ch.felix.moviedbapi.data.dto.UserDto;
import ch.felix.moviedbapi.data.entity.Movie;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("api/movie")
public class MovieApiController {

    private MovieDto movieDto;
    private UserDto userDto;

    public MovieApiController(MovieDto movieDto, UserDto userDto) {
        this.movieDto = movieDto;
        this.userDto = userDto;
    }

    @GetMapping(produces = "application/json")
    public List<Movie> getMovieList(@RequestParam(name = "name", required = false, defaultValue = "") String name,
                                    @RequestParam("sessionId") String sessionId) {
        if (userDto.getBySessionId(sessionId) != null) {
            return movieDto.searchTop10(name);
        } else {
            return null;
        }
    }

    @GetMapping(value = "{movieId}", produces = "application/json")
    public Movie getOneMovie(@PathVariable Long movieId,
                             @RequestParam("sessionId") String sessionId) {
        if (userDto.getBySessionId(sessionId) != null) {
            return movieDto.getById(movieId);
        } else {
            return null;
        }
    }

}
