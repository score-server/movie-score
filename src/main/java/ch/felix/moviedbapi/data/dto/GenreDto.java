package ch.felix.moviedbapi.data.dto;

import ch.felix.moviedbapi.data.entity.Genre;
import ch.felix.moviedbapi.data.repository.GenreRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GenreDto implements DtoInterface<Genre> {

    private GenreRepository genreRepository;

    public GenreDto(GenreRepository genreRepository) {
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
}
