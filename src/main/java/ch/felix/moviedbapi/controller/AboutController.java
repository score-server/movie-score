package ch.felix.moviedbapi.controller;

import ch.felix.moviedbapi.data.dto.MovieDto;
import ch.felix.moviedbapi.data.entity.Nice;
import ch.felix.moviedbapi.data.repository.EpisodeRepository;
import ch.felix.moviedbapi.data.repository.NiceRepository;
import ch.felix.moviedbapi.data.repository.SerieRepository;
import ch.felix.moviedbapi.service.UserIndicatorService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Wetwer
 * @project movie-db
 */
@Controller
@RequestMapping("about")
public class AboutController {

    private EpisodeRepository episodeRepository;
    private MovieDto movieDto;

    private UserIndicatorService userIndicatorService;
    private NiceRepository niceRepository;
    private SerieRepository serieRepository;

    public AboutController(EpisodeRepository episodeRepository, MovieDto movieDto,
                           UserIndicatorService userIndicatorService, NiceRepository niceRepository,
                           SerieRepository serieRepository) {
        this.episodeRepository = episodeRepository;
        this.movieDto = movieDto;
        this.userIndicatorService = userIndicatorService;
        this.niceRepository = niceRepository;
        this.serieRepository = serieRepository;
    }

    @GetMapping
    public String getAboutPage(Model model, HttpServletRequest request) {
        if (userIndicatorService.isUser(model, request)) {
            model.addAttribute("movies", movieDto.getAll().size());
            model.addAttribute("series", serieRepository.findAll().size());
            model.addAttribute("episodes", episodeRepository.findAll().size());
            Nice nice = niceRepository.findNiceById(1L);
            try {
                nice.getId();
            } catch (NullPointerException e) {
                nice = new Nice();
                nice.setLove(0);
                niceRepository.save(nice);
            }
            model.addAttribute("love", nice.getLove());
            model.addAttribute("page", "about");
            return "template";
        } else {
            return "redirect:/login?redirect=/about";
        }

    }

    @PostMapping("love")
    public String sendNice() {
        Nice nice = niceRepository.findNiceById(1L);
        Integer lik = nice.getLove() + 1;
        nice.setLove(lik);
        niceRepository.save(nice);

        return "redirect:/about";
    }

}
