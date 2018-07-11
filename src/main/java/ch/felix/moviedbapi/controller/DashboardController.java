package ch.felix.moviedbapi.controller;

import ch.felix.moviedbapi.data.repository.MovieRepository;
import ch.felix.moviedbapi.data.repository.SerieRepository;
import ch.felix.moviedbapi.service.CookieService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("")
public class DashboardController {

    private MovieRepository movieRepository;
    private SerieRepository serieRepository;

    private CookieService cookieService;

    public DashboardController(MovieRepository movieRepository, SerieRepository serieRepository,
                               CookieService cookieService) {
        this.movieRepository = movieRepository;
        this.serieRepository = serieRepository;
        this.cookieService = cookieService;
    }

    @GetMapping
    public String getDashboard(Model model, HttpServletRequest request) {
        try {
            model.addAttribute("currentUser", cookieService.getCurrentUser(request));
        } catch (NullPointerException e) {
        }

        model.addAttribute("movies", movieRepository.findTop50ByOrderByTitle());
        model.addAttribute("series", serieRepository.findAll());

        model.addAttribute("page", "home");
        return "template";
    }


    @GetMapping("search")
    public String searchMovie(@RequestParam("querry") String querry, Model model) {
        try {
            model.addAttribute("movies", movieRepository.findTop50ByTitleContaining(querry));
            model.addAttribute("series", serieRepository.findTop50ByTitleContaining(querry));
            model.addAttribute("page", "home");
            return "template";
        } catch (NumberFormatException | NullPointerException e) {
            e.printStackTrace();
            return "redirect:/";
        }
    }
}
