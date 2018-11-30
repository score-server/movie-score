package ch.wetwer.moviedbapi.data.repository;

import ch.wetwer.moviedbapi.data.entity.Comment;
import ch.wetwer.moviedbapi.data.entity.User;
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
