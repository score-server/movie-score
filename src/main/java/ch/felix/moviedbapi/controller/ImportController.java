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
        movieImportService.importFile("moviePath");
        return "redirect:/";
    }

    @PostMapping(value = "serie", produces = "application/json")
    public String importSeries() {
        seriesImportService.importFile("seriePath");
        return "redirect:/";
    }


    @PostMapping("path/movie") //TODO: Add to Home or Settings
    public String setMovieImportPath(@RequestParam("path") String pathParam) {
        settingsService.setValue("moviePath", pathParam);
        return "redirect:/";
    }

    @PostMapping("path/serie") //TODO: Add to Home or Settings
    public String setSerieImportPath(@RequestParam("path") String pathParam, Model model) {
        model.addAttribute("moviePath",settingsService.getKey("seriePath"));
        settingsService.setValue("seriePath", pathParam);
        return "redirect:/";
    }
}
