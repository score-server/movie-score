package ch.felix.moviedbapi.service;

import ch.felix.moviedbapi.data.entity.ActivityLog;
import ch.felix.moviedbapi.data.entity.User;
import ch.felix.moviedbapi.data.repository.ActivityLogRepository;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.Date;

/**
 * @author Wetwer
 * @project movie-db
 */

@Service
public class ActivityService {

    private ActivityLogRepository activityLogRepository;

    public ActivityService(ActivityLogRepository activityLogRepository) {
        this.activityLogRepository = activityLogRepository;
    }

    public void log(String log, User user) {
        ActivityLog activityLog = new ActivityLog();
        activityLog.setLog(log);
        activityLog.setUser(user);
        activityLog.setTimestamp(new Timestamp(new Date().getTime()));
        activityLogRepository.save(activityLog);
    }

    public void log(String log) {
        ActivityLog activityLog = new ActivityLog();
        activityLog.setLog(log);
        activityLog.setTimestamp(new Timestamp(new Date().getTime()));
        activityLogRepository.save(activityLog);
    }
}
