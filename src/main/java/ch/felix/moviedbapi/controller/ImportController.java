package ch.felix.moviedbapi.controller;

import ch.felix.moviedbapi.service.importer.MovieImportService;
import ch.felix.moviedbapi.service.importer.SeriesImportService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

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

    public ImportController(MovieImportService movieImportService, SeriesImportService seriesImportService) {
        this.movieImportService = movieImportService;
        this.seriesImportService = seriesImportService;
    }

    @PostMapping(value = "/movie", produces = "application/json")
    public String importMovies(Model model) {
        movieImportService.importFile("moviePath");
        model.addAttribute("response", "{\"response\":\"101\"}");//Added
        return "json";
    }

    @PostMapping(value = "/serie", produces = "application/json")
    public String importSeries(Model model) {
        seriesImportService.importFile("seriePath");
        model.addAttribute("response", "{\"response\":\"101\"}");//Added
        return "json";
    }


}
