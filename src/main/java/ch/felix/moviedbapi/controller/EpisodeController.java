package ch.felix.moviedbapi.controller;

import ch.felix.moviedbapi.data.entity.Episode;
import ch.felix.moviedbapi.data.repository.EpisodeRepository;
import ch.felix.moviedbapi.data.repository.SeasonRepository;
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
@RequestMapping("episode")
public class EpisodeController {

    private EpisodeRepository episodeRepository;
    private SeasonRepository seasonRepository;

    public EpisodeController(EpisodeRepository episodeRepository, SeasonRepository seasonRepository) {
        this.episodeRepository = episodeRepository;
        this.seasonRepository = seasonRepository;
    }

    @GetMapping("/season/{seasonId}")
    public @ResponseBody
    List<Episode> getForSeason(@PathVariable("seasonId") String seasonParam) {
        return episodeRepository.findEpisodesBySeason(seasonRepository.findSeasonById(Long.valueOf(seasonParam)));
    }

    @GetMapping("/{episodeId}")
    public @ResponseBody
    Episode getOneEpisode(@PathVariable("episodeId") String episodeId) {
        return episodeRepository.findEpisodeById(Long.valueOf(episodeId));
    }


}
