package ch.felix.moviedbapi.controller;

import ch.felix.moviedbapi.service.SettingsService;
import ch.felix.moviedbapi.service.importer.MovieImportService;
import ch.felix.moviedbapi.service.importer.SeriesImportService;
import com.sun.org.apache.xpath.internal.operations.Mod;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author Felix
 * @date 30.05.2018
 * <p>
 * Project: movie-db-api
 * Package: ch.felix.moviedbapi.controller
 **/

@Controller
@RequestMapping("import")
public class ImportController {

    private MovieImportService movieImportService;
    private SeriesImportService seriesImportService;
    private SettingsService settingsService;

    public ImportController(MovieImportService movieImportService, SeriesImportService seriesImportService, SettingsService settingsService) {
        this.movieImportService = movieImportService;
        this.seriesImportService = seriesImportService;
        this.settingsService = settingsService;
    }

    @PostMapping(value = "movie", produces = "application/json")
    public String importMovies() {
        if (settingsService.getKey("import").equals("1")) {
            return "redirect:/settings";
        }
        settingsService.setValue("import", "1");
        movieImportService.importFile("moviePath");
        return "redirect:/settings";
    }

    @PostMapping(value = "serie", produces = "application/json")
    public String importSeries() {
        if (settingsService.getKey("import").equals("1")) {
            return "redirect:/settings";
        }
        settingsService.setValue("import", "1");
        seriesImportService.importFile("seriePath");
        return "redirect:/settings";
    }


    @PostMapping("path/movie")
    public String setMovieImportPath(@RequestParam("path") String pathParam) {
        settingsService.setValue("moviePath", pathParam);
        return "redirect:/settings";
    }

    @PostMapping("path/serie")
    public String setSerieImportPath(@RequestParam("path") String pathParam) {
        settingsService.setValue("seriePath", pathParam);
        return "redirect:/settings";
    }

    @PostMapping("reset")
    public String importReset() {
        settingsService.setValue("import", "0");
        return "redirect:/settings";
    }
}
