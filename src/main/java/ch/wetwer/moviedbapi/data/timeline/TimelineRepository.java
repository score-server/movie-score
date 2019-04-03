package ch.wetwer.moviedbapi.data.timeline;

import ch.wetwer.moviedbapi.data.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author Wetwer
 * @project movie-score
 */
@Repository
public interface TimelineRepository extends JpaRepository<Timeline, Long> {

    List<Timeline> findTimelinesByTitleContaining(String search);

    List<Timeline> findTimelinesByUser(User user);

    Timeline findTimelineById(Long id);

}
