package ch.felix.moviedbapi.data.repository;

import ch.felix.moviedbapi.data.entity.ActivityLog;
import ch.felix.moviedbapi.data.entity.ImportLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author Wetwer
 * @project movie-db
 */

@Repository
public interface ActivityLogRepository extends JpaRepository<ActivityLog, Long> {

    List<ActivityLog> findAllByOrderByTimestampDesc();

}
