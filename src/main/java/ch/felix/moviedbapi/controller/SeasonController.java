package ch.felix.moviedbapi.controller;

import ch.felix.moviedbapi.data.entity.Season;
import ch.felix.moviedbapi.data.repository.SeasonRepository;
import ch.felix.moviedbapi.data.repository.SerieRepository;
import java.util.List;
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
    private SerieRepository serieRepository;

    public SeasonController(SeasonRepository seasonRepository, SerieRepository serieRepository) {
        this.seasonRepository = seasonRepository;
        this.serieRepository = serieRepository;
    }

    @GetMapping("/serie/{serieId}")
    public @ResponseBody
    List<Season> getForSerie(@PathVariable("serieId") String serieParam) {
        return seasonRepository.findSeasonsBySerie(serieRepository.findSerieById(Long.valueOf(serieParam)));
    }

    @GetMapping("/{seasonId}")
    public @ResponseBody
    Season getOneSeason(@PathVariable("seasonId") String seasonParam) {
        return seasonRepository.findSeasonById(Long.valueOf(seasonParam));
    }

}
