package ch.felix.moviedbapi.controller;

import ch.felix.moviedbapi.data.repository.EpisodeRepository;
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
@RequestMapping("episode")
public class EpisodeController {

    private EpisodeRepository episodeRepository;

    public EpisodeController(EpisodeRepository episodeRepository) {
        this.episodeRepository = episodeRepository;
    }

    @GetMapping("/season/{seasonId}")
    public String getForSeason(@PathVariable("seasonId") String seasonParam) {
        episodeRepository.findEpisodesBySeasonFk(Long.valueOf(seasonParam));
        return "json";
    }

    @GetMapping("/{episodeId}")
    public String getOneEpisode(@PathVariable("episodeId") String episodeId) {
        episodeRepository.findEpisodeById(Long.valueOf(episodeId));
        return "json";
    }


}
