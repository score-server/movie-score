package ch.felix.moviedbapi.controller;

import ch.felix.moviedbapi.data.dto.GenreDto;
import ch.felix.moviedbapi.data.entity.Genre;
import ch.felix.moviedbapi.data.entity.User;
import ch.felix.moviedbapi.service.DuplicateService;
import ch.felix.moviedbapi.service.SearchService;
import ch.felix.moviedbapi.service.UserAuthService;
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

    private GenreDto genreDto;

    private SearchService searchService;
    private DuplicateService duplicateService;
    private UserAuthService userAuthService;

    public HomeController(GenreDto genreDto, SearchService searchService, DuplicateService duplicateService,
                          UserAuthService userAuthService) {
        this.genreDto = genreDto;
        this.searchService = searchService;
        this.duplicateService = duplicateService;
        this.userAuthService = userAuthService;
    }

    @GetMapping
    public String getDashboard(@RequestParam(name = "search", required = false, defaultValue = "") String search,
                               @RequestParam(name = "orderBy", required = false, defaultValue = "") String orderBy,
                               @RequestParam(name = "genre", required = false, defaultValue = "") String genreParam,
                               Model model, HttpServletRequest request) {
        if (userAuthService.isUser(model, request)) {
            User user = userAuthService.getUser(request).getUser();
            if (!genreParam.equals("")) {
                return "redirect:/movies/1?search=" + search + "&orderBy=" + orderBy + "&genre=" + genreParam;
            }


            List<String> genres = new ArrayList<>();
            for (Genre genre : genreDto.getAll()) {
                genres.add(genre.getName());
            }
            genres = duplicateService.removeStringDuplicates(genres);
            if (search.equals("") && genreParam.equals("") && orderBy.equals("")) {
                model.addAttribute("startedVideos", searchService.findStartedVideos(user));
            } else {
                model.addAttribute("startedMovies", new ArrayList<>());
            }
            model.addAttribute("genres", genres);

            model.addAttribute("movies", searchService.searchMoviesTop(search, orderBy));
            model.addAttribute("all", searchService.searchMoviesTop("", orderBy));
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
