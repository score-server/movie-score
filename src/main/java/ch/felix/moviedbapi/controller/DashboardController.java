package ch.felix.moviedbapi.controller;

import ch.felix.moviedbapi.service.CookieService;
import ch.felix.moviedbapi.service.SearchService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Wetwer
 * @project movie-db
 */
@Controller
@RequestMapping("")
public class DashboardController {

    private CookieService cookieService;
    private SearchService searchService;

    public DashboardController(CookieService cookieService, SearchService searchService) {
        this.cookieService = cookieService;
        this.searchService = searchService;
    }

    @GetMapping
    public String getDashboard(@RequestParam(name = "search", required = false, defaultValue = "") String search,
                               @RequestParam(name = "orderBy", required = false, defaultValue = "") String orderBy,
                               Model model, HttpServletRequest request) {
        try {
            model.addAttribute("currentUser", cookieService.getCurrentUser(request));
        } catch (NullPointerException e) {
        }

        model.addAttribute("movies", searchService.searchMoviesTop(search, orderBy));
        model.addAttribute("series", searchService.searchSerieTop(search));

        model.addAttribute("search", search);
        model.addAttribute("orderBy", orderBy);

        model.addAttribute("page", "home");
        return "template";
    }
}
