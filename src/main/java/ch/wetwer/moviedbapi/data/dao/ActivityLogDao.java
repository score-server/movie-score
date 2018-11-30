package ch.wetwer.moviedbapi.data.dao;

import ch.wetwer.moviedbapi.data.entity.ActivityLog;
import ch.wetwer.moviedbapi.data.entity.User;
import ch.wetwer.moviedbapi.data.repository.ActivityLogRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ActivityLogDao implements DaoInterface<ActivityLog> {

    private ActivityLogRepository activityLogRepository;

    public ActivityLogDao(ActivityLogRepository activityLogRepository) {
        this.activityLogRepository = activityLogRepository;
    }

    @Override
    public ActivityLog getById(Long id) {
        return activityLogRepository.getOne(id);
    }

    @Override
    public List<ActivityLog> getAll() {
        return activityLogRepository.findAllByOrderByTimestampDesc();
    }

    public List<ActivityLog> getAllByUser(User user) {
        return activityLogRepository.findActivityLogsByUserOrderByTimestampDesc(user);
    }

    @Override
    public void save(ActivityLog activityLog) {
        activityLogRepository.save(activityLog);
    }

    public void delete() {
        activityLogRepository.deleteAll();
    }
}
