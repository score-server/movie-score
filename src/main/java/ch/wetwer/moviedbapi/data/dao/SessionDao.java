package ch.wetwer.moviedbapi.data.dao;

import ch.wetwer.moviedbapi.data.entity.Session;
import ch.wetwer.moviedbapi.data.repository.SessionRepository;
import org.springframework.stereotype.Service;

import java.util.List;

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
}
