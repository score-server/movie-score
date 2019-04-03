package ch.wetwer.moviedbapi.data.session;

import ch.wetwer.moviedbapi.data.DaoInterface;
import ch.wetwer.moviedbapi.data.user.User;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author Wetwer
 * @project movie-score
 */

@Service
public class SessionDao implements DaoInterface<Session> {

    private SessionRepository sessionRepository;

    public SessionDao(SessionRepository sessionRepository) {
        this.sessionRepository = sessionRepository;
    }

    @Override
    public Session getById(Long id) {
        return sessionRepository.getOne(id);
    }

    @Override
    public List<Session> getAll() {
        return sessionRepository.findAll();
    }

    @Override
    public void save(Session session) {
        sessionRepository.save(session);
    }

    public Session getBySessionId(String sessionId) {
        return sessionRepository.findSessionBySessionIdAndActive(sessionId, true);
    }

    public void deactivate(Session session) {
        session.setActive(false);
        save(session);
    }

    public List<Session> getByUser(User user) {
        return sessionRepository.findSessionsByUserAndActiveOrderByTimestamp(user, true);
    }
}
