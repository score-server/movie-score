package ch.felix.moviedbapi.controller;

import ch.felix.moviedbapi.data.entity.Genre;
import ch.felix.moviedbapi.data.entity.MovieGenre;
import ch.felix.moviedbapi.data.repository.GenreRepository;
import ch.felix.moviedbapi.data.repository.MovieGenreRepository;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("genre")
public class GenreController {

    private GenreRepository genreRepository;
    private MovieGenreRepository movieGenreRepository;

    public GenreController(GenreRepository genreRepository, MovieGenreRepository movieGenreRepository) {
        this.genreRepository = genreRepository;
        this.movieGenreRepository = movieGenreRepository;
    }

    @GetMapping
    public @ResponseBody
    List<Genre> getGenres() {
        return genreRepository.findAll();
    }


    @GetMapping(value = "/movie/{movieId}", produces = "application/json")
    public @ResponseBody
    List<String> getGenresForMovie(@PathVariable("movieId") String movieId) {
        List<MovieGenre> movieGenres = movieGenreRepository.findMovieGenresByMovieId(Long.valueOf(movieId));
        List<String> genreList = new ArrayList<>();

        for (MovieGenre movieGenre : movieGenres) {
            genreList.add(genreRepository.findGenreById(movieGenre.getGenreId()).getName());
        }
        return genreList;
    }

}
