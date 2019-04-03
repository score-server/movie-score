package ch.wetwer.moviedbapi.controller;

import ch.wetwer.moviedbapi.data.episode.EpisodeDao;
import ch.wetwer.moviedbapi.data.movie.MovieDao;
import ch.wetwer.moviedbapi.data.serie.SerieDao;
import ch.wetwer.moviedbapi.data.updatelog.UpdateLogDao;
import ch.wetwer.moviedbapi.service.auth.UserAuthService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Wetwer
 * @project movie-score
 */

@Controller
@RequestMapping("about")
public class AboutController {

    private EpisodeDao episodeDao;
    private MovieDao movieDao;
    private SerieDao serieDao;
    private UpdateLogDao updateLogDao;

    private UserAuthService userAuthService;

    public AboutController(EpisodeDao episodeDao, MovieDao movieDao, SerieDao serieDao, UpdateLogDao updateLogDao,
                           UserAuthService userAuthService) {
        this.episodeDao = episodeDao;
        this.movieDao = movieDao;
        this.updateLogDao = updateLogDao;
        this.serieDao = serieDao;
        this.userAuthService = userAuthService;
    }

    @GetMapping
    public String getAboutView(Model model, HttpServletRequest request) {
        if (userAuthService.isUser(model, request)) {
            userAuthService.log(this.getClass(), request);
            model.addAttribute("movies", movieDao.getAll().size());
            model.addAttribute("latest", movieDao.getLatestInfo());
            model.addAttribute("series", serieDao.getAll().size());
            model.addAttribute("episodes", episodeDao.getAll().size());
            model.addAttribute("updateLogs", updateLogDao.getAll());
            model.addAttribute("page", "about");
            return "template";
        } else {
            return "redirect:/login?redirect=/about";
        }
    }
}
