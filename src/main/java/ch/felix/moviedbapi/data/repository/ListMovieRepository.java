package ch.felix.moviedbapi.data.repository;

import ch.felix.moviedbapi.data.entity.ListMovie;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author Wetwer
 * @project movie-db
 */
@Repository
public interface ListMovieRepository extends JpaRepository<ListMovie, Long> {

    ListMovie findListMovieById(Long id);

}
