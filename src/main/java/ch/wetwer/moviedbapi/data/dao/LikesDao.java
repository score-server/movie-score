package ch.wetwer.moviedbapi.data.dao;

import ch.wetwer.moviedbapi.data.entity.Likes;
import ch.wetwer.moviedbapi.data.entity.Movie;
import ch.wetwer.moviedbapi.data.entity.User;
import ch.wetwer.moviedbapi.data.repository.LikesRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LikesDao implements DaoInterface<Likes> {

    private LikesRepository likesRepository;

    public LikesDao(LikesRepository likesRepository) {
        this.likesRepository = likesRepository;
    }

    @Override
    public Likes getById(Long id) {
        return likesRepository.getOne(id);
    }

    @Override
    public List<Likes> getAll() {
        return likesRepository.findAll();
    }

    @Override
    public void save(Likes likes) {
        likesRepository.save(likes);
    }

    public Likes getByUserAndMovie(User user, Movie movie) {
        return likesRepository.findLikeByUserAndMovie(user, movie);
    }

    public void delete(Likes likes) {
        likesRepository.delete(likes);
    }
}
