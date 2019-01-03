package ch.wetwer.moviedbapi.data.subtitle;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author Wetwer
 * @project movie-db
 */

@Repository
public interface SubtitleRepository extends JpaRepository<Subtitle, Long> {
}
