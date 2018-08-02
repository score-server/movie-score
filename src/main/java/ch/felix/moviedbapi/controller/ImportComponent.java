package ch.felix.moviedbapi.controller;

import ch.felix.moviedbapi.service.importer.ImportService;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * @author Wetwer
 * @project movie-db
 */
@Component
public class ImportComponent {

    private ImportService seriesImportService;
    private ImportService movieImportService;

    public ImportComponent(ImportService seriesImportService, ImportService movieImportService) {
        this.seriesImportService = seriesImportService;
        this.movieImportService = movieImportService;
    }

//    @Scheduled(fixedRate = 86400000)
    public void updateMovieAndSeriesList() {
        movieImportService.importFile("moviePath");
        seriesImportService.importFile("seriePath");
    }


}
