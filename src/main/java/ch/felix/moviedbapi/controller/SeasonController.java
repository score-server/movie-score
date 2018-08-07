package ch.felix.moviedbapi.controller;

import ch.felix.moviedbapi.data.entity.Season;
import ch.felix.moviedbapi.data.repository.SeasonRepository;
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
@RequestMapping("season")
public class SeasonController {

    private SeasonRepository seasonRepository;

    private UserIndicatorService userIndicatorService;

    public SeasonController(SeasonRepository seasonRepository, UserIndicatorService userIndicatorService) {
        this.seasonRepository = seasonRepository;
        this.userIndicatorService = userIndicatorService;
    }

    @GetMapping(value = "/{seasonId}", produces = "application/json")
    public String getOneSeason(@PathVariable("seasonId") String seasonParam, Model model, HttpServletRequest request) {
        userIndicatorService.allowGuest(model, request);

        Season season = seasonRepository.findSeasonById(Long.valueOf(seasonParam));

        model.addAttribute("season", season);
        model.addAttribute("page", "season");
        return "template";
    }

}
