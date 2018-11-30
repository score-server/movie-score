package ch.wetwer.moviedbapi.data.dao;

import ch.wetwer.moviedbapi.data.entity.ListMovie;
import ch.wetwer.moviedbapi.data.repository.ListMovieRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ListMovieDao implements DaoInterface<ListMovie> {

    private ListMovieRepository listMovieRepository;

    public ListMovieDao(ListMovieRepository listMovieRepository) {
        this.listMovieRepository = listMovieRepository;
    }

    @Override
    public ListMovie getById(Long id) {
        return listMovieRepository.findListMovieById(id);
    }

    @Override
    public List<ListMovie> getAll() {
        return listMovieRepository.findAll();
    }

    @Override
    public void save(ListMovie listMovie) {
        listMovieRepository.save(listMovie);
    }

    public void delete(ListMovie listMovie) {
        listMovieRepository.delete(listMovie);
    }
}
