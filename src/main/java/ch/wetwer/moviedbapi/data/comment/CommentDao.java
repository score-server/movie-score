package ch.wetwer.moviedbapi.data.comment;

import ch.wetwer.moviedbapi.data.DaoInterface;
import ch.wetwer.moviedbapi.data.episode.Episode;
import ch.wetwer.moviedbapi.data.movie.Movie;
import ch.wetwer.moviedbapi.data.user.User;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author Wetwer
 * @project movie-score
 */

@Service
public class CommentDao implements DaoInterface<Comment> {

    private CommentRepository commentRepository;

    public CommentDao(CommentRepository commentRepository) {
        this.commentRepository = commentRepository;
    }

    @Override
    public Comment getById(Long id) {
        return commentRepository.getOne(id);
    }

    @Override
    public List<Comment> getAll() {
        return commentRepository.findAll();
    }

    @Override
    public void save(Comment comment) {
        commentRepository.save(comment);
    }

    public void createMovieComment(User user, Movie movie, String commentParam) {
        Comment comment = new Comment();
        comment.setUser(user);
        comment.setMovie(movie);
        comment.setComment(commentParam);
        save(comment);
    }

    public void createEpisodeComment(User user, Episode episode, String commentParam) {
        Comment comment = new Comment();
        comment.setUser(user);
        comment.setEpisode(episode);
        comment.setComment(commentParam);
        save(comment);
    }
}
