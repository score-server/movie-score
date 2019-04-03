package ch.wetwer.moviedbapi.data.comment;

import ch.wetwer.moviedbapi.data.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


/**
 * @author Wetwer
 * @project movie-score
 */
@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {

    void deleteCommentByUser(User user);

}
