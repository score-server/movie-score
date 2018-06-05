package ch.felix.moviedbapi.controller.importside;

import ch.felix.moviedbapi.service.importer.MovieImportService;
import ch.felix.moviedbapi.service.importer.SeriesImportService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
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

    public ImportController(MovieImportService movieImportService, SeriesImportService seriesImportService) {
        this.movieImportService = movieImportService;
        this.seriesImportService = seriesImportService;
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


}
