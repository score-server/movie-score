package ch.wetwer.moviedbapi.service.auth;

import ch.wetwer.moviedbapi.data.session.Session;
import ch.wetwer.moviedbapi.data.session.SessionDao;
import ch.wetwer.moviedbapi.data.user.User;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.Date;

/**
 * @author Wetwer
 * @project movie-score
 */

@Service
public class SessionService {

    private SessionDao sessionDto;

    public SessionService(SessionDao sessionDto) {
        this.sessionDto = sessionDto;
    }

    public Session addSession(User user, String sessionId) {
        Session session = new Session();
        session.setSessionId(sessionId);
        session.setUser(user);
        session.setActive(true);
        session.setTimestamp(new Timestamp(new Date().getTime()));
        sessionDto.save(session);
        return session;
    }

    public void logout(String sessionId) {
        sessionDto.deactivate(sessionDto.getBySessionId(sessionId));
    }
}
