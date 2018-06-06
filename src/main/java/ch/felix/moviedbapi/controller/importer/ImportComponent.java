package ch.felix.moviedbapi.controller.importer;

import ch.felix.moviedbapi.service.importer.ImportService;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * @author Felix
 * @date 05.06.2018
 * <p>
 * Project: movie-db-api
 * Package: ch.felix.moviedbapi.controller.importside
 **/

@Component
public class ImportComponent {

    private ImportService seriesImportService;
    private ImportService movieImportService;

    public ImportComponent(ImportService seriesImportService, ImportService movieImportService) {
        this.seriesImportService = seriesImportService;
        this.movieImportService = movieImportService;
    }

    @Scheduled(fixedRate = 86400000)
    public void updateMovieAndSeriesList() {
        movieImportService.importFile("moviePath");
        seriesImportService.importFile("seriePath");
    }


}
