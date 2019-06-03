package ch.wetwer.moviedbapi.model;

import ch.wetwer.moviedbapi.data.activitylog.ActivityLog;
import ch.wetwer.moviedbapi.data.importlog.ImportLog;
import ch.wetwer.moviedbapi.data.request.Request;
import lombok.Data;

import java.util.List;

/**
 * @author Wetwer
 * @project movie-score
 * @package ch.wetwer.moviedbapi.model
 * @created 21.05.2019
 **/

@Data
public class SettingsModel {

    private String moviePath;

    private String seriesPath;

    private String previewPath;

    private String importProgress;

    private List<ImportLog> importLogs;

    private List<ActivityLog> activityLogs;

    private List<Request> requests;

}
