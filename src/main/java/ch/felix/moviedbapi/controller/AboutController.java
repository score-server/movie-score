package ch.felix.moviedbapi.controller;

import ch.felix.moviedbapi.data.repository.EpisodeRepository;
import ch.felix.moviedbapi.data.repository.MovieRepository;
import ch.felix.moviedbapi.model.UserIndicator;
import ch.felix.moviedbapi.service.UserIndicatorService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Wetwer
 * @project movie-db
 */
@Controller
@RequestMapping("about")
public class AboutController {

    private EpisodeRepository episodeRepository;
    private MovieRepository movieRepository;

    private UserIndicatorService userIndicatorService;

    public AboutController(EpisodeRepository episodeRepository, MovieRepository movieRepository,
                           UserIndicatorService userIndicatorService) {
        this.episodeRepository = episodeRepository;
        this.movieRepository = movieRepository;
        this.userIndicatorService = userIndicatorService;
    }

    @GetMapping
    public String getAboutPage(Model model, HttpServletRequest request) {
        userIndicatorService.allowGuestAccess(model, request);

        model.addAttribute("episodes", episodeRepository.findAll().size());
        model.addAttribute("movies", movieRepository.findAll().size());
        model.addAttribute("page", "about");
        return "template";
    }


}
