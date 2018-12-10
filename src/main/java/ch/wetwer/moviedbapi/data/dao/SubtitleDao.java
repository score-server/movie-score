package ch.wetwer.moviedbapi.data.dao;

import ch.wetwer.moviedbapi.data.entity.Movie;
import ch.wetwer.moviedbapi.data.entity.Subtitle;
import ch.wetwer.moviedbapi.data.entity.User;
import ch.wetwer.moviedbapi.data.repository.SubtitleRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

/**
 * @author Wetwer
 * @project movie-db-api
 * @package ch.wetwer.moviedbapi.data.dao
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


    public void addSubtitle(Movie movie, MultipartFile multipartFile, String language, User user) throws IOException {
        Subtitle subtitle = new Subtitle();
        subtitle.setMovie(movie);
        subtitle.setUser(user);
        subtitle.setFile(multipartFile.getBytes());
        subtitle.setLanguage(language);
        subtitleRepository.save(subtitle);
    }

}
