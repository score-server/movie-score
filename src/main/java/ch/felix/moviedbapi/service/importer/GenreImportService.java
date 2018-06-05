package ch.felix.moviedbapi.service.importer;


import ch.felix.moviedbapi.data.entity.Genre;
import ch.felix.moviedbapi.data.entity.Movie;
import ch.felix.moviedbapi.data.entity.Serie;
import ch.felix.moviedbapi.data.repository.GenreRepository;
import ch.felix.moviedbapi.jsonmodel.tmdb.GenreJson;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class GenreImportService {

    private GenreRepository genreRepository;

    public GenreImportService(GenreRepository genreRepository) {
        this.genreRepository = genreRepository;
    }

    public void setGenre(Movie movie, List<GenreJson> genreList) {
        for (GenreJson genreJson : genreList) {
            Genre genre = new Genre();
            genre.setName(genreJson.getName());
            genre.setMovie(movie);
            genreRepository.save(genre);
        }

    }

    public void setGenre(Serie serie, List<GenreJson> genreList) {
        for (GenreJson genreJson : genreList) {
            Genre genre = new Genre();
            genre.setName(genreJson.getName());
            genre.setSerie(serie);
            genreRepository.save(genre);
        }

    }
}
