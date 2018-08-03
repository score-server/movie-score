package ch.felix.moviedbapi.controller;

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

    public ImportController(MovieImportService movieImportService, SeriesImportService seriesImportService,
                            SettingsService settingsService, UserIndicatorService userIndicatorService) {
        this.movieImportService = movieImportService;
        this.seriesImportService = seriesImportService;
        this.settingsService = settingsService;
        this.userIndicatorService = userIndicatorService;
    }

    @PostMapping(value = "movie", produces = "application/json")
    public String importMovies(Model model, HttpServletRequest request) {
        if (userIndicatorService.disallowUserAccess(model, request)) {
            return "redirect:/";
        }

        if (settingsService.getKey("import").equals("1")) {
            return "redirect:/settings";
        }
        settingsService.setValue("import", "1");
        movieImportService.importFile("moviePath");
        return "redirect:/settings";
    }

    @PostMapping(value = "serie", produces = "application/json")
    public String importSeries(Model model, HttpServletRequest request) {
        if (userIndicatorService.disallowUserAccess(model, request)) {
            return "redirect:/";
        }

        if (settingsService.getKey("import").equals("1")) {
            return "redirect:/settings";
        }
        settingsService.setValue("import", "1");
        seriesImportService.importFile("seriePath");
        return "redirect:/settings";
    }


    @PostMapping("path/movie")
    public String setMovieImportPath(@RequestParam("path") String pathParam, Model model, HttpServletRequest request) {
        if (userIndicatorService.disallowUserAccess(model, request)) {
            return "redirect:/";
        }

        settingsService.setValue("moviePath", pathParam);
        return "redirect:/settings";
    }

    @PostMapping("path/serie")
    public String setSerieImportPath(@RequestParam("path") String pathParam, Model model, HttpServletRequest request) {
        if (userIndicatorService.disallowUserAccess(model, request)) {
            return "redirect:/";
        }

        settingsService.setValue("seriePath", pathParam);
        return "redirect:/settings";
    }

    @PostMapping("reset")
    public String importReset(Model model, HttpServletRequest request) {
        if (userIndicatorService.disallowUserAccess(model, request)) {
            return "redirect:/";
        }

        settingsService.setValue("import", "0");
        return "redirect:/settings";
    }
}
