package ch.felix.moviedbapi.controller;

import ch.felix.moviedbapi.data.entity.Genre;
import ch.felix.moviedbapi.data.entity.User;
import ch.felix.moviedbapi.data.repository.GenreRepository;
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
public class HomeController {

    private GenreRepository genreRepository;

    private SearchService searchService;
    private DuplicateService duplicateService;
    private UserIndicatorService userIndicatorService;

    public HomeController(GenreRepository genreRepository, SearchService searchService,
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
        if (userIndicatorService.isUser(model, request)) {
            if (!genreParam.equals("")) {
                return "redirect:/movies/1?search=" + search + "&orderBy=" + orderBy + "&genre=" + genreParam;
            }

            User user = userIndicatorService.getUser(request).getUser();

            List<String> genres = new ArrayList<>();
            for (Genre genre : genreRepository.findGenreByOrderByName()) {
                genres.add(genre.getName());
            }
            genres = duplicateService.removeStringDuplicates(genres);
            model.addAttribute("genres", genres);

            model.addAttribute("startedMovies", searchService.findStartedMovies(user));
            model.addAttribute("movies", searchService.searchMoviesTop(search, orderBy, genreParam));
            model.addAttribute("all", searchService.searchMoviesTop("", orderBy, genreParam));
            model.addAttribute("series", searchService.searchSerieTop(search));

            model.addAttribute("search", search);
            model.addAttribute("orderBy", orderBy);
            model.addAttribute("currentGenre", genreParam);

            model.addAttribute("page", "home");
            return "template";
        } else {
            return "redirect:/login";
        }
    }
}
