package ch.felix.moviedbapi.service.importer;


import ch.felix.moviedbapi.data.dto.GenreDto;
import ch.felix.moviedbapi.data.entity.Genre;
import ch.felix.moviedbapi.data.entity.Movie;
import ch.felix.moviedbapi.data.entity.Serie;
import ch.felix.moviedbapi.jsonmodel.tmdb.GenreJson;
import java.util.List;
import org.springframework.stereotype.Service;

/**
 * @author Wetwer
 * @project movie-db
 */
@Service
public class GenreImportService {

    private GenreDto genreDto;

    public GenreImportService(GenreDto genreDto) {
        this.genreDto = genreDto;
    }

    void setGenre(Movie movie, List<GenreJson> genreList) {
        for (GenreJson genreJson : genreList) {
            Genre genre = new Genre();
            genre.setName(genreJson.getName());
            genre.setMovie(movie);
            genreDto.save(genre);
        }

    }

    void setGenre(Serie serie, List<GenreJson> genreList) {
        for (GenreJson genreJson : genreList) {
            Genre genre = new Genre();
            genre.setName(genreJson.getName());
            genre.setSerie(serie);
            genreDto.save(genre);
        }

    }
}
