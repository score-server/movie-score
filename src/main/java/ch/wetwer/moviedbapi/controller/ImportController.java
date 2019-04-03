package ch.wetwer.moviedbapi.controller;

import ch.wetwer.moviedbapi.data.updatelog.UpdateLog;
import ch.wetwer.moviedbapi.data.updatelog.UpdateLogDao;
import ch.wetwer.moviedbapi.data.user.User;
import ch.wetwer.moviedbapi.service.ActivityService;
import ch.wetwer.moviedbapi.service.auth.UserAuthService;
import ch.wetwer.moviedbapi.service.filehandler.SettingsService;
import ch.wetwer.moviedbapi.service.importer.MovieImportService;
import ch.wetwer.moviedbapi.service.importer.SeriesImportService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Wetwer
 * @project movie-score
 */
@Controller
@RequestMapping("import")
public class ImportController {

    private UpdateLogDao updateLogDao;

    private UserAuthService userAuthService;
    private MovieImportService movieImportService;
    private SeriesImportService seriesImportService;
    private ActivityService activityService;
    private SettingsService settingsService;

    public ImportController(UpdateLogDao updateLogDao, UserAuthService userAuthService, MovieImportService movieImportService,
                            SeriesImportService seriesImportService, ActivityService activityService,
                            SettingsService settingsService) {
        this.updateLogDao = updateLogDao;
        this.userAuthService = userAuthService;
        this.movieImportService = movieImportService;
        this.seriesImportService = seriesImportService;
        this.activityService = activityService;
        this.settingsService = settingsService;
    }

    @PostMapping(value = "movie")
    public String importMovies(Model model, HttpServletRequest request) {
        User user = userAuthService.getUser(request).getUser();
        if (userAuthService.isAdministrator(model, request)) {
            if (settingsService.getKey("import").equals("1")) {
                return "redirect:/settings";
            }
            movieImportService.importAll();
            activityService.log(user.getName() + " started Movie Import", user);
            return "redirect:/settings";
        } else {
            return "redirect:/";
        }
    }

    @PostMapping(value = "movie/update")
    public String updateMovies(Model model, HttpServletRequest request) {
        User user = userAuthService.getUser(request).getUser();
        if (userAuthService.isAdministrator(model, request)) {
            if (settingsService.getKey("import").equals("1")) {
                return "redirect:/settings";
            }
            movieImportService.updateAll();
            activityService.log(user.getName() + " started Movie Update", user);
            return "redirect:/settings";
        } else {
            return "redirect:/";
        }
    }

    @PostMapping(value = "serie")
    public String importSeries(Model model, HttpServletRequest request) {
        User user = userAuthService.getUser(request).getUser();
        if (userAuthService.isAdministrator(model, request)) {
            if (settingsService.getKey("import").equals("1")) {
                return "redirect:/settings";
            }
            seriesImportService.importAll();
            activityService.log(user.getName() + " started Series Import", user);
            return "redirect:/settings";
        } else {
            return "redirect:/";
        }
    }

    @PostMapping(value = "serie/update")
    public String updateSeries(Model model, HttpServletRequest request) {
        User user = userAuthService.getUser(request).getUser();
        if (userAuthService.isAdministrator(model, request)) {
            if (settingsService.getKey("import").equals("1")) {
                return "redirect:/settings";
            }
            seriesImportService.updateAll();
            activityService.log(user.getName() + " started Series Update", user);
            return "redirect:/settings";
        } else {
            return "redirect:/";
        }
    }


    @PostMapping("path/movie")
    public String setMovieImportPath(@RequestParam("path") String pathParam, Model model, HttpServletRequest request) {
        if (userAuthService.isAdministrator(model, request)) {
            settingsService.setValue("moviePath", pathParam);
            return "redirect:/settings";
        } else {
            return "redirect:/";
        }
    }

    @PostMapping("path/serie")
    public String setSerieImportPath(@RequestParam("path") String pathParam, Model model, HttpServletRequest request) {
        if (userAuthService.isAdministrator(model, request)) {
            settingsService.setValue("seriePath", pathParam);
            return "redirect:/settings";
        } else {
            return "redirect:/";
        }
    }

    @PostMapping("reset")
    public String importReset(Model model, HttpServletRequest request) {
        if (userAuthService.isAdministrator(model, request)) {
            movieImportService.setImportStatus("0");

            UpdateLog updateLog = updateLogDao.getAll().get(0);
            updateLog.setStatus("Failed");
            updateLogDao.save(updateLog);

            return "redirect:/settings";
        } else {
            return "redirect:/";
        }
    }
}
