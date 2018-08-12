package ch.felix.moviedbapi.controller;

import ch.felix.moviedbapi.data.entity.Movie;
import ch.felix.moviedbapi.data.repository.GenreRepository;
import ch.felix.moviedbapi.data.repository.MovieRepository;

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

    private SimilarMovieService similarMovieService;
    private UserIndicatorService userIndicatorService;

    public MovieController(MovieRepository movieRepository, SimilarMovieService similarMovieService,
                           UserIndicatorService userIndicatorService) {
        this.movieRepository = movieRepository;
        this.similarMovieService = similarMovieService;
        this.userIndicatorService = userIndicatorService;
    }


    @GetMapping("{movieId}")
    public String getOneMovie(@PathVariable("movieId") String movieId, Model model, HttpServletRequest request) {
        userIndicatorService.allowGuest(model, request);

        Movie movie = movieRepository.findMovieById(Long.valueOf(movieId));
        model.addAttribute("movie", movie);
        model.addAttribute("similar", similarMovieService.getSimilarMovies(movie));

        try {
            log.info(userIndicatorService.getUser(request).getUser().getName() + " gets Movie " + movie.getTitle());
        } catch (NullPointerException e) {
            log.info("Guest gets Trailer " + movie.getTitle());
        }

        model.addAttribute("page", "movie");
        return "template";
    }
}
