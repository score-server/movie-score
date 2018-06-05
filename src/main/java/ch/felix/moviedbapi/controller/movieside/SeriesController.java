package ch.felix.moviedbapi.controller.movieside;

import ch.felix.moviedbapi.data.entity.Serie;
import ch.felix.moviedbapi.data.repository.SerieRepository;
import ch.felix.moviedbapi.service.importer.SeriesImportService;
import java.util.List;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
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
@RequestMapping("serie")
public class SeriesController {

    private SerieRepository serieRepository;

    private SeriesImportService serieImportService;

    public SeriesController(SerieRepository serieRepository, SeriesImportService serieImportService) {
        this.serieRepository = serieRepository;
        this.serieImportService = serieImportService;
    }

    @GetMapping
    public @ResponseBody
    List<Serie> getAllSeries() {
        return serieRepository.findAll();
    }

    @GetMapping("/{serieId}")
    public @ResponseBody
    Serie getOneSerie(@PathVariable("serieId") String serieParam) {
        return serieRepository.findSerieById(Long.valueOf(serieParam));
    }
}
