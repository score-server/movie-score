package ch.felix.moviedbapi.data.repository;

import ch.felix.moviedbapi.data.entity.Timeline;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TimelineRepository extends JpaRepository<Timeline, Long> {

    List<Timeline> findTimelinesByTitleContaining(String search);

    Timeline findTimelineById(Long id);

}
