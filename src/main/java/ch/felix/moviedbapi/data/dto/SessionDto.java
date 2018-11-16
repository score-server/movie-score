package ch.felix.moviedbapi.data.dto;

import ch.felix.moviedbapi.data.entity.Session;
import ch.felix.moviedbapi.data.repository.SessionRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SessionDto implements DtoInterface<Session> {

    private SessionRepository sessionRepository;

    public SessionDto(SessionRepository sessionRepository) {
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
        return sessionRepository.findSessionBySessionId(sessionId);
    }

    public void delete(String sessionId) {
        sessionRepository.delete(sessionRepository.findSessionBySessionId(sessionId));
    }
}
