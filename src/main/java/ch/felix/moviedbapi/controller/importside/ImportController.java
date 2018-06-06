package ch.felix.moviedbapi.controller.importside;

import ch.felix.moviedbapi.service.SettingsService;
import ch.felix.moviedbapi.service.importer.MovieImportService;
import ch.felix.moviedbapi.service.importer.SeriesImportService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

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

    @GetMapping(value = "movie", produces = "application/json")
    public @ResponseBody
    String importMovies() {
        movieImportService.importFile("moviePath");
        return "101";
    }

    @GetMapping(value = "serie", produces = "application/json")
    public @ResponseBody
    String importSeries() {
        seriesImportService.importFile("seriePath");
        return "103";
    }


    @PostMapping(value = "path/movie", produces = "application/json")
    public @ResponseBody
    String setMovieImportPath(@RequestParam("path") String pathParam) {
        settingsService.setValue("moviePath", pathParam);
        return "102";
    }

    @GetMapping(value = "path/movie", produces = "application/json")
    public @ResponseBody
    String getMovieImportPath() {
        return settingsService.getKey("moviePath");
    }

    @PostMapping(value = "path/serie", produces = "application/json")
    public @ResponseBody
    String setSerieImportPath(@RequestParam("path") String pathParam) {
        settingsService.setValue("seriePath", pathParam);
        return "102";
    }

    @GetMapping(value = "path/serie", produces = "application/json")
    public @ResponseBody
    String getSerieImportPath() {
        return settingsService.getKey("seriePath");
    }
}
