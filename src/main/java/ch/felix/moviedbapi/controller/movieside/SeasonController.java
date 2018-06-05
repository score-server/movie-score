package ch.felix.moviedbapi.controller.movieside;

import ch.felix.moviedbapi.data.entity.Season;
import ch.felix.moviedbapi.data.repository.SeasonRepository;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

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

    @GetMapping("/{seasonId}")
    public @ResponseBody
    Season getOneSeason(@PathVariable("seasonId") String seasonParam) {
        return seasonRepository.findSeasonById(Long.valueOf(seasonParam));
    }

}