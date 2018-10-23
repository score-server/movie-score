package ch.felix.moviedbapi.data.dto;

import ch.felix.moviedbapi.data.entity.Movie;
import ch.felix.moviedbapi.data.repository.MovieRepository;
import com.google.common.collect.Lists;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
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

    public List<Movie> searchRecomended(String search) {
        List<Movie> recomendedMovies = new ArrayList<>();
        List<Movie> movies = movieRepository.findMoviesByTitleContainingOrderByPopularityDesc(search);
        for (Movie movie : movies) {
            if (movie.getLikes().size() > 0) {
                recomendedMovies.add(movie);
            }
        }
        recomendedMovies.sort(Comparator.comparing(s -> s.getLikes().size()));
        recomendedMovies = Lists.reverse(recomendedMovies);
        return recomendedMovies;
    }
}
