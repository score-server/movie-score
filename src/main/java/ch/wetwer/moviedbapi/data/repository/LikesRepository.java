package ch.wetwer.moviedbapi.data.repository;

import ch.wetwer.moviedbapi.data.entity.Likes;
import ch.wetwer.moviedbapi.data.entity.Movie;
import ch.wetwer.moviedbapi.data.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author Wetwer
 * @project movie-db
 */
@Repository
public interface LikesRepository extends JpaRepository<Likes, Long> {

    Likes findLikeByUserAndMovie(User user, Movie movie);

    void deleteLikesByUser(User user);

}
