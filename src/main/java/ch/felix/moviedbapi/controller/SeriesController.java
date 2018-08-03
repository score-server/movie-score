package ch.felix.moviedbapi.controller;

import ch.felix.moviedbapi.data.entity.Genre;
import ch.felix.moviedbapi.data.repository.GenreRepository;
import ch.felix.moviedbapi.data.repository.SerieRepository;

import ch.felix.moviedbapi.service.CookieService;
import ch.felix.moviedbapi.service.DuplicateService;
import ch.felix.moviedbapi.service.SearchService;
import ch.felix.moviedbapi.service.UserIndicatorService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Wetwer
 * @project movie-db
 */
@Controller
@RequestMapping("serie")
public class SeriesController {

    private SerieRepository serieRepository;
    private GenreRepository genreRepository;

    private CookieService cookieService;
    private SearchService searchService;
    private DuplicateService duplicateService;
    private UserIndicatorService userIndicatorService;

    public SeriesController(SerieRepository serieRepository, GenreRepository genreRepository,
                            SearchService searchService, DuplicateService duplicateService,
                            UserIndicatorService userIndicatorService) {
        this.serieRepository = serieRepository;
        this.genreRepository = genreRepository;
        this.searchService = searchService;
        this.duplicateService = duplicateService;
        this.userIndicatorService = userIndicatorService;
    }

    @GetMapping(produces = "application/json")
    public String getSeries(@RequestParam(name = "search", required = false, defaultValue = "") String search,
                            @RequestParam(name = "genre", required = false, defaultValue = "") String genreParam,
                            Model model, HttpServletRequest request) {
        userIndicatorService.allowGuestAccess(model, request);


        List<String> genres = new ArrayList<>();
        for (Genre genre : genreRepository.findAllByNameContainingOrderByName(search)) {
            genres.add(genre.getName());
        }
        genres = duplicateService.removeStringDuplicates(genres);
        model.addAttribute("genres", genres);

        model.addAttribute("series", searchService.searchSerie(search, genreParam));

        model.addAttribute("search", search);
        model.addAttribute("currentGenre", genreParam);

        model.addAttribute("page", "serieList");
        return "template";
    }

    @GetMapping(value = "/{serieId}", produces = "application/json")
    public String getOneSerie(@PathVariable("serieId") String serieParam, Model model, HttpServletRequest request) {
        userIndicatorService.allowGuestAccess(model, request);

        model.addAttribute("serie", serieRepository.findSerieById(Long.valueOf(serieParam)));
        model.addAttribute("page", "serie");
        return "template";
    }
}
