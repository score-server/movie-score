package ch.felix.moviedbapi.data.dto;

import ch.felix.moviedbapi.data.entity.Request;
import ch.felix.moviedbapi.data.repository.RequestRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RequestDto implements DtoInterface<Request> {

    private RequestRepository requestRepository;

    public RequestDto(RequestRepository requestRepository) {
        this.requestRepository = requestRepository;
    }

    @Override
    public Request getById(Long id) {
        return requestRepository.findRequestById(id);
    }

    @Override
    public List<Request> getAll() {
        return getAll();
    }

    @Override
    public void save(Request request) {
        requestRepository.save(request);
    }

    public void delete(Request request) {
        requestRepository.delete(request);
    }
}
