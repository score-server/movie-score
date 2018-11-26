package ch.felix.moviedbapi.data.repository;

import ch.felix.moviedbapi.data.entity.Subtitle;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author Wetwer
 * @project movie-db
 */

@Repository
public interface SubtitleRepository extends JpaRepository<Subtitle, Long> {
}
