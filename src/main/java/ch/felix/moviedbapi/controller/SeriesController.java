package ch.felix.moviedbapi.controller;

import ch.felix.moviedbapi.data.dao.SeasonDao;
import ch.felix.moviedbapi.data.dao.SerieDao;
import ch.felix.moviedbapi.data.entity.Serie;
import ch.felix.moviedbapi.service.GenreSearchType;
import ch.felix.moviedbapi.service.SearchService;
import ch.felix.moviedbapi.service.auth.UserAuthService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Wetwer
 * @project movie-db
 */
@Controller
@RequestMapping("serie")
public class SeriesController {

    private SerieDao serieDao;
    private SeasonDao seasonDao;

    private SearchService searchService;
    private UserAuthService userAuthService;

    public SeriesController(SerieDao serieDao, SeasonDao seasonDao, SearchService searchService,
                            UserAuthService userAuthService) {
        this.serieDao = serieDao;
        this.seasonDao = seasonDao;
        this.searchService = searchService;
        this.userAuthService = userAuthService;
    }

    @GetMapping
    public String getSeries(@RequestParam(name = "search", required = false, defaultValue = "") String search,
                            @RequestParam(name = "genre", required = false, defaultValue = "") String genreParam,
                            Model model, HttpServletRequest request) {
        if (userAuthService.isUser(model, request)) {
            model.addAttribute("genres", searchService.getGenres(GenreSearchType.SERIE));
            model.addAttribute("series", searchService.searchSerie(search, genreParam));

            model.addAttribute("search", search);
            model.addAttribute("currentGenre", genreParam);

            model.addAttribute("page", "serieList");
            return "template";
        } else {
            return "redirect:/login?redirect=/serie";
        }
    }

    @GetMapping(value = "/{serieId}")
    public String getOneSerie(@PathVariable("serieId") String serieId, Model model, HttpServletRequest request) {
        if (userAuthService.isUser(model, request)) {
            Serie serie = serieDao.getById(Long.valueOf(serieId));

            model.addAttribute("serie", serie);
            model.addAttribute("seasons", seasonDao.getBySerie(serie));
            model.addAttribute("page", "serie");
            return "template";
        } else {
            return "redirect:/login?redirect=/serie/" + serieId;
        }

    }
}
