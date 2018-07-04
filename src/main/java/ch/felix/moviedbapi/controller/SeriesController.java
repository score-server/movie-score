package ch.felix.moviedbapi.controller;

import ch.felix.moviedbapi.data.repository.SerieRepository;

import ch.felix.moviedbapi.service.CookieService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

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

    public SeriesController(SerieRepository serieRepository, CookieService cookieService) {
        this.serieRepository = serieRepository;
        this.cookieService = cookieService;
    }

    @GetMapping(produces = "application/json")
    public String getAllSeries(Model model, HttpServletRequest request) {
        try {
            model.addAttribute("currentUser", cookieService.getCurrentUser(request));
        } catch (NullPointerException e) {
        }
        model.addAttribute("series", serieRepository.findAll());
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

    @GetMapping(value = "search/{search}", produces = "application/json")
    public String searchSerie(@PathVariable("search") String searchParam, Model model, HttpServletRequest request) {
        try {
            model.addAttribute("currentUser", cookieService.getCurrentUser(request));
        } catch (NullPointerException e) {
        }

        model.addAttribute("series", serieRepository.findSeriesByTitleContaining(searchParam));
        model.addAttribute("page", "searchSerie");
        return "template";
    }
}
