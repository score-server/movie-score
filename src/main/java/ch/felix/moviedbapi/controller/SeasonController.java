package ch.felix.moviedbapi.controller;

import ch.felix.moviedbapi.data.entity.Season;
import ch.felix.moviedbapi.data.repository.SeasonRepository;
import ch.felix.moviedbapi.service.CookieService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Felix
 * @date 30.05.2018
 * <p>
 * Project: movie-db-api
 * Package: ch.felix.moviedbapi.controller
 **/

@Controller
@RequestMapping("season")
public class SeasonController {

    private SeasonRepository seasonRepository;
    private CookieService cookieService;

    public SeasonController(SeasonRepository seasonRepository, CookieService cookieService) {
        this.seasonRepository = seasonRepository;
        this.cookieService = cookieService;
    }

    @GetMapping(value = "/{seasonId}", produces = "application/json")
    public String getOneSeason(@PathVariable("seasonId") String seasonParam, Model model, HttpServletRequest request) {
        try {
            model.addAttribute("currentUser", cookieService.getCurrentUser(request));
        } catch (NullPointerException e) {
        }
        Season season = seasonRepository.findSeasonById(Long.valueOf(seasonParam));

        model.addAttribute("season", season);
        model.addAttribute("episodes", season.getEpisodes());
        model.addAttribute("page", "season");
        return "template";

    }

}
