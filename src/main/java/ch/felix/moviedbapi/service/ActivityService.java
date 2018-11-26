package ch.felix.moviedbapi.service;

import ch.felix.moviedbapi.data.dao.ActivityLogDao;
import ch.felix.moviedbapi.data.entity.ActivityLog;
import ch.felix.moviedbapi.data.entity.User;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.Date;

/**
 * @author Wetwer
 * @project movie-db
 */

@Service
public class ActivityService {

    private ActivityLogDao activityLogDao;

    public ActivityService(ActivityLogDao activityLogDao) {
        this.activityLogDao = activityLogDao;
    }

    public void log(String log, User user) {
        final ActivityLog activityLog = new ActivityLog();
        activityLog.setLog(log);
        activityLog.setUser(user);
        activityLog.setTimestamp(new Timestamp(new Date().getTime()));
        activityLogDao.save(activityLog);
    }

    public void log(String log) {
        final ActivityLog activityLog = new ActivityLog();
        activityLog.setLog(log);
        activityLog.setTimestamp(new Timestamp(new Date().getTime()));
        activityLogDao.save(activityLog);
    }
}
