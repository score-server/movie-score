package ch.felix.moviedbapi.controller;

import ch.felix.moviedbapi.data.repository.MovieRepository;
import ch.felix.moviedbapi.data.repository.SerieRepository;
import ch.felix.moviedbapi.service.CookieService;
import ch.felix.moviedbapi.service.SettingsService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("")
public class DashboardController {

    private MovieRepository movieRepository;
    private SerieRepository serieRepository;

    private CookieService cookieService;
    private SettingsService settingsService;

    public DashboardController(MovieRepository movieRepository, SerieRepository serieRepository,
                               CookieService cookieService, SettingsService settingsService) {
        this.movieRepository = movieRepository;
        this.serieRepository = serieRepository;
        this.cookieService = cookieService;
        this.settingsService = settingsService;
    }

    @GetMapping
    public String getDashboard(Model model, HttpServletRequest request) {
        try {
            model.addAttribute("currentUser", cookieService.getCurrentUser(request));
        } catch (NullPointerException e) {
        }

        model.addAttribute("movies", movieRepository.findAll());
        model.addAttribute("series", serieRepository.findAll());

        model.addAttribute("moviePath", settingsService.getKey("moviePath"));
        model.addAttribute("seriePath", settingsService.getKey("seriePath"));

        model.addAttribute("page", "home");
        return "template";
    }

}
