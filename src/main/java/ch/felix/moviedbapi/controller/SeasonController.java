package ch.felix.moviedbapi.controller;

import ch.felix.moviedbapi.data.repository.SeasonRepository;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

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

    @GetMapping("/serie/{serieId}")
    public String getForSerie(@PathVariable("serieId") String serieParam) {
        seasonRepository.findSeasonsBySerieFk(Long.valueOf(serieParam));
        return "json";
    }

    @GetMapping("/{seasonId}")
    public String getOneSeason(@PathVariable("seasonId") String seasonParam) {
        seasonRepository.findSeasonById(Long.valueOf(seasonParam));
        return "json";
    }

}
