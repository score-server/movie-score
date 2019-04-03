package ch.wetwer.moviedbapi.controller;

import ch.wetwer.moviedbapi.data.episode.EpisodeDao;
import ch.wetwer.moviedbapi.data.movie.MovieDao;
import ch.wetwer.moviedbapi.data.uploadFile.UploadFileDao;
import ch.wetwer.moviedbapi.service.MultipartFileSender;
import ch.wetwer.moviedbapi.service.auth.UserAuthService;
import ch.wetwer.moviedbapi.service.filehandler.SettingsService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.nio.file.Paths;

/**
 * @author Wetwer
 * @project movie-score
 */
@Controller
@RequestMapping("video")
public class VideoController {

    private MovieDao movieDao;
    private EpisodeDao episodeDao;
    private UploadFileDao uploadFileDao;

    private UserAuthService userAuthService;
    private SettingsService settingsService;

    public VideoController(MovieDao movieDao, EpisodeDao episodeDao,
                           UploadFileDao uploadFileDao, UserAuthService userAuthService,
                           SettingsService settingsService) {
        this.movieDao = movieDao;
        this.episodeDao = episodeDao;
        this.uploadFileDao = uploadFileDao;
        this.userAuthService = userAuthService;
        this.settingsService = settingsService;
    }

    @GetMapping(value = "/movie/{movieId}")
    public void getMovie(@PathVariable("movieId") Long movieId,
                         HttpServletRequest request, HttpServletResponse response) throws Exception {
        if (userAuthService.isUser(request)) {
            userAuthService.log(this.getClass(), request);
            MultipartFileSender.fromPath(Paths.get(movieDao.getById(movieId).getVideoPath()))
                    .with(request)
                    .with(response)
                    .serveResource();
        }
    }

    @GetMapping(value = "/episode/{episodeId}")
    public void getEpisode(@PathVariable("episodeId") Long episodeId,
                           HttpServletRequest request, HttpServletResponse response) throws Exception {
        if (userAuthService.isUser(request)) {
            userAuthService.log(this.getClass(), request);

            MultipartFileSender.fromPath(Paths.get(episodeDao.getById(episodeId).getPath()))
                    .with(request)
                    .with(response)
                    .serveResource();
        }
    }

    @GetMapping("preview/{hash}")
    public void getPreviewMovie(@PathVariable("hash") int hash,
                                HttpServletRequest request, HttpServletResponse response) throws Exception {
        if (userAuthService.isUser(request)) {
            userAuthService.log(this.getClass(), request);

            MultipartFileSender.fromPath(Paths.get(
                    settingsService.getKey("moviePath") + "_tmp/" + uploadFileDao.getByHash(hash).getFilename()))
                    .with(request)
                    .with(response)
                    .serveResource();
        }
    }


}
