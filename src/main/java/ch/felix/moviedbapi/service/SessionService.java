package ch.felix.moviedbapi.service;

import ch.felix.moviedbapi.data.dto.SessionDto;
import ch.felix.moviedbapi.data.entity.Session;
import ch.felix.moviedbapi.data.entity.User;
import org.springframework.stereotype.Service;

@Service
public class SessionService {

    private SessionDto sessionDto;

    public SessionService(SessionDto sessionDto) {
        this.sessionDto = sessionDto;
    }

    public void addSession(User user, String sessionId) {
        Session session = new Session();
        session.setSessionId(sessionId);
        session.setUser(user);
        sessionDto.save(session);
    }

    public void logout(String sessionId) {
        sessionDto.delete(sessionId);
    }
}
