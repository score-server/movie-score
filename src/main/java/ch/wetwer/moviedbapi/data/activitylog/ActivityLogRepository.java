package ch.wetwer.moviedbapi.data.activitylog;

import ch.wetwer.moviedbapi.data.user.User;
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

    List<ActivityLog> findActivityLogsByUserOrderByTimestampDesc(User user);

    void deleteActivityLogsByUser(User user);

}
