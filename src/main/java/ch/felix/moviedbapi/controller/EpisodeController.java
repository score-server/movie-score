package ch.felix.moviedbapi.controller;

import ch.felix.moviedbapi.data.entity.Episode;
import ch.felix.moviedbapi.data.entity.Movie;
import ch.felix.moviedbapi.data.entity.User;
import ch.felix.moviedbapi.data.repository.EpisodeRepository;
import ch.felix.moviedbapi.data.repository.TimeRepository;
import ch.felix.moviedbapi.service.ActivityService;
import ch.felix.moviedbapi.service.UserIndicatorService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.io.File;

/**
 * @author Wetwer
 * @project movie-db
 */

@Slf4j
@Controller
@RequestMapping("episode")
public class EpisodeController {

    private EpisodeRepository episodeRepository;
    private TimeRepository timeRepository;

    private UserIndicatorService userIndicatorService;
    private ActivityService activityService;

    public EpisodeController(EpisodeRepository episodeRepository, TimeRepository timeRepository,
                             UserIndicatorService userIndicatorService, ActivityService activityService) {
        this.episodeRepository = episodeRepository;
        this.timeRepository = timeRepository;
        this.userIndicatorService = userIndicatorService;
        this.activityService = activityService;
    }

    @GetMapping(value = "/{episodeId}")
    public String getOneEpisode(@PathVariable("episodeId") String episodeId, Model model, HttpServletRequest request) {
        if (userIndicatorService.isUser(model, request)) {

            User user = userIndicatorService.getUser(request).getUser();
            Episode episode = episodeRepository.findEpisodeById(Long.valueOf(episodeId));

            model.addAttribute("episode", episode);
            model.addAttribute("comments", episode.getComments());
            try {
                model.addAttribute("time", timeRepository.findTimeByUserAndEpisode(user, episode).getTime());
            } catch (NullPointerException e) {
                model.addAttribute("time", 0);
            }

            activityService.log(user.getName()
                    + " gets Episode " + episode.getSeason().getSerie().getTitle()
                    + " S" + episode.getSeason().getSeason()
                    + "E" + episode.getEpisode(), userIndicatorService.getUser(request).getUser());


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

    @PostMapping("{episodeId}/path")
    public String getOneEpisode(@PathVariable("episodeId") Long episodeId, @RequestParam("path") String path, HttpServletRequest request) {
        if (userIndicatorService.isAdministrator(request)) {
            User user = userIndicatorService.getUser(request).getUser();
            Episode episode = episodeRepository.findEpisodeById(episodeId);
            episode.setPath(path);
            episodeRepository.save(episode);
            activityService.log(user.getName() + " changed Path on "
                    + "S" + episode.getSeason().getSeason()
                    + "E" + episode.getEpisode() + " to " + path, user);
            return "redirect:/episode/" + episodeId + "?path";
        } else {
            return "redirect:/episode/" + episodeId;
        }
    }
}
