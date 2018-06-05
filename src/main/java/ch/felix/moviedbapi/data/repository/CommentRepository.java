package ch.felix.moviedbapi.data.repository;

import ch.felix.moviedbapi.data.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author Felix
 * @date 05.06.2018
 * <p>
 * Project: movie-db-api
 * Package: ch.felix.moviedbapi.data.repository
 **/
public interface CommentRepository extends JpaRepository<Comment, Long> {

    Comment findCommentById(Long id);

}
