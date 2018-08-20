package ch.felix.moviedbapi.controller;

import ch.felix.moviedbapi.data.entity.Movie;
import ch.felix.moviedbapi.data.repository.MovieRepository;
import ch.felix.moviedbapi.service.ActivityService;
import ch.felix.moviedbapi.service.SimilarMovieService;
import ch.felix.moviedbapi.service.UserIndicatorService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

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
    private ActivityService activityService;

    public MovieController(MovieRepository movieRepository, SimilarMovieService similarMovieService,
                           UserIndicatorService userIndicatorService, ActivityService activityService) {
        this.movieRepository = movieRepository;
        this.similarMovieService = similarMovieService;
        this.userIndicatorService = userIndicatorService;
        this.activityService = activityService;
    }


    @GetMapping("{movieId}")
    public String getOneMovie(@PathVariable("movieId") String movieId, Model model, HttpServletRequest request) {
        if (userIndicatorService.isUser(model, request)) {


            Movie movie = movieRepository.findMovieById(Long.valueOf(movieId));
            model.addAttribute("movie", movie);
            model.addAttribute("similar", similarMovieService.getSimilarMovies(movie));

            try {
                activityService.log(userIndicatorService.getUser(request).getUser().getName() + " gets Movie " + movie.getTitle());
            } catch (NullPointerException e) {
                activityService.log("Guest gets Trailer " + movie.getTitle());
            }

            model.addAttribute("page", "movie");
            return "template";
        } else {
            return "redirect:/login?redirect=/movie/" + movieId;
        }
    }
}
