package ch.wetwer.moviedbapi.data.request;

import ch.wetwer.moviedbapi.data.DaoInterface;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author Wetwer
 * @project movie-score
 */

@Service
public class RequestDao implements DaoInterface<Request> {

    private RequestRepository requestRepository;

    public RequestDao(RequestRepository requestRepository) {
        this.requestRepository = requestRepository;
    }

    @Override
    public Request getById(Long id) {
        return requestRepository.findRequestById(id);
    }

    @Override
    public List<Request> getAll() {
        return requestRepository.findAllByOrderByActiveDescRequest();
    }

    @Override
    public void save(Request request) {
        requestRepository.save(request);
    }

    public void delete(Request request) {
        requestRepository.delete(request);
    }
}
