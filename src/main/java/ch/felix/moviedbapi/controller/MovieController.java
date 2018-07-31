package ch.felix.moviedbapi.controller;

import ch.felix.moviedbapi.data.entity.Genre;
import ch.felix.moviedbapi.data.entity.Movie;
import ch.felix.moviedbapi.data.entity.User;
import ch.felix.moviedbapi.data.repository.GenreRepository;
import ch.felix.moviedbapi.data.repository.MovieRepository;

import java.util.ArrayList;
import java.util.List;

import ch.felix.moviedbapi.service.CookieService;
import ch.felix.moviedbapi.service.SearchService;
import ch.felix.moviedbapi.service.SimilarMovieService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Felix
 * @date 23.05.2018
 * <p>
 * Project: movie-db-api
 * Package: ch.felix.moviedbapi.controller
 **/

@Slf4j
@Controller
@RequestMapping("movie")
public class MovieController {

    private MovieRepository movieRepository;
    private GenreRepository genreRepository;

    private CookieService cookieService;
    private SearchService searchService;
    private SimilarMovieService similarMovieService;

    public MovieController(MovieRepository movieRepository, GenreRepository genreRepository,
                           CookieService cookieService, SearchService searchService,
                           SimilarMovieService similarMovieService) {
        this.movieRepository = movieRepository;
        this.genreRepository = genreRepository;
        this.cookieService = cookieService;
        this.searchService = searchService;
        this.similarMovieService = similarMovieService;
    }

    @GetMapping
    public String getMovies(@RequestParam(name = "search", required = false, defaultValue = "") String search,
                            @RequestParam(name = "orderBy", required = false, defaultValue = "") String orderBy,
                            Model model, HttpServletRequest request) {
        try {
            model.addAttribute("currentUser", cookieService.getCurrentUser(request));
        } catch (NullPointerException e) {
        }

        try {
            model.addAttribute("movies", searchService.searchMovies(search, orderBy));

            model.addAttribute("search", search);
            model.addAttribute("orderBy", orderBy);

            model.addAttribute("page", "movieList");
            return "template";
        } catch (NullPointerException e) {
            return "redirect:/";
        }
    }

    @GetMapping("{movieId}")
    public String getOneMovie(@PathVariable("movieId") String movieId, Model model, HttpServletRequest request) {
        try {
            model.addAttribute("currentUser", cookieService.getCurrentUser(request));
        } catch (NullPointerException e) {
            return "redirect:/login?redirect=/movie/" + movieId;
        }

        Movie movie = movieRepository.findMovieById(Long.valueOf(movieId));
        try {
            User currentUser = cookieService.getCurrentUser(request);
            log.info(currentUser.getName() + " request movie " + movie.getId() + ". " + movie.getTitle());


            model.addAttribute("movie", movie);
            model.addAttribute("similar", similarMovieService.getSimilarMovies(movie));
            model.addAttribute("comments", movie.getComments());
            model.addAttribute("page", "movie");
            return "template";
        } catch (NullPointerException | NumberFormatException e) {
            e.printStackTrace();
            return "redirect:/movie";
        }
    }

    @GetMapping("/genre/{genre}")
    public List<Movie> getMoviesForGenre(@PathVariable("genre") String genreParam, Model model) {
        List<Genre> genres = genreRepository.findGenresByName(genreParam);

        List<Movie> movieList = new ArrayList<>();
        for (Genre genre : genres) {
            movieList.add(genre.getMovie());
        }

        model.addAttribute("movies", movieList);
        return movieList;
    }
}
