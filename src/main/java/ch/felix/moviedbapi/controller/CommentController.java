package ch.felix.moviedbapi.controller;

import ch.felix.moviedbapi.data.dao.CommentDao;
import ch.felix.moviedbapi.data.dao.EpisodeDao;
import ch.felix.moviedbapi.data.dao.MovieDao;
import ch.felix.moviedbapi.data.entity.Episode;
import ch.felix.moviedbapi.data.entity.Movie;
import ch.felix.moviedbapi.data.entity.User;
import ch.felix.moviedbapi.service.ActivityService;
import ch.felix.moviedbapi.service.auth.UserAuthService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Wetwer
 * @project movie-db
 */
@Controller
@RequestMapping("comment")
public class CommentController {

    private MovieDao movieDao;
    private EpisodeDao episodeDao;
    private CommentDao commentDao;

    private UserAuthService userAuthService;
    private ActivityService activityService;

    public CommentController(MovieDao movieDao, EpisodeDao episodeDao, CommentDao commentDao,
                             UserAuthService userAuthService, ActivityService activityService) {
        this.movieDao = movieDao;
        this.episodeDao = episodeDao;
        this.commentDao = commentDao;
        this.userAuthService = userAuthService;
        this.activityService = activityService;
    }

    @PostMapping("add/movie")
    public String addCommentForMovie(@RequestParam("movieId") String movieId,
                                     @RequestParam("comment") String commentParam, HttpServletRequest request) {

        if (userAuthService.isUser(request)) {
            User user = userAuthService.getUser(request).getUser();
            Movie movie = movieDao.getById(Long.valueOf(movieId));

            commentDao.createMovieComment(user, movie, commentParam);

            activityService.log(user.getName() + " created Comment on Movie " + movie.getTitle());
            return "redirect:/movie/" + movieId;
        } else {
            return "redirect:/movie/" + movieId + "?error";
        }
    }

    @PostMapping("add/episode")
    public String addCommentForEpisode(@RequestParam("episodeId") String episodeId,
                                       @RequestParam("comment") String commentParam, HttpServletRequest request) {
        if (userAuthService.isUser(request)) {
            User user = userAuthService.getUser(request).getUser();
            Episode episode = episodeDao.getById(Long.valueOf(episodeId));

            commentDao.createEpisodeComment(user, episode, commentParam);

            activityService.log(user.getName() + " created Comment on Episode " + episode.getFullTitle());
            return "redirect:/episode/" + episodeId;
        } else {
            return "redirect:/episode/" + episodeId + "?error";
        }
    }
}
