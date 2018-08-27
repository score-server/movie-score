package ch.felix.moviedbapi.data.repository;

import ch.felix.moviedbapi.data.entity.Comment;
import ch.felix.moviedbapi.data.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


/**
 * @author Wetwer
 * @project movie-db
 */
@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {

    void deleteCommentByUser(User user);

}
