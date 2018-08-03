package ch.felix.moviedbapi.controller;

import ch.felix.moviedbapi.data.entity.Comment;
import ch.felix.moviedbapi.data.repository.CommentRepository;
import ch.felix.moviedbapi.data.repository.EpisodeRepository;
import ch.felix.moviedbapi.data.repository.MovieRepository;
import ch.felix.moviedbapi.data.repository.UserRepository;

import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintViolationException;

import ch.felix.moviedbapi.model.UserIndicator;
import ch.felix.moviedbapi.service.UserIndicatorService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author Wetwer
 * @project movie-db
 */
@Controller
@RequestMapping("comment")
public class CommentController {

    private MovieRepository movieRepository;
    private EpisodeRepository episodeRepository;
    private CommentRepository commentRepository;

    private UserIndicatorService userIndicatorService;

    public CommentController(MovieRepository movieRepository, EpisodeRepository episodeRepository,
                             CommentRepository commentRepository, UserIndicatorService userIndicatorService) {
        this.movieRepository = movieRepository;
        this.episodeRepository = episodeRepository;
        this.commentRepository = commentRepository;
        this.userIndicatorService = userIndicatorService;
    }

    @PostMapping("add/movie")
    public String addCommentForMovie(@RequestParam("movieId") String movieId,
                                     @RequestParam("comment") String commentParam, HttpServletRequest request) {
        try {
            UserIndicator userIndicator = userIndicatorService.getUser(request);

            if (userIndicator.isLoggedIn()) {
                Comment comment = new Comment();
                comment.setUser(userIndicator.getUser());
                comment.setMovie(movieRepository.findMovieById(Long.valueOf(movieId)));
                comment.setComment(commentParam);
                commentRepository.save(comment);
            }
            return "redirect:/movie/" + movieId;
        } catch (ConstraintViolationException e) {
            e.printStackTrace();
            return "redirect:/movie/" + movieId + "?error";
        }
    }

    @PostMapping("add/episode")
    public String addCommentForEpisode(@RequestParam("episodeId") String episodeId,
                                       @RequestParam("comment") String commentParam, HttpServletRequest request) {
        try {
            UserIndicator userIndicator = userIndicatorService.getUser(request);

            if (userIndicator.isLoggedIn()) {
                Comment comment = new Comment();
                comment.setUser(userIndicator.getUser());
                comment.setEpisode(episodeRepository.findEpisodeById(Long.valueOf(episodeId)));
                comment.setComment(commentParam);
                commentRepository.save(comment);
            }
            return "redirect:/episode/" + episodeId;
        } catch (ConstraintViolationException e) {
            e.printStackTrace();
            return "redirect:/episode/" + episodeId + "?error";
        }

    }
}
