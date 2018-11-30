package ch.wetwer.moviedbapi.service.importer;


import ch.wetwer.moviedbapi.data.dao.GenreDao;
import ch.wetwer.moviedbapi.data.entity.Genre;
import ch.wetwer.moviedbapi.data.entity.Movie;
import ch.wetwer.moviedbapi.data.entity.Serie;
import ch.wetwer.moviedbapi.model.tmdb.GenreJson;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author Wetwer
 * @project movie-db
 */
@Service
public class GenreImportService {

    private GenreDao genreDao;

    public GenreImportService(GenreDao genreDao) {
        this.genreDao = genreDao;
    }

    public void setGenre(Movie movie, List<GenreJson> genreList) {
        for (GenreJson genreJson : genreList) {
            Genre genre = new Genre();
            genre.setName(genreJson.getName());
            genre.setMovie(movie);
            genreDao.save(genre);
        }

    }

    public void setGenre(Serie serie, List<GenreJson> genreList) {
        for (GenreJson genreJson : genreList) {
            Genre genre = new Genre();
            genre.setName(genreJson.getName());
            genre.setSerie(serie);
            genreDao.save(genre);
        }

    }
}
