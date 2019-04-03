package ch.wetwer.moviedbapi.data.timeline;

import ch.wetwer.moviedbapi.data.DaoInterface;
import ch.wetwer.moviedbapi.data.user.User;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author Wetwer
 * @project movie-score
 */

@Service
public class TimeLineDao implements DaoInterface<Timeline> {

    private TimelineRepository timelineRepository;

    public TimeLineDao(TimelineRepository timelineRepository) {
        this.timelineRepository = timelineRepository;
    }

    @Override
    public Timeline getById(Long id) {
        return timelineRepository.findTimelineById(id);
    }

    @Override
    public List<Timeline> getAll() {
        return timelineRepository.findAll();
    }

    @Override
    public void save(Timeline timeline) {
        timelineRepository.save(timeline);
    }

    public List<Timeline> searchTimeLine(String search) {
        return timelineRepository.findTimelinesByTitleContaining(search);
    }

    public void delete(Timeline timeline) {
        timelineRepository.delete(timeline);
    }

    public List<Timeline> getByUser(User user) {
        return timelineRepository.findTimelinesByUser(user);
    }
}
