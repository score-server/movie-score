package ch.felix.moviedbapi.controller;

import ch.felix.moviedbapi.data.entity.Genre;
import ch.felix.moviedbapi.data.entity.Movie;
import ch.felix.moviedbapi.data.repository.GenreRepository;
import ch.felix.moviedbapi.service.DuplicateService;
import ch.felix.moviedbapi.service.PageService;
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
import java.util.Collections;
import java.util.List;

@Controller
@RequestMapping("movies")
public class MoviesController {

    private GenreRepository genreRepository;

    private SearchService searchService;
    private DuplicateService duplicateService;
    private UserIndicatorService userIndicatorService;
    private PageService pageService;

    public MoviesController(GenreRepository genreRepository, SearchService searchService,
                            DuplicateService duplicateService, UserIndicatorService userIndicatorService,
                            PageService pageService) {
        this.genreRepository = genreRepository;
        this.searchService = searchService;
        this.duplicateService = duplicateService;
        this.userIndicatorService = userIndicatorService;
        this.pageService = pageService;
    }

    @GetMapping("{page}")
    public String getMovies(@PathVariable(name = "page", required = false) Integer page,
                            @RequestParam(name = "search", required = false, defaultValue = "") String search,
                            @RequestParam(name = "orderBy", required = false, defaultValue = "") String orderBy,
                            @RequestParam(name = "genre", required = false, defaultValue = "") String genreParam,
                            Model model, HttpServletRequest request) {
        if (userIndicatorService.isUser(model, request)) {
            try {
                List<String> genres = new ArrayList<>();
                for (Genre genre : genreRepository.findGenreByOrderByName()) {
                    genres.add(genre.getName());
                }

                Collections.sort(genres, (s1, s2) -> s1.compareToIgnoreCase(s2));

                List<Movie> movies = pageService.getPage(searchService.searchMovies(search, orderBy, genreParam), page);

                genres = duplicateService.removeStringDuplicates(genres);
                model.addAttribute("genres", genres);
                model.addAttribute("movies", movies);
                model.addAttribute("all", searchService.searchMoviesTop("", orderBy, genreParam));

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
