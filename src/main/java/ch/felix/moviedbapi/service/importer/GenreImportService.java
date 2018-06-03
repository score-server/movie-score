package ch.felix.moviedbapi.service.importer;


import ch.felix.moviedbapi.data.entity.Genre;
import ch.felix.moviedbapi.data.entity.MovieGenre;
import ch.felix.moviedbapi.data.repository.GenreRepository;
import ch.felix.moviedbapi.data.repository.MovieGenreRepository;
import ch.felix.moviedbapi.jsonmodel.GenreJson;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GenreImportService {

    private GenreRepository genreRepository;
    private MovieGenreRepository movieGenreRepository;

    public GenreImportService(GenreRepository genreRepository, MovieGenreRepository movieGenreRepository) {
        this.genreRepository = genreRepository;
        this.movieGenreRepository = movieGenreRepository;
    }

    public void setGenre(Integer id, List<GenreJson> genreList) {
        for (GenreJson genreJson : genreList) {
            if (genreRepository.findGenreByName(genreJson.getName()) == null) {
                Genre genre = new Genre();
                genre.setName(genreJson.getName());
                genreRepository.save(genre);
            }

            MovieGenre movieGenre = new MovieGenre();
            movieGenre.setMovieId((long) id);
            movieGenre.setGenreId(genreRepository.findGenreByName(genreJson.getName()).getId());
            movieGenreRepository.save(movieGenre);
        }

    }
}
