package ch.felix.moviedbapi.data.repository;

import ch.felix.moviedbapi.data.entity.Session;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SessionRepository extends JpaRepository<Session, Long> {

    Session findSessionBySessionId(String sessionId);

}
