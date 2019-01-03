package ch.wetwer.moviedbapi.data.movie;

import ch.wetwer.moviedbapi.data.DaoInterface;
import com.google.common.collect.Lists;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@Service
public class MovieDao implements DaoInterface<Movie> {

    private MovieRepository movieRepository;

    public MovieDao(MovieRepository movieRepository) {
        this.movieRepository = movieRepository;
    }

    @Override
    public Movie getById(Long id) {
        return movieRepository.findMovieById(id);
    }

    @Override
    public List<Movie> getAll() {
        return movieRepository.findMoviesByOrderByTitle();
    }

    public List<Movie> searchTop10(String search) {
        return movieRepository.findTop10ByTitleContainingOrderByPopularityDesc(search);
    }

    @Override
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

    public List<Movie> getOrderByTitle() {
        return movieRepository.findMoviesByOrderByTitle();
    }

    public Movie getByTitle(String name) {
        return movieRepository.findMovieByTitle(name);
    }

    public List<Movie> getLatestInfo() {
        return movieRepository.findTop3ByOrderByTimestampDesc();
    }
}
