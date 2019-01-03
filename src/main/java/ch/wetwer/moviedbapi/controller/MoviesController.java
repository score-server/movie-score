package ch.wetwer.moviedbapi.controller;

import ch.wetwer.moviedbapi.data.movie.Movie;
import ch.wetwer.moviedbapi.service.GenreSearchType;
import ch.wetwer.moviedbapi.service.PageService;
import ch.wetwer.moviedbapi.service.SearchService;
import ch.wetwer.moviedbapi.service.auth.UserAuthService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
@RequestMapping("movies")
public class MoviesController {

    private SearchService searchService;
    private UserAuthService userAuthService;
    private PageService pageService;

    public MoviesController(SearchService searchService, UserAuthService userAuthService, PageService pageService) {
        this.searchService = searchService;
        this.userAuthService = userAuthService;
        this.pageService = pageService;
    }

    @GetMapping("{page}")
    public String getMovies(@PathVariable(name = "page", required = false) Integer page,
                            @RequestParam(name = "search", required = false, defaultValue = "") String search,
                            @RequestParam(name = "orderBy", required = false, defaultValue = "") String orderBy,
                            @RequestParam(name = "genre", required = false, defaultValue = "") String genreParam,
                            Model model, HttpServletRequest request) {
        if (userAuthService.isUser(model, request)) {
            try {
                userAuthService.log(this.getClass(), request);

                List<Movie> movies = pageService.getPage(searchService.searchMovies(search, orderBy, genreParam), page);

                model.addAttribute("genres", searchService.getGenres(GenreSearchType.MOVIE));
                model.addAttribute("movies", movies);
                model.addAttribute("all", searchService.searchMoviesTop("", orderBy));

                model.addAttribute("pageIndex", page);
                if (page - 1 == 0) {
                    model.addAttribute("pageIndexLast", page - 1);
                    model.addAttribute("lastDisabled", false);
                } else {
                    model.addAttribute("pageIndexLast", 1);
                    model.addAttribute("lastDisabled", true);
                }
                model.addAttribute("pageIndexNext", page + 1);
                model.addAttribute("search", search);
                model.addAttribute("orderBy", orderBy);
                model.addAttribute("currentGenre", genreParam);

                model.addAttribute("page", "movieList");
                return "template";
            } catch (NullPointerException e) {
                return "redirect:/";
            }
        } else {
            return "redirect:/login?redirect=/movies/" + page;
        }
    }

}
