package ch.felix.moviedbapi.controller;

import ch.felix.moviedbapi.data.entity.Genre;
import ch.felix.moviedbapi.data.repository.GenreRepository;
import ch.felix.moviedbapi.service.CookieService;
import ch.felix.moviedbapi.service.DuplicateService;
import ch.felix.moviedbapi.service.SearchService;
import ch.felix.moviedbapi.service.UserIndicatorService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
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
@RequestMapping("")
public class DashboardController {

    private GenreRepository genreRepository;

    private SearchService searchService;
    private DuplicateService duplicateService;
    private UserIndicatorService userIndicatorService;

    public DashboardController(GenreRepository genreRepository, SearchService searchService,
                               DuplicateService duplicateService, UserIndicatorService userIndicatorService) {
        this.genreRepository = genreRepository;
        this.searchService = searchService;
        this.duplicateService = duplicateService;
        this.userIndicatorService = userIndicatorService;
    }

    @GetMapping
    public String getDashboard(@RequestParam(name = "search", required = false, defaultValue = "") String search,
                               @RequestParam(name = "orderBy", required = false, defaultValue = "") String orderBy,
                               @RequestParam(name = "genre", required = false, defaultValue = "") String genreParam,
                               Model model, HttpServletRequest request) {
        userIndicatorService.allowGuestAccess(model, request);

        List<String> genres = new ArrayList<>();
        for (Genre genre : genreRepository.findAllByNameContainingOrderByName(search)) {
            genres.add(genre.getName());
        }
        genres = duplicateService.removeStringDuplicates(genres);
        model.addAttribute("genres", genres);

        model.addAttribute("movies", searchService.searchMoviesTop(search, orderBy, genreParam));
        model.addAttribute("series", searchService.searchSerieTop(search, genreParam));

        model.addAttribute("search", search);
        model.addAttribute("orderBy", orderBy);
        model.addAttribute("currentGenre", genreParam);

        model.addAttribute("page", "home");
        return "template";
    }
}
