package ch.felix.moviedbapi.controller;

import ch.felix.moviedbapi.data.entity.Likes;
import ch.felix.moviedbapi.data.entity.Movie;
import ch.felix.moviedbapi.data.entity.User;
import ch.felix.moviedbapi.data.repository.LikesRepository;
import ch.felix.moviedbapi.data.repository.MovieRepository;
import ch.felix.moviedbapi.data.repository.TimeRepository;
import ch.felix.moviedbapi.service.ActivityService;
import ch.felix.moviedbapi.service.SimilarMovieService;
import ch.felix.moviedbapi.service.UserIndicatorService;
import ch.felix.moviedbapi.service.importer.MovieImportService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import java.io.File;

/**
 * @author Wetwer
 * @project movie-db
 */

@Controller
@RequestMapping("movie")
public class MovieController {

    private MovieRepository movieRepository;
    private LikesRepository likesRepository;
    private TimeRepository timeRepository;

    private SimilarMovieService similarMovieService;
    private UserIndicatorService userIndicatorService;
    private ActivityService activityService;
    private MovieImportService movieImportService;

    public MovieController(MovieRepository movieRepository, LikesRepository likesRepository,
                           SimilarMovieService similarMovieService, UserIndicatorService userIndicatorService,
                           ActivityService activityService, TimeRepository timeRepository,
                           MovieImportService movieImportService) {
        this.movieRepository = movieRepository;
        this.likesRepository = likesRepository;
        this.timeRepository = timeRepository;
        this.similarMovieService = similarMovieService;
        this.userIndicatorService = userIndicatorService;
        this.activityService = activityService;
        this.movieImportService = movieImportService;
    }


    @GetMapping("{movieId}")
    public String getOneMovie(@PathVariable("movieId") String movieId, Model model, HttpServletRequest request) {
        if (userIndicatorService.isUser(model, request)) {
            Movie movie = movieRepository.findMovieById(Long.valueOf(movieId));
            model.addAttribute("movie", movie);
            model.addAttribute("similar", similarMovieService.getSimilarMovies(movie));

            try {
                Likes likes = likesRepository.findLikeByUserAndMovie(
                        userIndicatorService.getUser(request).getUser(), movie);
                likes.getId();
                model.addAttribute("hasliked", true);
            } catch (NullPointerException e) {
                model.addAttribute("hasliked", false);
            }
            User user = userIndicatorService.getUser(request).getUser();
            try {
                activityService.log(user.getName() + " gets Movie " + movie.getTitle(), user);
            } catch (NullPointerException e) {
                activityService.log("Guest gets Trailer " + movie.getTitle(), null);
            }

            try {
                model.addAttribute("time", timeRepository.findTimeByUserAndMovie(user, movie).getTime());
            } catch (NullPointerException e) {
                model.addAttribute("time", 0);
            }

            model.addAttribute("page", "movie");
            return "template";
        } else {
            return "redirect:/login?redirect=/movie/" + movieId;
        }
    }

    @PostMapping("{movieId}/like")
    public String likeMovie(@PathVariable("movieId") String movieId, HttpServletRequest request) {
        if (userIndicatorService.isUser(request)) {
            User user = userIndicatorService.getUser(request).getUser();
            Movie movie = movieRepository.findMovieById(Long.valueOf(movieId));
            try {
                Likes likes = likesRepository.findLikeByUserAndMovie(user, movie);
                likes.getId();
                likesRepository.delete(likes);
                activityService.log(user.getName() + " removed like on movie " + movie.getTitle(), user);
            } catch (NullPointerException e) {
                Likes likes = new Likes();
                likes.setMovie(movie);
                likes.setUser(user);
                likesRepository.save(likes);
                activityService.log(user.getName() + " likes movie " + movie.getTitle(), user);
            }
            return "redirect:/movie/" + movieId;
        } else {
            return "redirect:/login?redirect=/movie/" + movieId;
        }
    }


    @PostMapping("{movieId}/path")
    public String setMoviePath(@PathVariable("movieId") Long movieId, @RequestParam("path") String path,
                               HttpServletRequest request) {
        if (userIndicatorService.isAdministrator(request)) {
            User user = userIndicatorService.getUser(request).getUser();
            Movie movie = movieRepository.findMovieById(movieId);
            movie.setVideoPath(path);
            movieRepository.save(movie);
            movieImportService.updateFile(new File(path));
            activityService.log(user.getName() + " changed Path on Movie " + movie.getTitle() + " to " + path, user);
            return "redirect:/movie/" + movieId + "?path";
        } else {
            return "redirect:/movie/" + movieId;
        }
    }

    //TODO change Quality and Year
}
