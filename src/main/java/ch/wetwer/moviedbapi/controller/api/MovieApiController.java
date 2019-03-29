package ch.wetwer.moviedbapi.controller.api;

import ch.wetwer.moviedbapi.data.movie.Movie;
import ch.wetwer.moviedbapi.data.movie.MovieDao;
import ch.wetwer.moviedbapi.data.user.UserDao;
import ch.wetwer.moviedbapi.service.SearchService;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author Wetwer
 * @project movie-db
 */

@CrossOrigin
@RestController
@RequestMapping("api/movie")
public class MovieApiController {

    private MovieDao movieDao;
    private UserDao userDao;

    private SearchService searchService;

    public MovieApiController(MovieDao movieDao, UserDao userDao, SearchService searchService) {
        this.movieDao = movieDao;
        this.userDao = userDao;
        this.searchService = searchService;
    }

    @GetMapping(produces = "application/json")
    public List<Movie> getMovieList(@RequestParam(name = "name", required = false, defaultValue = "") String name,
                                    @RequestParam("sessionId") String sessionId) {
        if (userDao.getBySessionId(sessionId) != null) {
            return searchService.searchMoviesTop(name, "");
        } else {
            return null;
        }
    }

    @GetMapping(value = "{movieId}", produces = "application/json")
    public Movie getOneMovie(@PathVariable Long movieId,
                             @RequestParam("sessionId") String sessionId) {
        if (userDao.getBySessionId(sessionId) != null) {
            return movieDao.getById(movieId);
        } else {
            return null;
        }
    }

}
