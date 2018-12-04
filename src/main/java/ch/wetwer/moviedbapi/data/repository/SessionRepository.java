package ch.wetwer.moviedbapi.data.repository;

import ch.wetwer.moviedbapi.data.entity.Session;
import ch.wetwer.moviedbapi.data.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SessionRepository extends JpaRepository<Session, Long> {

    Session findSessionBySessionIdAndActive(String sessionId, Boolean active);

    List<Session> findSessionsByUserAndActiveOrderByTimestamp(User user, Boolean active);

}
