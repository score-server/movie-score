package ch.felix.moviedbapi.data.dto;

import org.springframework.stereotype.Service;

import java.util.List;

public interface DtoInterface<T> {

    T getById(Long id);

    List<T> getAll();

    void save(T t);

}
