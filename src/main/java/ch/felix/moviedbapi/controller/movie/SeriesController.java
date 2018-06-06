package ch.felix.moviedbapi.controller.movie;

import ch.felix.moviedbapi.data.entity.Serie;
import ch.felix.moviedbapi.data.repository.SerieRepository;
import ch.felix.moviedbapi.service.importer.SeriesImportService;
import java.util.List;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Felix
 * @date 30.05.2018
 * <p>
 * Project: movie-db-api
 * Package: ch.felix.moviedbapi.controller
 **/

@RestController
@RequestMapping("serie")
public class SeriesController {

    private SerieRepository serieRepository;

    private SeriesImportService serieImportService;

    public SeriesController(SerieRepository serieRepository, SeriesImportService serieImportService) {
        this.serieRepository = serieRepository;
        this.serieImportService = serieImportService;
    }

    @GetMapping(produces = "application/json")
    public List<Serie> getAllSeries() {
        return serieRepository.findAll();
    }

    @GetMapping(value = "/{serieId}", produces = "application/json")
    public Serie getOneSerie(@PathVariable("serieId") String serieParam) {
        return serieRepository.findSerieById(Long.valueOf(serieParam));
    }
}
