package ch.wetwer.moviedbapi.data.genre;

import ch.wetwer.moviedbapi.data.DaoInterface;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author Wetwer
 * @project movie-score
 */

@Service
public class GenreDao implements DaoInterface<Genre> {

    private GenreRepository genreRepository;

    public GenreDao(GenreRepository genreRepository) {
        this.genreRepository = genreRepository;
    }

    @Override
    public Genre getById(Long id) {
        return genreRepository.getOne(id);
    }

    @Override
    public List<Genre> getAll() {
        return genreRepository.findGenreByOrderByName();
    }

    @Override
    public void save(Genre genre) {
        genreRepository.save(genre);
    }

    public List<Genre> getForMovies() {
        return genreRepository.findGenresByMovieNotNullOrderByName();
    }

    public List<Genre> getForSeries() {
        return genreRepository.findGenresBySerieNotNullOrderByName();
    }
}
