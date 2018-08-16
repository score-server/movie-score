package ch.felix.moviedbapi.controller;

import ch.felix.moviedbapi.data.entity.Episode;
import ch.felix.moviedbapi.data.repository.EpisodeRepository;
import ch.felix.moviedbapi.service.ActivityService;
import ch.felix.moviedbapi.service.UserIndicatorService;
import lombok.extern.slf4j.Slf4j;
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

@Slf4j
@Controller
@RequestMapping("episode")
public class EpisodeController {

    private EpisodeRepository episodeRepository;

    private UserIndicatorService userIndicatorService;
    private ActivityService activityService;

    public EpisodeController(EpisodeRepository episodeRepository, UserIndicatorService userIndicatorService, ActivityService activityService) {
        this.episodeRepository = episodeRepository;
        this.userIndicatorService = userIndicatorService;
        this.activityService = activityService;
    }

    @GetMapping(value = "/{episodeId}", produces = "application/json")
    public String getOneEpisode(@PathVariable("episodeId") String episodeId, Model model, HttpServletRequest request) {
        if (userIndicatorService.isUser(model, request)) {

            Episode episode = episodeRepository.findEpisodeById(Long.valueOf(episodeId));

            model.addAttribute("episode", episode);
            model.addAttribute("comments", episode.getComments());

            activityService.log(userIndicatorService.getUser(request).getUser().getName()
                    + " gets Episode " + episode.getSeason().getSerie().getTitle()
                    + " S" + episode.getSeason().getSeason()
                    + "E" + episode.getEpisode());


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
