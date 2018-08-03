package ch.felix.moviedbapi.service;

import ch.felix.moviedbapi.data.entity.Genre;
import ch.felix.moviedbapi.data.entity.Movie;
import ch.felix.moviedbapi.data.entity.Serie;
import ch.felix.moviedbapi.data.entity.User;
import ch.felix.moviedbapi.data.repository.MovieRepository;
import ch.felix.moviedbapi.data.repository.SerieRepository;
import ch.felix.moviedbapi.data.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
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

    public List<Movie> searchMovies(String searchParam, String orderByParam, String genreParam) {
        List<Movie> movies;
        List<Movie> movies2 = new ArrayList<>();

        switch (orderByParam) {
            case "":
                movies = movieRepository.findMoviesByTitleContainingOrderByTitle(searchParam);
                break;
            case "rating":
                movies = movieRepository.findMoviesByTitleContainingOrderByVoteAverageDesc(searchParam);
                break;
            case "year":
                movies = movieRepository.findMoviesByTitleContainingOrderByYearDesc(searchParam);
                break;
            default:
                movies = movieRepository.findMoviesByTitleContainingOrderByTitle(searchParam);
                break;
        }

        if (genreParam.equals("")) {
            return movies;
        } else {
            for (Movie movie : movies) {
                for (Genre genre : movie.getGenres()) {
                    if (genre.getName().equals(genreParam)) {
                        movies2.add(movie);
                    }
                }
            }
        }
        return movies2;
    }

    public List<Movie> searchMoviesTop(String search, String orderBy, String genreParam) {
        List<Movie> movies;
        List<Movie> movies2 = new ArrayList<>();

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

        if (genreParam.equals("")) {
            return movies;
        } else {
            for (Movie movie : movies) {
                for (Genre genre : movie.getGenres()) {
                    if (genre.getName().equals(genreParam)) {
                        movies2.add(movie);
                    }
                }
            }
        }
        return movies2;
    }

    public List<Serie> searchSerie(String search) {
        return serieRepository.findSeriesByTitleContainingOrderByTitle(search);
    }

    public List<Serie> searchSerie(String search, String genreParam) {
        List<Serie> series = serieRepository.findSeriesByTitleContainingOrderByTitle(search);
        List<Serie> series2 = new ArrayList<>();

        if (genreParam.equals("")) {
            return series;
        } else {
            for (Serie serie : series) {
                for (Genre genre : serie.getGenres()) {
                    if (genre.getName().equals(genreParam)) {
                        series2.add(serie);
                    }
                }
            }
            return series2;
        }
    }

    public List<Serie> searchSerieTop(String search) {
        return serieRepository.findTop25ByTitleContainingOrderByTitle(search);
    }

    public List<Serie> searchSerieTop(String search, String genreParam) {
        List<Serie> series = serieRepository.findTop25ByTitleContainingOrderByTitle(search);
        List<Serie> series2 = new ArrayList<>();

        if (genreParam.equals("")) {
            return series;
        } else {
            for (Serie serie : series) {
                for (Genre genre : serie.getGenres()) {
                    if (genre.getName().equals(genreParam)) {
                        series2.add(serie);
                    }
                }
            }
        }
        return series2;
    }


    public List<User> searchUser(String search) {
        return userRepository.findUsersByNameContaining(search);
    }
}
