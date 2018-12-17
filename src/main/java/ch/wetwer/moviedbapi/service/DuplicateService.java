package ch.wetwer.moviedbapi.service;

import ch.wetwer.moviedbapi.data.entity.Movie;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @author Wetwer
 * @project movie-db
 */
@Service
public class DuplicateService {

    public List<String> removeStringDuplicates(List<String> list) {
        Set<String> movieSet = new HashSet<>(list);
        list.clear();
        list.addAll(movieSet);
        return list;
    }

    public List<Movie> removeMovieDuplicates(List<Movie> movies) {
        Set<Movie> movieSet = new HashSet<>(movies);
        movies.clear();
        movies.addAll(movieSet);
        return movies;
    }


}
