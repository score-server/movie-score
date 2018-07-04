package ch.felix.moviedbapi.controller;

import ch.felix.moviedbapi.data.entity.Episode;
import ch.felix.moviedbapi.data.repository.EpisodeRepository;
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
@RequestMapping("episode")
public class EpisodeController {

    private EpisodeRepository episodeRepository;

    public EpisodeController(EpisodeRepository episodeRepository) {
        this.episodeRepository = episodeRepository;
    }

    @GetMapping(value = "/{episodeId}", produces = "application/json")
    public String getOneEpisode(@PathVariable("episodeId") String episodeId, Model model) {
        model.addAttribute("episode", episodeRepository.findEpisodeById(Long.valueOf(episodeId)));
        model.addAttribute("comments", episodeRepository.findEpisodeById(Long.valueOf(episodeId)).getComments());
        model.addAttribute("page", "episode");
        return "template";
    }


}
