package ch.felix.moviedbapi.controller;

import ch.felix.moviedbapi.data.entity.Episode;
import ch.felix.moviedbapi.data.repository.EpisodeRepository;
import ch.felix.moviedbapi.service.UserIndicatorService;
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

    private UserIndicatorService userIndicatorService;

    public EpisodeController(EpisodeRepository episodeRepository, UserIndicatorService userIndicatorService) {
        this.episodeRepository = episodeRepository;
        this.userIndicatorService = userIndicatorService;
    }

    @GetMapping(value = "/{episodeId}", produces = "application/json")
    public String getOneEpisode(@PathVariable("episodeId") String episodeId, Model model, HttpServletRequest request) {
        if (userIndicatorService.isUser(model, request)) {

            Episode episode = episodeRepository.findEpisodeById(Long.valueOf(episodeId));

            model.addAttribute("episode", episode);
            model.addAttribute("comments", episode.getComments());

            try {
                model.addAttribute("nextEpisode", episodeRepository.findEpisodeById(
                        Long.valueOf(episodeId) + 1));
            } catch (NullPointerException | IndexOutOfBoundsException e) {
                e.printStackTrace();
            }

            model.addAttribute("page", "episode");
            return "template";
        } else {
            return "redirect:/login?redirect=/episode/" + episodeId;
        }
    }

}
