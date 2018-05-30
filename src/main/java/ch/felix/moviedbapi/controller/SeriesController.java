package ch.felix.moviedbapi.controller;

import ch.felix.moviedbapi.data.repository.SerieRepository;
import ch.felix.moviedbapi.service.importer.SeriesImportService;
import ch.felix.moviedbapi.service.json.SerieJsonService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

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

    private SerieJsonService serieJsonService;
    private SeriesImportService serieImportService;

    public SeriesController(SerieRepository serieRepository, SerieJsonService serieJsonService,
                            SeriesImportService serieImportService) {
        this.serieRepository = serieRepository;
        this.serieJsonService = serieJsonService;
        this.serieImportService = serieImportService;
    }

    @GetMapping
    public String getAllSeries(Model model) {
        model.addAttribute("response", serieJsonService.getSerieList(serieRepository.findAll()));
        return "json";
    }

    @GetMapping("/{serieId}")
    public String getOneSerie(@PathVariable("serieId") String serieParam) {
        serieRepository.findSerieById(Long.valueOf(serieParam));
        return "json";
    }
}
