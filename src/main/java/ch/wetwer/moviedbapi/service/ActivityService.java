package ch.wetwer.moviedbapi.service;

import ch.wetwer.moviedbapi.data.activitylog.ActivityLog;
import ch.wetwer.moviedbapi.data.activitylog.ActivityLogDao;
import ch.wetwer.moviedbapi.data.user.User;
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
        ActivityLog activityLog = new ActivityLog();
        activityLog.setLog(log);
        activityLog.setUser(user);
        activityLog.setTimestamp(new Timestamp(new Date().getTime()));
        activityLogDao.save(activityLog);
    }

    public void log(String log) {
        ActivityLog activityLog = new ActivityLog();
        activityLog.setLog(log);
        activityLog.setTimestamp(new Timestamp(new Date().getTime()));
        activityLogDao.save(activityLog);
    }
}
