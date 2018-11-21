package ch.felix.moviedbapi.controller;

import ch.felix.moviedbapi.data.dao.EpisodeDao;
import ch.felix.moviedbapi.data.dao.SeasonDao;
import ch.felix.moviedbapi.data.entity.Season;
import ch.felix.moviedbapi.service.auth.UserAuthService;
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
@RequestMapping("season")
public class SeasonController {

    private SeasonDao seasonDao;
    private EpisodeDao episodeDao;

    private UserAuthService userAuthService;

    public SeasonController(SeasonDao seasonDao, EpisodeDao episodeDao, UserAuthService userAuthService) {
        this.seasonDao = seasonDao;
        this.userAuthService = userAuthService;
        this.episodeDao = episodeDao;
    }

    @GetMapping(value = "/{seasonId}")
    public String getOneSeason(@PathVariable("seasonId") String seasonId, Model model, HttpServletRequest request) {
        if (userAuthService.isUser(model, request)) {
            Season season = seasonDao.getById(Long.valueOf(seasonId));

            model.addAttribute("season", season);
            model.addAttribute("episodes", episodeDao.getBySeason(season));
            model.addAttribute("page", "season");
            return "template";
        } else {
            return "redirect:/login?redirect=/season/" + seasonId;
        }
    }

}
