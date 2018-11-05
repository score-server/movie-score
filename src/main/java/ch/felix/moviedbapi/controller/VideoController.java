package ch.felix.moviedbapi.controller;

import ch.felix.moviedbapi.data.dto.EpisodeDto;
import ch.felix.moviedbapi.data.dto.MovieDto;
import ch.felix.moviedbapi.service.MultipartFileSender;
import ch.felix.moviedbapi.service.UserAuthService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.nio.file.Paths;

/**
 * @author Wetwer
 * @project movie-db
 */
@Controller
@RequestMapping("video")
public class VideoController {

    private MovieDto movieDto;
    private EpisodeDto episodeDto;

    private UserAuthService userAuthService;

    public VideoController(MovieDto movieDto, EpisodeDto episodeDto,
                           UserAuthService userAuthService) {
        this.movieDto = movieDto;
        this.episodeDto = episodeDto;
        this.userAuthService = userAuthService;
    }

    @GetMapping(value = "/movie/{movieId}")
    public void getMovie(@PathVariable("movieId") String movieId, HttpServletRequest request,
                         HttpServletResponse response) throws Exception {
        if (userAuthService.isUser(request)) {
            MultipartFileSender.fromPath(Paths.get(movieDto.getById(Long.valueOf(movieId)).getVideoPath()))
                    .with(request)
                    .with(response)
                    .serveResource();
        }
    }

    @GetMapping(value = "/episode/{episodeId}")
    public void getEpisode(@PathVariable("episodeId") String episodeId,
                           HttpServletRequest request, HttpServletResponse response) throws Exception {
        if (userAuthService.isUser(request)) {
            MultipartFileSender.fromPath(Paths.get(episodeDto.getById(Long.valueOf(episodeId)).getPath()))
                    .with(request)
                    .with(response)
                    .serveResource();
        }
    }
}
