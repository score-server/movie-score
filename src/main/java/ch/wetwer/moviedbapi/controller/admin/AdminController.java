package ch.wetwer.moviedbapi.controller.admin;

import ch.wetwer.moviedbapi.data.movie.MovieDao;
import ch.wetwer.moviedbapi.service.SearchService;
import ch.wetwer.moviedbapi.service.auth.UserAuthService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("admin")
public class AdminController {

    private MovieDao movieDao;
    private UserAuthService userAuthService;
    private SearchService searchService;

    public AdminController(MovieDao movieDao, UserAuthService userAuthService, SearchService searchService) {
        this.movieDao = movieDao;
        this.userAuthService = userAuthService;
        this.searchService = searchService;
    }

    @GetMapping("movie")
    private String getMoviePanel(Model model, HttpServletRequest request) {
        if (userAuthService.isAdministrator(model, request)) {
            model.addAttribute("movieList", movieDao.getOrderByTitle());
            model.addAttribute("page", "adminMovie");
            return "template";
        }
        return "redirect:/?access";
    }

    @GetMapping("experimental")
    private String getExperimental(Model model, HttpServletRequest request) {
        if (userAuthService.isAdministrator(model, request)) {
            model.addAttribute("movieList", searchService.searchMovies("", "", "Horror"));
            model.addAttribute("page", "adminExperimental");
            return "template";
        }
        return "redirect:/?access";
    }
}
