package ch.wetwer.moviedbapi.controller;

import ch.wetwer.moviedbapi.data.episode.Episode;
import ch.wetwer.moviedbapi.data.episode.EpisodeDao;
import ch.wetwer.moviedbapi.data.time.TimeDao;
import ch.wetwer.moviedbapi.data.user.User;
import ch.wetwer.moviedbapi.service.ActivityService;
import ch.wetwer.moviedbapi.service.EpisodeService;
import ch.wetwer.moviedbapi.service.VideoConverterService;
import ch.wetwer.moviedbapi.service.auth.UserAuthService;
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
 * @project movie-score
 */

@Slf4j
@Controller
@RequestMapping("episode")
public class EpisodeController {

    private EpisodeDao episodeDao;
    private TimeDao timeDao;

    private UserAuthService userAuthService;
    private ActivityService activityService;
    private EpisodeService episodeService;
    private VideoConverterService videoConverterService;

    public EpisodeController(EpisodeDao episodeDao, TimeDao timeDao, UserAuthService userAuthService,
                             ActivityService activityService, EpisodeService episodeService,
                             VideoConverterService videoConverterService) {
        this.episodeDao = episodeDao;
        this.timeDao = timeDao;
        this.userAuthService = userAuthService;
        this.activityService = activityService;
        this.episodeService = episodeService;
        this.videoConverterService = videoConverterService;
    }

    @GetMapping(value = "{episodeId}")
    public String getOneEpisode(@PathVariable("episodeId") Long episodeId, Model model, HttpServletRequest request) {
        if (userAuthService.isUser(model, request)) {
            userAuthService.log(this.getClass(), request);

            User user = userAuthService.getUser(request).getUser();
            Episode episode = episodeDao.getById(episodeId);

            model.addAttribute("episode", episode);
            model.addAttribute("comments", episode.getComments());
            try {
                model.addAttribute("time", timeDao.getByUserAndEpisode(user, episode).getTime());
            } catch (NullPointerException e) {
                model.addAttribute("time", 0);
            }

            activityService.log(user.getName() + " gets Episode " +
                            "<a href=\"/episode/" + episode.getId() + "\">" + episode.getFullTitle() + "</a>",
                    userAuthService.getUser(request).getUser());
            model.addAttribute("nextEpisode", episodeService.getNextEpisode(episode));
            model.addAttribute("page", "episode");
            return "template";
        } else {
            return "redirect:/login?redirect=/episode/" + episodeId;
        }
    }


    @PostMapping("{episodeId}/path")
    public String getOneEpisode(@PathVariable("episodeId") Long episodeId, @RequestParam("path") String path, HttpServletRequest request) {
        if (userAuthService.isAdministrator(request)) {
            User user = userAuthService.getUser(request).getUser();
            Episode episode = episodeDao.getById(episodeId);
            episode.setPath(path);
            episodeDao.save(episode);
            activityService.log(user.getName() + " changed Path on " +
                            "<a href=\"/episode/" + episode.getId() + "\">" + episode.getFullTitle() + "</a>",
                    user);
            return "redirect:/episode/" + episodeId + "?path";
        } else {
            return "redirect:/episode/" + episodeId;
        }
    }

    @PostMapping(value = "convert/{episodeId}")
    public String convert(@PathVariable("episodeId") Long episodeId, HttpServletRequest request) {
        Episode episode = episodeDao.getById(episodeId);
        if (userAuthService.isAdministrator(request)) {
            userAuthService.log(this.getClass(), request);
            if (episode.getPath().endsWith(".mkv")) {
                episode.setConvertPercentage(0);
                episodeDao.save(episode);

                videoConverterService.convertEpisodeToMp4(episode);
            }
            return "redirect:/season/" + episode.getSeason().getId() + "";
        } else {
            return "redirect:/login?redirect=/season/" + episode.getSeason().getId() + "?error";
        }
    }
}
