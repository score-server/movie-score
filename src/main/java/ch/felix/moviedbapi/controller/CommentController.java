package ch.felix.moviedbapi.controller;

import ch.felix.moviedbapi.data.dto.MovieDto;
import ch.felix.moviedbapi.data.entity.Comment;
import ch.felix.moviedbapi.data.repository.CommentRepository;
import ch.felix.moviedbapi.data.repository.EpisodeRepository;
import ch.felix.moviedbapi.model.UserIndicator;
import ch.felix.moviedbapi.service.UserIndicatorService;
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

    private MovieDto movieDto;
    private EpisodeRepository episodeRepository;
    private CommentRepository commentRepository;

    private UserIndicatorService userIndicatorService;

    public CommentController(MovieDto movieDto, EpisodeRepository episodeRepository,
                             CommentRepository commentRepository, UserIndicatorService userIndicatorService) {
        this.movieDto = movieDto;
        this.episodeRepository = episodeRepository;
        this.commentRepository = commentRepository;
        this.userIndicatorService = userIndicatorService;
    }

    @PostMapping("add/movie")
    public String addCommentForMovie(@RequestParam("movieId") String movieId,
                                     @RequestParam("comment") String commentParam, HttpServletRequest request) {

        if (userIndicatorService.isUser(request)) {
            UserIndicator userIndicator = userIndicatorService.getUser(request);
            Comment comment = new Comment();
            comment.setUser(userIndicator.getUser());
            comment.setMovie(movieDto.getById(Long.valueOf(movieId)));
            comment.setComment(commentParam);
            commentRepository.save(comment);
            return "redirect:/movie/" + movieId;
        } else {
            return "redirect:/movie/" + movieId + "?error";
        }
    }

    @PostMapping("add/episode")
    public String addCommentForEpisode(@RequestParam("episodeId") String episodeId,
                                       @RequestParam("comment") String commentParam, HttpServletRequest request) {
        if (userIndicatorService.isUser(request)) {
            UserIndicator userIndicator = userIndicatorService.getUser(request);
            Comment comment = new Comment();
            comment.setUser(userIndicator.getUser());
            comment.setEpisode(episodeRepository.findEpisodeById(Long.valueOf(episodeId)));
            comment.setComment(commentParam);
            commentRepository.save(comment);
            return "redirect:/episode/" + episodeId;
        } else {
            return "redirect:/episode/" + episodeId + "?error";
        }
    }
}
