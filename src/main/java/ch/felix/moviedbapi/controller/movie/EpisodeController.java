package ch.felix.moviedbapi.controller.movie;

import ch.felix.moviedbapi.data.entity.Episode;
import ch.felix.moviedbapi.data.repository.EpisodeRepository;
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

@RestController
@RequestMapping("episode")
public class EpisodeController {

    private EpisodeRepository episodeRepository;

    public EpisodeController(EpisodeRepository episodeRepository) {
        this.episodeRepository = episodeRepository;
    }

    @GetMapping("/{episodeId}")
    public Episode getOneEpisode(@PathVariable("episodeId") String episodeId) {
        return episodeRepository.findEpisodeById(Long.valueOf(episodeId));
    }


}
