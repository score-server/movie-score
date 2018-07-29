package ch.felix.moviedbapi.controller;

import ch.felix.moviedbapi.data.repository.EpisodeRepository;
import ch.felix.moviedbapi.data.repository.MovieRepository;
import ch.felix.moviedbapi.service.CookieService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("about")
public class AboutController {

    private CookieService cookieService;
    private EpisodeRepository episodeRepository;
    private MovieRepository movieRepository;

    public AboutController(CookieService cookieService, EpisodeRepository episodeRepository, MovieRepository movieRepository) {
        this.cookieService = cookieService;
        this.episodeRepository = episodeRepository;
        this.movieRepository = movieRepository;
    }

    @GetMapping
    public String getAboutPage(Model model, HttpServletRequest request) {
        try {
            model.addAttribute("currentUser", cookieService.getCurrentUser(request));
        } catch (NullPointerException e) {
        }

        model.addAttribute("episodes", episodeRepository.findAll().size());
        model.addAttribute("movies", movieRepository.findAll().size());
        model.addAttribute("page", "about");
        return "template";
    }


}
