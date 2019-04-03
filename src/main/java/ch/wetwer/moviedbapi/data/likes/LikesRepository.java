package ch.wetwer.moviedbapi.data.likes;

import ch.wetwer.moviedbapi.data.movie.Movie;
import ch.wetwer.moviedbapi.data.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author Wetwer
 * @project movie-score
 */
@Repository
public interface LikesRepository extends JpaRepository<Likes, Long> {

    Likes findLikeByUserAndMovie(User user, Movie movie);

    void deleteLikesByUser(User user);

}
