package ch.felix.moviedbapi.controller;

import ch.felix.moviedbapi.data.dao.EpisodeDao;
import ch.felix.moviedbapi.data.dao.TimeDao;
import ch.felix.moviedbapi.data.entity.Episode;
import ch.felix.moviedbapi.data.entity.Season;
import ch.felix.moviedbapi.data.entity.Serie;
import ch.felix.moviedbapi.data.entity.User;
import ch.felix.moviedbapi.service.ActivityService;
import ch.felix.moviedbapi.service.auth.UserAuthService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Wetwer
 * @project movie-db
 */

@Slf4j
@Controller
@RequestMapping("episode")
public class EpisodeController {

    private EpisodeDao episodeDao;
    private TimeDao timeDao;

    private UserAuthService userAuthService;
    private ActivityService activityService;

    public EpisodeController(EpisodeDao episodeDao, TimeDao timeDao,
                             UserAuthService userAuthService, ActivityService activityService) {
        this.episodeDao = episodeDao;
        this.timeDao = timeDao;
        this.userAuthService = userAuthService;
        this.activityService = activityService;
    }

    @GetMapping(value = "/{episodeId}")
    public String getOneEpisode(@PathVariable("episodeId") String episodeId, Model model, HttpServletRequest request) {
        if (userAuthService.isUser(model, request)) {

            final User user = userAuthService.getUser(request).getUser();
            final Episode episode = episodeDao.getById(Long.valueOf(episodeId));

            model.addAttribute("episode", episode);
            model.addAttribute("comments", episode.getComments());
            try {
                model.addAttribute("time", timeDao.getByUserAndEpisode(user, episode).getTime());
            } catch (NullPointerException e) {
                model.addAttribute("time", 0);
            }

            activityService.log(user.getName() + " gets Episode " + episode.getFullTitle(),
                    userAuthService.getUser(request).getUser());
            model.addAttribute("nextEpisode", getNextEpisode(episode));
            model.addAttribute("page", "episode");
            return "template";
        } else {
            return "redirect:/login?redirect=/episode/" + episodeId;
        }
    }

    private Episode getNextEpisode(Episode episode) {
        final Season season = episode.getSeason();
        for (Episode nextEpisode : season.getEpisodes()) {
            if (nextEpisode.getEpisode() == episode.getEpisode() + 1) {
                return nextEpisode;
            }
        }

        final Serie serie = season.getSerie();
        for (Season nextSeason : serie.getSeasons()) {
            if (nextSeason.getSeason() == season.getSeason() + 1)
                for (Episode nextEpisode : nextSeason.getEpisodes()) {
                    if (nextEpisode.getEpisode() == 1) {
                        return nextEpisode;
                    }
                }
        }
        return null;
    }

    @PostMapping("{episodeId}/path")
    public String getOneEpisode(@PathVariable("episodeId") Long episodeId, @RequestParam("path") String path, HttpServletRequest request) {
        if (userAuthService.isAdministrator(request)) {
            final User user = userAuthService.getUser(request).getUser();
            final Episode episode = episodeDao.getById(episodeId);
            episode.setPath(path);
            episodeDao.save(episode);
            activityService.log(user.getName() + " changed Path on "
                    + episode.getFullTitle() + " to " + path, user);
            return "redirect:/episode/" + episodeId + "?path";
        } else {
            return "redirect:/episode/" + episodeId;
        }
    }
}
