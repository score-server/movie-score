package ch.felix.moviedbapi.controller;

import ch.felix.moviedbapi.data.entity.Genre;
import ch.felix.moviedbapi.data.entity.Movie;
import ch.felix.moviedbapi.data.repository.GenreRepository;
import ch.felix.moviedbapi.data.repository.MovieRepository;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

/**
 * @author Felix
 * @date 23.05.2018
 * <p>
 * Project: movie-db-api
 * Package: ch.felix.moviedbapi.controller
 **/

@Controller
@RequestMapping("movie")
public class MovieController {

    private MovieRepository movieRepository;
    private GenreRepository genreRepository;

    public MovieController(MovieRepository movieRepository, GenreRepository genreRepository) {
        this.movieRepository = movieRepository;
        this.genreRepository = genreRepository;
    }

    @GetMapping(produces = "application/json")
    public String getMovies(Model model) {
        try {
            model.addAttribute("movies", movieRepository.findAll());
            model.addAttribute("page", "movieList");
            return "template";
        } catch (NullPointerException e) {
            return "redirect:/";//No Movies found
        }
    }

    @GetMapping(value = "/{movieId}", produces = "application/json")
    public String getOneMovie(@PathVariable("movieId") String movieId, Model model) {
        try {
            model.addAttribute("movie", movieRepository.findMovieById(Long.valueOf(movieId)));
            model.addAttribute("comments", movieRepository.findMovieById(Long.valueOf(movieId)).getComments());
            model.addAttribute("page", "movie");
            return "template";
        } catch (NullPointerException | NumberFormatException e) {
            e.printStackTrace();
            return "redirect:/movie";
        }
    }

    @GetMapping(value = "/search", produces = "application/json")
    public String searchMovie(@RequestParam("search") String search, Model model) {
        try {
            model.addAttribute("movies", movieRepository.findMoviesByTitleContaining(search));
            model.addAttribute("page", "searchMovie");
            return "template";
        } catch (NumberFormatException | NullPointerException e) {
            e.printStackTrace();
            return "redirect:/movie";
        }
    }

    @GetMapping(value = "/genre/{genre}", produces = "application/json")
    public List<Movie> getMoviesForGenre(@PathVariable("genre") String genreParam, Model model) {
        List<Genre> genres = genreRepository.findGenresByName(genreParam);

        List<Movie> movieList = new ArrayList<>();
        for (Genre genre : genres) {
            movieList.add(genre.getMovie());
        }
        model.addAttribute("movies", movieList);
        //TODO GENRE PAGE
        return movieList;
    }
}
