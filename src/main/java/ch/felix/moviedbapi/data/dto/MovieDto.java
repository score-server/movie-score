package ch.felix.moviedbapi.data.dto;

import ch.felix.moviedbapi.data.entity.Movie;
import ch.felix.moviedbapi.data.repository.MovieRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MovieDto {

    private MovieRepository movieRepository;

    public MovieDto(MovieRepository movieRepository) {
        this.movieRepository = movieRepository;
    }

    public Movie getById(Long id) {
        return movieRepository.findMovieById(id);
    }

    public List<Movie> getAll() {
        return movieRepository.findAll();
    }

    public List<Movie> searchTop10(String search) {
        return movieRepository.findTop10ByTitleContainingOrderByPopularityDesc(search);
    }

    public void save(Movie movie) {
        movieRepository.save(movie);
    }
}
