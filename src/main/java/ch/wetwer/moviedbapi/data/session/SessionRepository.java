package ch.wetwer.moviedbapi.data.session;

import ch.wetwer.moviedbapi.data.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * @author Wetwer
 * @project movie-score
 */

public interface SessionRepository extends JpaRepository<Session, Long> {

    Session findSessionBySessionIdAndActive(String sessionId, Boolean active);

    List<Session> findSessionsByUserAndActiveOrderByTimestamp(User user, Boolean active);

}
