package ch.felix.moviedbapi.data.repository;

import ch.felix.moviedbapi.data.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;


/**
 * @author Wetwer
 * @project movie-db
 */
public interface CommentRepository extends JpaRepository<Comment, Long> {

    Comment findCommentById(Long id);

}
