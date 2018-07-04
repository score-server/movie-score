package ch.felix.moviedbapi.controller;

import ch.felix.moviedbapi.data.entity.Serie;
import ch.felix.moviedbapi.data.repository.SerieRepository;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
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

@Controller
@RequestMapping("serie")
public class SeriesController {

    private SerieRepository serieRepository;

    public SeriesController(SerieRepository serieRepository) {
        this.serieRepository = serieRepository;
    }

    @GetMapping(produces = "application/json")
    public String getAllSeries(Model model) {
        model.addAttribute("series", serieRepository.findAll());
        model.addAttribute("page", "serieList");
        return "template";
    }

    @GetMapping(value = "/{serieId}", produces = "application/json")
    public String getOneSerie(@PathVariable("serieId") String serieParam, Model model) {
        model.addAttribute("serie", serieRepository.findSerieById(Long.valueOf(serieParam)));
        model.addAttribute("page", "serie");
        return "template";
    }

    @GetMapping(value = "search/{search}", produces = "application/json")
    public String searchSerie(@PathVariable("search") String searchParam, Model model) {
        model.addAttribute("series", serieRepository.findSeriesByTitleContaining(searchParam));
        model.addAttribute("page", "searchSerie");
        return "template";
    }
}
