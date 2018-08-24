package ch.felix.moviedbapi.controller;

import ch.felix.moviedbapi.data.entity.User;
import ch.felix.moviedbapi.service.ActivityService;
import ch.felix.moviedbapi.service.SettingsService;
import ch.felix.moviedbapi.service.UserIndicatorService;
import ch.felix.moviedbapi.service.importer.MovieImportService;
import ch.felix.moviedbapi.service.importer.SeriesImportService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Wetwer
 * @project movie-db
 */
@Controller
@RequestMapping("import")
public class ImportController {

    private MovieImportService movieImportService;
    private SeriesImportService seriesImportService;
    private SettingsService settingsService;
    private UserIndicatorService userIndicatorService;
    private ActivityService activityService;

    public ImportController(MovieImportService movieImportService, SeriesImportService seriesImportService,
                            SettingsService settingsService, UserIndicatorService userIndicatorService,
                            ActivityService activityService) {
        this.movieImportService = movieImportService;
        this.seriesImportService = seriesImportService;
        this.settingsService = settingsService;
        this.userIndicatorService = userIndicatorService;
        this.activityService = activityService;
    }

    @PostMapping(value = "movie")
    public String importMovies(Model model, HttpServletRequest request) {
        User user = userIndicatorService.getUser(request).getUser();
        if (userIndicatorService.isAdministrator(model, request)) {
            if (settingsService.getKey("import").equals("1")) {
                return "redirect:/settings";
            }
            settingsService.setValue("importProgress", "0");
            settingsService.setValue("import", "1");
            movieImportService.importFile("moviePath");
            activityService.log(user.getName() + " started Movie Import", user);
            return "redirect:/settings";
        } else {
            return "redirect:/";
        }
    }

    @PostMapping(value = "movie/update")
    public String updateMovies(Model model, HttpServletRequest request) {
        User user = userIndicatorService.getUser(request).getUser();
        if (userIndicatorService.isAdministrator(model, request)) {
            if (settingsService.getKey("import").equals("1")) {
                return "redirect:/settings";
            }
            settingsService.setValue("importProgress", "0");
            settingsService.setValue("import", "1");
            movieImportService.updateFile();
            activityService.log(user.getName() + " started Movie Update", user);
            return "redirect:/settings";
        } else {
            return "redirect:/";
        }
    }

    @PostMapping(value = "serie")
    public String importSeries(Model model, HttpServletRequest request) {
        User user = userIndicatorService.getUser(request).getUser();
        if (userIndicatorService.isAdministrator(model, request)) {
            if (settingsService.getKey("import").equals("1")) {
                return "redirect:/settings";
            }
            settingsService.setValue("importProgress", "0");
            settingsService.setValue("import", "1");
            seriesImportService.importFile("seriePath");
            activityService.log(user.getName() + " started Movie Import", user);
            return "redirect:/settings";
        } else {
            return "redirect:/";
        }
    }

    @PostMapping(value = "serie/update")
    public String updateSeries(Model model, HttpServletRequest request) {
        User user = userIndicatorService.getUser(request).getUser();
        if (userIndicatorService.isAdministrator(model, request)) {
            if (settingsService.getKey("import").equals("1")) {
                return "redirect:/settings";
            }
            settingsService.setValue("importProgress", "0");
            settingsService.setValue("import", "1");
            seriesImportService.update();
            activityService.log(user.getName() + " started Series Update", user);
            return "redirect:/settings";
        } else {
            return "redirect:/";
        }
    }


    @PostMapping("path/movie")
    public String setMovieImportPath(@RequestParam("path") String pathParam, Model model, HttpServletRequest request) {
        if (userIndicatorService.isAdministrator(model, request)) {
            settingsService.setValue("moviePath", pathParam);
            return "redirect:/settings";
        } else {
            return "redirect:/";
        }
    }

    @PostMapping("path/serie")
    public String setSerieImportPath(@RequestParam("path") String pathParam, Model model, HttpServletRequest request) {
        if (userIndicatorService.isAdministrator(model, request)) {
            settingsService.setValue("seriePath", pathParam);
            return "redirect:/settings";
        } else {
            return "redirect:/";
        }
    }

    @PostMapping("reset")
    public String importReset(Model model, HttpServletRequest request) {
        if (userIndicatorService.isAdministrator(model, request)) {
            settingsService.setValue("importProgress", "0");
            settingsService.setValue("import", "0");
            return "redirect:/settings";
        } else {
            return "redirect:/";
        }
    }
}
