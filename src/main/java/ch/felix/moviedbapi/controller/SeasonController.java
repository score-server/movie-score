package ch.felix.moviedbapi.controller;

import ch.felix.moviedbapi.data.entity.Season;
import ch.felix.moviedbapi.data.repository.SeasonRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

    public SeasonController(SeasonRepository seasonRepository) {
        this.seasonRepository = seasonRepository;
    }

    @GetMapping(value = "/{seasonId}", produces = "application/json")
    public String getOneSeason(@PathVariable("seasonId") String seasonParam, Model model) {
        model.addAttribute("season", seasonRepository.findSeasonById(Long.valueOf(seasonParam)));
        model.addAttribute("page", "season");
        return "template";

    }

}
