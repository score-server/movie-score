package ch.felix.moviedbapi.controller;

import ch.felix.moviedbapi.data.entity.Nice;
import ch.felix.moviedbapi.data.repository.EpisodeRepository;
import ch.felix.moviedbapi.data.repository.MovieRepository;
import ch.felix.moviedbapi.data.repository.NiceRepository;
import ch.felix.moviedbapi.service.UserIndicatorService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
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
    private NiceRepository niceRepository;

    public AboutController(EpisodeRepository episodeRepository, MovieRepository movieRepository,
                           UserIndicatorService userIndicatorService, NiceRepository niceRepository) {
        this.episodeRepository = episodeRepository;
        this.movieRepository = movieRepository;
        this.userIndicatorService = userIndicatorService;
        this.niceRepository = niceRepository;
    }

    @GetMapping
    public String getAboutPage(Model model, HttpServletRequest request) {
        if (userIndicatorService.isUser(model, request)) {
            model.addAttribute("episodes", episodeRepository.findAll().size());
            model.addAttribute("movies", movieRepository.findAll().size());
            Nice nice = niceRepository.findNiceById(1L);
            try {
                nice.getId();
            } catch (NullPointerException e) {
                nice = new Nice();
                nice.setLove(0);
                niceRepository.save(nice);
            }
            model.addAttribute("love", nice.getLove());
            model.addAttribute("page", "about");
            return "template";
        } else {
            return "redirect:/login?redirect=/about";
        }

    }

    @PostMapping("love")
    public String sendNice() {
        Nice nice = niceRepository.findNiceById(1L);
        Integer lik = nice.getLove() + 1;
        nice.setLove(lik);
        niceRepository.save(nice);

        return "redirect:/about";
    }

}
