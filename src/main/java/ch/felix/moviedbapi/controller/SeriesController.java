package ch.felix.moviedbapi.controller;

import ch.felix.moviedbapi.data.dto.GenreDto;
import ch.felix.moviedbapi.data.entity.Genre;
import ch.felix.moviedbapi.data.entity.Serie;
import ch.felix.moviedbapi.data.repository.SeasonRepository;
import ch.felix.moviedbapi.data.repository.SerieRepository;
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
    private GenreDto genreDto;
    private SeasonRepository seasonRepository;

    private SearchService searchService;
    private DuplicateService duplicateService;
    private UserIndicatorService userIndicatorService;

    public SeriesController(SerieRepository serieRepository, GenreDto genreDto,
                            SeasonRepository seasonRepository, SearchService searchService,
                            DuplicateService duplicateService, UserIndicatorService userIndicatorService) {
        this.serieRepository = serieRepository;
        this.genreDto = genreDto;
        this.seasonRepository = seasonRepository;
        this.searchService = searchService;
        this.duplicateService = duplicateService;
        this.userIndicatorService = userIndicatorService;
    }

    @GetMapping
    public String getSeries(@RequestParam(name = "search", required = false, defaultValue = "") String search,
                            @RequestParam(name = "genre", required = false, defaultValue = "") String genreParam,
                            Model model, HttpServletRequest request) {
        if (userIndicatorService.isUser(model, request)) {
            List<String> genres = new ArrayList<>();
            for (Genre genre : genreDto.getAll()) {
                genres.add(genre.getName());
            }
            genres = duplicateService.removeStringDuplicates(genres);
            model.addAttribute("genres", genres);

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
        if (userIndicatorService.isUser(model, request)) {
            Serie serie = serieRepository.findSerieById(Long.valueOf(serieId));

            model.addAttribute("serie", serie);
            model.addAttribute("seasons", seasonRepository.findSeasonsBySerieOrderBySeason(serie));
            model.addAttribute("page", "serie");
            return "template";
        } else {
            return "redirect:/login?redirect=/serie/" + serieId;
        }

    }
}
