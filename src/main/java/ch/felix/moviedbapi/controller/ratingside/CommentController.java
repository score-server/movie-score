package ch.felix.moviedbapi.controller.ratingside;

import ch.felix.moviedbapi.data.entity.Comment;
import ch.felix.moviedbapi.data.repository.CommentRepository;
import ch.felix.moviedbapi.data.repository.EpisodeRepository;
import ch.felix.moviedbapi.data.repository.MovieRepository;
import ch.felix.moviedbapi.data.repository.UserRepository;
import java.util.List;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author Felix
 * @date 05.06.2018
 * <p>
 * Project: movie-db-api
 * Package: ch.felix.moviedbapi.controller
 **/

@Controller
@RequestMapping("comment")
public class CommentController {

    private MovieRepository movieRepository;
    private EpisodeRepository episodeRepository;
    private UserRepository userRepository;
    private CommentRepository commentRepository;

    public CommentController(MovieRepository movieRepository, EpisodeRepository episodeRepository,
                             UserRepository userRepository, CommentRepository commentRepository) {
        this.movieRepository = movieRepository;
        this.episodeRepository = episodeRepository;
        this.userRepository = userRepository;
        this.commentRepository = commentRepository;
    }

    @GetMapping("movie/{movieId}")
    public @ResponseBody
    List<Comment> getForMovie(@PathVariable("movieId") String movieId) {
        return movieRepository.findMovieById(Long.valueOf(movieId)).getComments();
    }

    @GetMapping("episode/{episodeId}")
    public @ResponseBody
    List<Comment> getForEpisode(@PathVariable("episodeId") String episodeId) {
        return episodeRepository.findEpisodeById(Long.valueOf(episodeId)).getComments();
    }

    @PostMapping("add/movie")
    public @ResponseBody
    String addCommentForMovie(@RequestParam("userId") String userId,
                              @RequestParam("movieId") String movieId,
                              @RequestParam("comment") String commentParam) {
        Comment comment = new Comment();
        comment.setUser(userRepository.findUserById(Long.valueOf(userId)));
        comment.setMovie(movieRepository.findMovieById(Long.valueOf(movieId)));
        comment.setComment(commentParam);
        commentRepository.save(comment);
        return "101";
    }

    @PostMapping("add/serie")
    public @ResponseBody
    String addCommentForEpisode(@RequestParam("userId") String userId,
                                @RequestParam("episodeId") String episodeId,
                                @RequestParam("comment") String commentParam) {
        Comment comment = new Comment();
        comment.setUser(userRepository.findUserById(Long.valueOf(userId)));
        comment.setEpisode(episodeRepository.findEpisodeById(Long.valueOf(episodeId)));
        comment.setComment(commentParam);
        commentRepository.save(comment);
        return "101";
    }
}
