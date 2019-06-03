package ch.wetwer.moviedbapi.controller.betterApi;

import ch.wetwer.moviedbapi.data.activitylog.ActivityLogDao;
import ch.wetwer.moviedbapi.data.importlog.ImportLogDao;
import ch.wetwer.moviedbapi.data.request.RequestDao;
import ch.wetwer.moviedbapi.data.user.UserDao;
import ch.wetwer.moviedbapi.model.SettingsModel;
import ch.wetwer.moviedbapi.service.filehandler.SettingsService;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Wetwer
 * @project movie-score
 * @package ch.wetwer.moviedbapi.controller.betterApi
 * @created 21.05.2019
 **/

@CrossOrigin
@RestController
@RequestMapping("api/2/settings")
public class ControlCenterApi {

    private ImportLogDao importLogDao;
    private ActivityLogDao activityLogDao;
    private RequestDao requestDao;
    private UserDao userDao;

    private SettingsService settingsService;

    public ControlCenterApi(ImportLogDao importLogDao, ActivityLogDao activityLogDao, RequestDao requestDao,
                            UserDao userDao, SettingsService settingsService) {
        this.importLogDao = importLogDao;
        this.activityLogDao = activityLogDao;
        this.requestDao = requestDao;
        this.userDao = userDao;
        this.settingsService = settingsService;
    }

    @GetMapping(produces = "application/json")
    public SettingsModel getSettings(@RequestParam("sessionId") String sessionId) {
        if (userDao.getBySessionId(sessionId) != null) {
            SettingsModel settingsModel = new SettingsModel();

            settingsModel.setMoviePath(settingsService.getKey("moviePath"));
            settingsModel.setSeriesPath(settingsService.getKey("seriePath"));
            settingsModel.setPreviewPath(settingsService.getKey("preview"));
            settingsModel.setImportProgress(settingsService.getKey("importProgress"));
            settingsModel.setImportLogs(importLogDao.getAll());
            settingsModel.setActivityLogs(activityLogDao.getAll());
            settingsModel.setRequests(requestDao.getAll());

            return settingsModel;
        }
        return null;
    }

}
