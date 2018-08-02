package ch.felix.moviedbapi.controller;

import ch.felix.moviedbapi.data.repository.SerieRepository;

import ch.felix.moviedbapi.service.CookieService;
import ch.felix.moviedbapi.service.SearchService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;

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

    private CookieService cookieService;
    private SearchService searchService;

    public SeriesController(SerieRepository serieRepository, CookieService cookieService, SearchService searchService) {
        this.serieRepository = serieRepository;
        this.cookieService = cookieService;
        this.searchService = searchService;
    }

    @GetMapping(produces = "application/json")
    public String getSeries(@RequestParam(name = "search", required = false, defaultValue = "") String search,
                            Model model, HttpServletRequest request) {
        try {
            model.addAttribute("currentUser", cookieService.getCurrentUser(request));
        } catch (NullPointerException e) {
        }

        model.addAttribute("series", searchService.searchSerie(search));

        model.addAttribute("search", search);

        model.addAttribute("page", "serieList");
        return "template";
    }

    @GetMapping(value = "/{serieId}", produces = "application/json")
    public String getOneSerie(@PathVariable("serieId") String serieParam, Model model, HttpServletRequest request) {
        try {
            model.addAttribute("currentUser", cookieService.getCurrentUser(request));
        } catch (NullPointerException e) {
        }

        model.addAttribute("serie", serieRepository.findSerieById(Long.valueOf(serieParam)));
        model.addAttribute("page", "serie");
        return "template";
    }
}
