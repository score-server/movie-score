package ch.wetwer.moviedbapi.controller;

import ch.wetwer.moviedbapi.data.likes.Likes;
import ch.wetwer.moviedbapi.data.likes.LikesDao;
import ch.wetwer.moviedbapi.data.movie.Movie;
import ch.wetwer.moviedbapi.data.movie.MovieDao;
import ch.wetwer.moviedbapi.data.time.TimeDao;
import ch.wetwer.moviedbapi.data.user.User;
import ch.wetwer.moviedbapi.service.ActivityService;
import ch.wetwer.moviedbapi.service.SimilarMovieService;
import ch.wetwer.moviedbapi.service.auth.UserAuthService;
import ch.wetwer.moviedbapi.service.importer.MovieImportService;
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

    private MovieDao movieDto;
    private LikesDao likesDto;
    private TimeDao timeDto;

    private SimilarMovieService similarMovieService;
    private UserAuthService userAuthService;
    private ActivityService activityService;
    private MovieImportService movieImportService;

    public MovieController(MovieDao movieDto, LikesDao likesDto, SimilarMovieService similarMovieService,
                           UserAuthService userAuthService, ActivityService activityService, TimeDao timeDto,
                           MovieImportService movieImportService) {
        this.movieDto = movieDto;
        this.likesDto = likesDto;
        this.timeDto = timeDto;
        this.similarMovieService = similarMovieService;
        this.userAuthService = userAuthService;
        this.activityService = activityService;
        this.movieImportService = movieImportService;
    }


    @GetMapping("{movieId}")
    public String getOneMovie(@PathVariable("movieId") Long movieId, Model model, HttpServletRequest request) {
        if (userAuthService.isUser(model, request)) {
            userAuthService.log(this.getClass(), request);
            Movie movie = movieDto.getById(movieId);
            model.addAttribute("movie", movie);
            model.addAttribute("similar", similarMovieService.getSimilarMovies(movie));

            try {
                Likes likes = likesDto.getByUserAndMovie(userAuthService.getUser(request).getUser(), movie);
                likes.getId();
                model.addAttribute("hasliked", true);
            } catch (NullPointerException e) {
                model.addAttribute("hasliked", false);
            }
            User user = userAuthService.getUser(request).getUser();
            try {
                activityService.log(user.getName()
                        + " gets Movie <a href=\"/movie/" + movie.getId() + "\">" + movie.getTitle() + "</a>", user);
            } catch (NullPointerException e) {
                activityService.log("Guest gets Trailer " + movie.getTitle(), null);
            }

            try {
                model.addAttribute("time", timeDto.getByUserAndMovie(user, movie).getTime());
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
    public String likeMovie(@PathVariable("movieId") Long movieId, HttpServletRequest request) {
        if (userAuthService.isUser(request)) {
            User user = userAuthService.getUser(request).getUser();
            Movie movie = movieDto.getById(movieId);
            try {
                Likes likes = likesDto.getByUserAndMovie(user, movie);
                likes.getId();
                likesDto.delete(likes);
                activityService.log(user.getName() + " removed like on movie " +
                        "<a href=\"/movie/" + movie.getId() + "\">" + movie.getTitle() + "</a>", user);
            } catch (NullPointerException e) {
                Likes likes = new Likes();
                likes.setMovie(movie);
                likes.setUser(user);
                likesDto.save(likes);
                activityService.log(user.getName() + " likes movie " +
                        "<a href=\"/movie/" + movie.getId() + "\">" + movie.getTitle() + "</a>", user);
            }
            return "null";
        } else {
            return "null";
        }
    }


    @PostMapping("{movieId}/path")
    public String setMoviePath(@PathVariable("movieId") Long movieId, @RequestParam("path") String path,
                               HttpServletRequest request) {
        if (userAuthService.isAdministrator(request)) {
            User user = userAuthService.getUser(request).getUser();
            Movie movie = movieDto.getById(movieId);
            movie.setVideoPath(path);
            movieDto.save(movie);
            movieImportService.updateFile(new File(path));
            activityService.log(user.getName() + " changed Path on Movie " +
                    "<a href=\"/movie/" + movie.getId() + "\">" + movie.getTitle() + "</a> to " + path, user);
            return "redirect:/movie/" + movieId + "?path";
        } else {
            return "redirect:/movie/" + movieId;
        }
    }

    @PostMapping("{movieId}/attributes")
    public String setMoviePath(@PathVariable("movieId") Long movieId, @RequestParam("quality") String quality,
                               @RequestParam("year") String year,
                               @RequestParam("tmdbId") Integer tmdbId,
                               HttpServletRequest request) {
        if (userAuthService.isAdministrator(request)) {
            Movie movie = movieDto.getById(movieId);
            movie.setQuality(quality);
            movie.setYear(year);
            movie.setTmdbId(tmdbId);
            movieDto.save(movie);
            movieImportService.updateFile(new File(movie.getVideoPath()));
            return "redirect:/movie/" + movieId + "?attributes";
        } else {
            return "redirect:/movie/" + movieId;
        }
    }
}
