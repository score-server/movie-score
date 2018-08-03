package ch.felix.moviedbapi.controller;

import ch.felix.moviedbapi.data.entity.Genre;
import ch.felix.moviedbapi.data.entity.Movie;
import ch.felix.moviedbapi.data.entity.User;
import ch.felix.moviedbapi.data.repository.GenreRepository;
import ch.felix.moviedbapi.data.repository.MovieRepository;

import java.util.ArrayList;
import java.util.List;

import ch.felix.moviedbapi.service.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Wetwer
 * @project movie-db
 */
@Slf4j
@Controller
@RequestMapping("movie")
public class MovieController {

    private MovieRepository movieRepository;
    private GenreRepository genreRepository;

    private CookieService cookieService;
    private SearchService searchService;
    private SimilarMovieService similarMovieService;
    private DuplicateService duplicateService;
    private UserIndicatorService userIndicatorService;

    public MovieController(MovieRepository movieRepository, GenreRepository genreRepository,
                           CookieService cookieService, SearchService searchService,
                           SimilarMovieService similarMovieService, DuplicateService duplicateService, UserIndicatorService userIndicatorService) {
        this.movieRepository = movieRepository;
        this.genreRepository = genreRepository;
        this.cookieService = cookieService;
        this.searchService = searchService;
        this.similarMovieService = similarMovieService;
        this.duplicateService = duplicateService;
        this.userIndicatorService = userIndicatorService;
    }

    @GetMapping
    public String getMovies(@RequestParam(name = "search", required = false, defaultValue = "") String search,
                            @RequestParam(name = "orderBy", required = false, defaultValue = "") String orderBy,
                            @RequestParam(name = "genre", required = false, defaultValue = "") String genreParam,
                            Model model, HttpServletRequest request) {
        userIndicatorService.allowGuestAccess(model, request);

        try {
            List<String> genres = new ArrayList<>();
            for (Genre genre : genreRepository.findAllByNameContainingOrderByName(search)) {
                genres.add(genre.getName());
            }
            genres = duplicateService.removeStringDuplicates(genres);
            model.addAttribute("genres", genres);

            model.addAttribute("movies", searchService.searchMovies(search, orderBy, genreParam));


            model.addAttribute("search", search);
            model.addAttribute("orderBy", orderBy);
            model.addAttribute("currentGenre", genreParam);

            model.addAttribute("page", "movieList");
            return "template";
        } catch (NullPointerException e) {
            return "redirect:/";
        }
    }

    @GetMapping("{movieId}")
    public String getOneMovie(@PathVariable("movieId") String movieId, Model model, HttpServletRequest request) {
        userIndicatorService.allowGuestAccess(model, request);

        Movie movie = movieRepository.findMovieById(Long.valueOf(movieId));
        try {
            model.addAttribute("movie", movie);
            model.addAttribute("similar", similarMovieService.getSimilarMovies(movie));
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
