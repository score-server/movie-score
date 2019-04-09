package ch.wetwer.moviedbapi.controller;

import ch.wetwer.moviedbapi.data.movie.MovieDao;
import ch.wetwer.moviedbapi.data.previewimg.PreviewImageDao;
import ch.wetwer.moviedbapi.service.MultipartFileSender;
import ch.wetwer.moviedbapi.service.auth.UserAuthService;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.persistence.EntityNotFoundException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.nio.file.Paths;

/**
 * @author Wetwer
 * @project movie-score
 */
@Controller
@RequestMapping("preview")
public class PreviewController {

    private MovieDao movieDao;
    private PreviewImageDao previewImageDao;

    private UserAuthService userAuthService;

    public PreviewController(MovieDao movieDao, PreviewImageDao previewImageDao, UserAuthService userAuthService) {
        this.movieDao = movieDao;
        this.previewImageDao = previewImageDao;
        this.userAuthService = userAuthService;
    }

    @GetMapping(value = "/movie/{movieId}")
    public void getPreviewFile(@PathVariable("movieId") Long movieId,
                               HttpServletRequest request, HttpServletResponse response) throws Exception {
        if (userAuthService.isUser(request)) {
            userAuthService.log(this.getClass(), request);
            MultipartFileSender.fromPath(Paths.get(movieDao.getById(movieId).getPreviewPath()))
                    .with(request)
                    .with(response)
                    .serveResource();
        }
    }

    @ResponseBody
    @GetMapping(value = "/img/{previewImgId}")
    public ResponseEntity<ByteArrayResource> getPreviewImg(@PathVariable("previewImgId") Long previewImgId) {
        try {
            ByteArrayResource file = new ByteArrayResource(
                    previewImageDao.getById(previewImgId).getPreviewImg());
            return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION,
                    "attachment; filename=\"" + file.getFilename() + "\"").body(file);
        } catch (EntityNotFoundException ignored) {
            return null;
        }
    }
}
