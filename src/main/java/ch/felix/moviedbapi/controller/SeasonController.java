package ch.felix.moviedbapi.controller;

import ch.felix.moviedbapi.data.dto.EpisodeDto;
import ch.felix.moviedbapi.data.dto.SeasonDto;
import ch.felix.moviedbapi.data.entity.Season;
import ch.felix.moviedbapi.service.UserAuthService;
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

    private SeasonDto seasonDto;
    private EpisodeDto episodeDto;

    private UserAuthService userAuthService;

    public SeasonController(SeasonDto seasonDto, EpisodeDto episodeDto, UserAuthService userAuthService) {
        this.seasonDto = seasonDto;
        this.userAuthService = userAuthService;
        this.episodeDto = episodeDto;
    }

    @GetMapping(value = "/{seasonId}")
    public String getOneSeason(@PathVariable("seasonId") String seasonId, Model model, HttpServletRequest request) {
        if (userAuthService.isUser(model, request)) {
            Season season = seasonDto.getById(Long.valueOf(seasonId));

            model.addAttribute("season", season);
            model.addAttribute("episodes", episodeDto.getBySeason(season));
            model.addAttribute("page", "season");
            return "template";
        } else {
            return "redirect:/login?redirect=/season/" + seasonId;
        }
    }

}
