package ch.wetwer.moviedbapi.data.announcement;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * @author Wetwer
 * @project movie-score
 * @package ch.wetwer.moviedbapi.data.announcement
 * @created 29.04.2019
 **/
public interface AnnouncementRepository extends JpaRepository<Announcement, Long> {

}
