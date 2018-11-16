package ch.felix.moviedbapi.service;

import ch.felix.moviedbapi.data.dto.GenreDto;
import ch.felix.moviedbapi.data.dto.MovieDto;
import ch.felix.moviedbapi.data.dto.TimeDto;
import ch.felix.moviedbapi.data.dto.UserDto;
import ch.felix.moviedbapi.data.entity.Genre;
import ch.felix.moviedbapi.data.entity.Movie;
import ch.felix.moviedbapi.data.entity.Serie;
import ch.felix.moviedbapi.data.entity.User;
import ch.felix.moviedbapi.data.repository.MovieRepository;
import ch.felix.moviedbapi.data.repository.SerieRepository;
import ch.felix.moviedbapi.model.StartedVideo;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Wetwer
 * @project movie-db
 */
@Service
public class SearchService {

    private MovieDto movieDto;
    private TimeDto timeDto;
    private GenreDto genreDto;
    private UserDto userDto;
    private MovieRepository movieRepository;
    private SerieRepository serieRepository;

    private DuplicateService duplicateService;

    public SearchService(MovieRepository movieRepository, MovieDto movieDto, TimeDto timeDto, GenreDto genreDto,
                         UserDto userDto, SerieRepository serieRepository, DuplicateService duplicateService) {
        this.movieRepository = movieRepository;
        this.movieDto = movieDto;
        this.timeDto = timeDto;
        this.genreDto = genreDto;
        this.serieRepository = serieRepository;
        this.userDto = userDto;
        this.duplicateService = duplicateService;
    }

    public List<Movie> searchMovies(String searchParam, String orderByParam, String genreParam) {
        List<Movie> movies;
        List<Movie> movies2 = new ArrayList<>();

        switch (orderByParam) {
            case "":
                movies = movieRepository.findMoviesByTitleContainingOrderByPopularityDesc(searchParam);
                break;
            case "title":
                movies = movieRepository.findMoviesByTitleContainingOrderByTitle(searchParam);
                break;
            case "rating":
                movies = movieRepository.findMoviesByTitleContainingOrderByVoteAverageDesc(searchParam);
                break;
            case "year":
                movies = movieRepository.findMoviesByTitleContainingOrderByYearDesc(searchParam);
                break;
            case "recomended":
                movies = movieDto.searchRecomended(searchParam);
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

    public List<Movie> searchMoviesTop(String search, String orderBy) {
        List<Movie> movies;

        switch (orderBy) {
            case "":
                movies = movieRepository.findTop24ByTitleContainingOrderByPopularityDesc(search);
                break;
            case "title":
                movies = movieRepository.findTop24ByTitleContainingOrderByTitle(search);
                break;
            case "rating":
                movies = movieRepository.findTop24ByTitleContainingOrderByVoteAverageDesc(search);
                break;
            case "year":
                movies = movieRepository.findTop24ByTitleContainingOrderByYearDesc(search);
                break;
            default:
                movies = movieRepository.findTop24ByTitleContainingOrderByTitle(search);
                break;
        }
        return movies;
    }

    public List<Serie> searchSerie(String search, String genreParam) {
        List<Serie> series = serieRepository.findSeriesByTitleContainingOrderByPopularityDesc(search);
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

    public List<StartedVideo> findStartedVideos(User user) {
        return timeDto.getStartedMovies(user);
    }

    public List<Serie> searchSerieTop(String search) {
        return serieRepository.findTop24ByTitleContainingOrderByPopularityDesc(search);
    }


    public List<User> searchUser(String search) {
        return userDto.search(search);
    }

    public List<String> getGenres(GenreSearchType type) {
        List<Genre> genreList = new ArrayList<>();
        switch (type) {
            case ALL:
                genreList = genreDto.getAll();
                break;
            case MOVIE:
                genreList = genreDto.getForMovies();
                break;
            case SERIE:
                genreList = genreDto.getForSeries();
                break;
        }

        List<String> genres = new ArrayList<>();
        for (Genre genre : genreList) {
            genres.add(genre.getName());
        }
        genres = duplicateService.removeStringDuplicates(genres);
        genres.sort(String::compareToIgnoreCase);
        return genres;
    }

}
