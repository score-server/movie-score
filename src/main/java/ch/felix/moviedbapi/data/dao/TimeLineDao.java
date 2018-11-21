package ch.felix.moviedbapi.data.dao;

import ch.felix.moviedbapi.data.entity.Timeline;
import ch.felix.moviedbapi.data.entity.User;
import ch.felix.moviedbapi.data.repository.TimelineRepository;
import org.springframework.stereotype.Service;

import java.util.List;

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
