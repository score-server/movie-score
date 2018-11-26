package ch.felix.moviedbapi.data.dao;

import ch.felix.moviedbapi.data.entity.Subtitle;
import ch.felix.moviedbapi.data.repository.SubtitleRepository;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author Wetwer
 * @project movie-db-api
 * @package ch.felix.moviedbapi.data.dao
 * @created 26.11.2018
 **/
@Service
public class SubtitleDao implements DaoInterface<Subtitle> {

    private SubtitleRepository subtitleRepository;

    public SubtitleDao(SubtitleRepository subtitleRepository) {
        this.subtitleRepository = subtitleRepository;
    }

    @Override
    public Subtitle getById(Long id) {
        return subtitleRepository.getOne(id);
    }

    @Override
    public List<Subtitle> getAll() {
        return subtitleRepository.findAll();
    }

    @Override
    public void save(Subtitle subtitle) {
        subtitleRepository.save(subtitle);
    }
}
