package ch.felix.moviedbapi.service;

import ch.felix.moviedbapi.data.entity.Genre;
import ch.felix.moviedbapi.data.entity.Movie;
import ch.felix.moviedbapi.data.repository.MovieRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class SimilarMovieService {

    private MovieRepository movieRepository;

    public SimilarMovieService(MovieRepository movieRepository) {
        this.movieRepository = movieRepository;
    }

    public List<Movie> getSimilarMovies(Movie movie) {
        List<Movie> movies = new ArrayList<>();
        List<Movie> moviesByName = movieRepository.findMoviesByTitleContainingOrderByYearDesc(
                movie.getTitle().replace("the ", "").split(" ")[0]);
        for (Movie similarMovie : moviesByName) {
            if (similarMovie != movie) {
                if (movies.size() != 3) {
                    movies.add(similarMovie);
                    movies = removeDuplicates(movies);
                } else {
                    return movies;
                }
            }
        }

        List<Movie> moviesByGenre = movieRepository.findMoviesByTitleContainingOrderByYearDesc("");
        for (Movie similarMovie : moviesByGenre) {
            if (compareGenres(movie, similarMovie)) {
                if (similarMovie != movie) {
                    if (movies.size() != 3) {
                        movies.add(similarMovie);
                        movies = removeDuplicates(movies);
                    } else {
                        return movies;
                    }
                }
            }
        }
        return movies;
    }

    private List<Movie> removeDuplicates(List<Movie> movies) {
        Set<Movie> movieSet = new HashSet<>();
        movieSet.addAll(movies);
        movies.clear();
        movies.addAll(movieSet);
        return movies;
    }

    private boolean compareGenres(Movie movie, Movie movie2) {
        int common = 0;
        for (Genre genre : movie.getGenres()) {
            for (Genre genre2 : movie2.getGenres()) {
                if (genre.getName().equals(genre2.getName())) {
                    common++;
                    if (common == movie.getGenres().size()) {
                        return true;
                    }
                }
            }
        }
        return false;
    }
}
