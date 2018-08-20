package ch.felix.moviedbapi.data.repository;

import ch.felix.moviedbapi.data.entity.Nice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author Wetwer
 * @project movie-db
 */
@Repository
public interface NiceRepository extends JpaRepository<Nice, Long> {

    Nice findNiceById(Long id);

}
