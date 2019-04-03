package ch.wetwer.moviedbapi.data.likes;

import ch.wetwer.moviedbapi.data.DaoInterface;
import ch.wetwer.moviedbapi.data.movie.Movie;
import ch.wetwer.moviedbapi.data.user.User;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author Wetwer
 * @project movie-score
 */

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
