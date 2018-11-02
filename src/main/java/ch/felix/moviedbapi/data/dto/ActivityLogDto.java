package ch.felix.moviedbapi.data.dto;

import ch.felix.moviedbapi.data.entity.ActivityLog;
import ch.felix.moviedbapi.data.entity.User;
import ch.felix.moviedbapi.data.repository.ActivityLogRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ActivityLogDto implements DtoInterface<ActivityLog> {

    private ActivityLogRepository activityLogRepository;

    public ActivityLogDto(ActivityLogRepository activityLogRepository) {
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
