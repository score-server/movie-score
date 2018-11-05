package ch.felix.moviedbapi.controller;

import ch.felix.moviedbapi.data.dto.GenreDto;
import ch.felix.moviedbapi.data.entity.Genre;
import ch.felix.moviedbapi.data.entity.Movie;
import ch.felix.moviedbapi.service.DuplicateService;
import ch.felix.moviedbapi.service.PageService;
import ch.felix.moviedbapi.service.SearchService;
import ch.felix.moviedbapi.service.UserAuthService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("movies")
public class MoviesController {

    private GenreDto genreDto;

    private SearchService searchService;
    private DuplicateService duplicateService;
    private UserAuthService userAuthService;
    private PageService pageService;

    public MoviesController(GenreDto genreDto, SearchService searchService,
                            DuplicateService duplicateService, UserAuthService userAuthService,
                            PageService pageService) {
        this.genreDto = genreDto;
        this.searchService = searchService;
        this.duplicateService = duplicateService;
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
                List<String> genres = new ArrayList<>();
                for (Genre genre : genreDto.getAll()) {
                    genres.add(genre.getName());
                }

                genres.sort(String::compareToIgnoreCase);

                List<Movie> movies = pageService.getPage(searchService.searchMovies(search, orderBy, genreParam), page);

                genres = duplicateService.removeStringDuplicates(genres);
                model.addAttribute("genres", genres);
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
