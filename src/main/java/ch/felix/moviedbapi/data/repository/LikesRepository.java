package ch.felix.moviedbapi.data.repository;

import ch.felix.moviedbapi.data.entity.Likes;
import ch.felix.moviedbapi.data.entity.Movie;
import ch.felix.moviedbapi.data.entity.User;
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
