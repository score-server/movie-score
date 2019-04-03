package ch.wetwer.moviedbapi.data;

import java.util.List;

/**
 * @author Wetwer
 * @project movie-score
 */

public interface DaoInterface<T> {

    T getById(Long id);

    List<T> getAll();

    void save(T t);

}
