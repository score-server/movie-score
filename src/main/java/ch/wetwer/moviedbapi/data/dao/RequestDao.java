package ch.wetwer.moviedbapi.data.dao;

import ch.wetwer.moviedbapi.data.entity.Request;
import ch.wetwer.moviedbapi.data.repository.RequestRepository;
import org.springframework.stereotype.Service;

import java.util.List;

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
        return requestRepository.findAllByOrderByActiveDesc();
    }

    @Override
    public void save(Request request) {
        requestRepository.save(request);
    }

    public void delete(Request request) {
        requestRepository.delete(request);
    }
}
