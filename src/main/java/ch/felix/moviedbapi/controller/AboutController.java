package ch.felix.moviedbapi.controller;

import ch.felix.moviedbapi.data.dto.EpisodeDto;
import ch.felix.moviedbapi.data.dto.MovieDto;
import ch.felix.moviedbapi.data.repository.SerieRepository;
import ch.felix.moviedbapi.service.UserAuthService;
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

    private EpisodeDto episodeDto;
    private MovieDto movieDto;
    private SerieRepository serieRepository;

    private UserAuthService userAuthService;

    public AboutController(EpisodeDto episodeDto, MovieDto movieDto, UserAuthService userAuthService,
                           SerieRepository serieRepository) {
        this.episodeDto = episodeDto;
        this.movieDto = movieDto;
        this.userAuthService = userAuthService;
        this.serieRepository = serieRepository;
    }

    @GetMapping
    public String getAboutPage(Model model, HttpServletRequest request) {
        if (userAuthService.isUser(model, request)) {
            model.addAttribute("movies", movieDto.getAll().size());
            model.addAttribute("series", serieRepository.findAll().size());
            model.addAttribute("episodes", episodeDto.getAll().size());
            model.addAttribute("page", "about");
            return "template";
        } else {
            return "redirect:/login?redirect=/about";
        }

    }
}
