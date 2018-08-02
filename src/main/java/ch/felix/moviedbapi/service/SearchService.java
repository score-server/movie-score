package ch.felix.moviedbapi.service;

import ch.felix.moviedbapi.data.entity.Movie;
import ch.felix.moviedbapi.data.entity.Serie;
import ch.felix.moviedbapi.data.entity.User;
import ch.felix.moviedbapi.data.repository.MovieRepository;
import ch.felix.moviedbapi.data.repository.SerieRepository;
import ch.felix.moviedbapi.data.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author Wetwer
 * @project movie-db
 */
@Service
public class SearchService {

    private MovieRepository movieRepository;
    private SerieRepository serieRepository;
    private UserRepository userRepository;

    public SearchService(MovieRepository movieRepository, SerieRepository serieRepository,
                         UserRepository userRepository) {
        this.movieRepository = movieRepository;
        this.serieRepository = serieRepository;
        this.userRepository = userRepository;
    }

    public List<Movie> searchMovies(String search, String orderBy) {
        List<Movie> movies;

        switch (orderBy) {
            case "":
                movies = movieRepository.findMoviesByTitleContainingOrderByTitle(search);
                break;
            case "rating":
                movies = movieRepository.findMoviesByTitleContainingOrderByVoteAverageDesc(search);
                break;
            case "year":
                movies = movieRepository.findMoviesByTitleContainingOrderByYearDesc(search);
                break;
            default:
                movies = movieRepository.findMoviesByTitleContainingOrderByTitle(search);
                break;
        }

        return movies;
    }

    public List<Movie> searchMoviesTop(String search, String orderBy) {
        List<Movie> movies;

        switch (orderBy) {
            case "":
                movies = movieRepository.findTop25ByTitleContainingOrderByTitle(search);
                break;
            case "rating":
                movies = movieRepository.findTop25ByTitleContainingOrderByVoteAverageDesc(search);
                break;
            case "year":
                movies = movieRepository.findTop25ByTitleContainingOrderByYearDesc(search);
                break;
            default:
                movies = movieRepository.findTop25ByTitleContainingOrderByTitle(search);
                break;
        }

        return movies;
    }

    public List<Serie> searchSerie(String search) {
        return serieRepository.findSeriesByTitleContainingOrderByTitle(search);
    }

    public List<Serie> searchSerieTop(String search) {
        return serieRepository.findTop25ByTitleContainingOrderByTitle(search);
    }


    public List<User> searchUser(String search) {
        return userRepository.findUsersByNameContaining(search);
    }
}
