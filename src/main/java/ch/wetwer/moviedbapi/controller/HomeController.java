package ch.wetwer.moviedbapi.controller;

import ch.wetwer.moviedbapi.data.movie.Movie;
import ch.wetwer.moviedbapi.data.user.User;
import ch.wetwer.moviedbapi.service.GenreSearchType;
import ch.wetwer.moviedbapi.service.SearchService;
import ch.wetwer.moviedbapi.service.auth.UserAuthService;
import ch.wetwer.moviedbapi.service.sugestion.SugestionService;
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
 * @project movie-score
 */
@Controller
@RequestMapping("")
public class HomeController {

    private SearchService searchService;
    private UserAuthService userAuthService;
    private SugestionService sugestionService;

    public HomeController(SearchService searchService, UserAuthService userAuthService,
                          SugestionService sugestionService) {
        this.searchService = searchService;
        this.userAuthService = userAuthService;
        this.sugestionService = sugestionService;
    }

    @GetMapping
    public String getDashboard(@RequestParam(name = "search", required = false, defaultValue = "") String search,
                               @RequestParam(name = "orderBy", required = false, defaultValue = "") String orderBy,
                               @RequestParam(name = "genre", required = false, defaultValue = "") String genreParam,
                               Model model, HttpServletRequest request) {
        if (userAuthService.isUser(model, request)) {
            User user = userAuthService.getUser(request).getUser();
            userAuthService.log(this.getClass(), user);

            if (!genreParam.equals("")) {
                return "redirect:/movies/1?search=" + search + "&orderBy=" + orderBy + "&genre=" + genreParam;
            }

            try {
                if (search.equals("") && genreParam.equals("") && orderBy.equals("")) {
                    model.addAttribute("startedVideos", searchService.findStartedVideos(user));
                } else {
                    model.addAttribute("startedVideos", new ArrayList<>());
                }
            } catch (NullPointerException e) {
                model.addAttribute("startedVideos", new ArrayList<>());
            }

            model.addAttribute("genres", searchService.getGenres(GenreSearchType.MOVIE));

            List<Movie> movieList = searchService.searchMoviesTop(search, orderBy);
            model.addAttribute("movies", movieList);
            model.addAttribute("series", searchService.searchSerieTop(search));

            if (movieList.isEmpty()) {
                model.addAttribute("sugestions", sugestionService.getAvalible(search));
            }

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
