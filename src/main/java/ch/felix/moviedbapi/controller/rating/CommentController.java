package ch.felix.moviedbapi.controller.rating;

import ch.felix.moviedbapi.data.entity.Comment;
import ch.felix.moviedbapi.data.repository.CommentRepository;
import ch.felix.moviedbapi.data.repository.EpisodeRepository;
import ch.felix.moviedbapi.data.repository.MovieRepository;
import ch.felix.moviedbapi.data.repository.UserRepository;
import ch.felix.moviedbapi.service.ViolationService;
import java.util.List;
import javax.validation.ConstraintViolationException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Felix
 * @date 05.06.2018
 * <p>
 * Project: movie-db-api
 * Package: ch.felix.moviedbapi.controller
 **/

@RestController
@RequestMapping("comment")
public class CommentController {

    private MovieRepository movieRepository;
    private EpisodeRepository episodeRepository;
    private UserRepository userRepository;
    private CommentRepository commentRepository;

    private ViolationService violationService;

    public CommentController(MovieRepository movieRepository, EpisodeRepository episodeRepository,
                             UserRepository userRepository, CommentRepository commentRepository, ViolationService violationService) {
        this.movieRepository = movieRepository;
        this.episodeRepository = episodeRepository;
        this.userRepository = userRepository;
        this.commentRepository = commentRepository;
        this.violationService = violationService;
    }

    @GetMapping(value = "movie/{movieId}", produces = "application/json")
    public List<Comment> getForMovie(@PathVariable("movieId") String movieId) {
        return movieRepository.findMovieById(Long.valueOf(movieId)).getComments();
    }

    @GetMapping(value = "episode/{episodeId}", produces = "application/json")
    public List<Comment> getForEpisode(@PathVariable("episodeId") String episodeId) {
        return episodeRepository.findEpisodeById(Long.valueOf(episodeId)).getComments();
    }

    @PostMapping("add/movie")
    public String addCommentForMovie(@RequestParam("userId") String userId,
                                     @RequestParam("movieId") String movieId,
                                     @RequestParam("comment") String commentParam) {
        try {
            Comment comment = new Comment();
            comment.setUser(userRepository.findUserById(Long.valueOf(userId)));
            comment.setMovie(movieRepository.findMovieById(Long.valueOf(movieId)));
            comment.setComment(commentParam);
            commentRepository.save(comment);
            return "103";
        } catch (ConstraintViolationException e) {
            return "205 " + violationService.getViolation(e);
        }
    }

    @PostMapping("add/episode")
    public String addCommentForEpisode(@RequestParam("userId") String userId,
                                       @RequestParam("episodeId") String episodeId,
                                       @RequestParam("comment") String commentParam) {
        try {
            Comment comment = new Comment();
            comment.setUser(userRepository.findUserById(Long.valueOf(userId)));
            comment.setEpisode(episodeRepository.findEpisodeById(Long.valueOf(episodeId)));
            comment.setComment(commentParam);
            commentRepository.save(comment);
            return "103";
        } catch (ConstraintViolationException e) {
            return "205 " + violationService.getViolation(e);
        }

    }
}
