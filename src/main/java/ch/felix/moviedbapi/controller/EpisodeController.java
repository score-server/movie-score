package ch.felix.moviedbapi.controller;

import ch.felix.moviedbapi.data.repository.EpisodeRepository;
import ch.felix.moviedbapi.service.CookieService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Wetwer
 * @project movie-db
 */
@Controller
@RequestMapping("episode")
public class EpisodeController {

    private EpisodeRepository episodeRepository;

    private CookieService cookieService;

    public EpisodeController(EpisodeRepository episodeRepository, CookieService cookieService) {
        this.episodeRepository = episodeRepository;
        this.cookieService = cookieService;
    }

    @GetMapping(value = "/{episodeId}", produces = "application/json")
    public String getOneEpisode(@PathVariable("episodeId") String episodeId, Model model, HttpServletRequest request) {
        try {
            model.addAttribute("currentUser", cookieService.getCurrentUser(request));
        } catch (NullPointerException e) {
            return "redirect:/login?redirect=/episode/" + episodeId;
        }

        model.addAttribute("episode", episodeRepository.findEpisodeById(Long.valueOf(episodeId)));
        model.addAttribute("comments", episodeRepository.findEpisodeById(Long.valueOf(episodeId)).getComments());
        model.addAttribute("page", "episode");
        return "template";
    }

}
