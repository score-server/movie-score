package ch.wetwer.moviedbapi.controller;

import ch.wetwer.moviedbapi.data.movie.MovieDao;
import ch.wetwer.moviedbapi.data.subtitle.SubtitleDao;
import ch.wetwer.moviedbapi.service.SubtitleConverter;
import ch.wetwer.moviedbapi.service.auth.UserAuthService;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * @author Wetwer
 * @project movie-score
 * @package ch.wetwer.moviedbapi.controller
 * @created 26.11.2018
 **/

@Controller
@RequestMapping("subtitle")
public class SubtitleController {

    private MovieDao movieDao;
    private SubtitleDao subtitleDao;

    private UserAuthService userAuthService;
    private SubtitleConverter subtitleConverter;

    public SubtitleController(MovieDao movieDao, SubtitleDao subtitleDao, UserAuthService userAuthService,
                              SubtitleConverter subtitleConverter) {
        this.movieDao = movieDao;
        this.subtitleDao = subtitleDao;
        this.userAuthService = userAuthService;
        this.subtitleConverter = subtitleConverter;
    }

    @PostMapping("add/{movieId}")
    public String addSubtitles(@PathVariable("movieId") Long movieId,
                               @RequestParam("file") MultipartFile multipartFile,
                               @RequestParam("language") String language,
                               HttpServletRequest request) {
        if (userAuthService.isUser(request)) {
            userAuthService.log(this.getClass(), request);
            try {
                if (multipartFile.getOriginalFilename().endsWith(".vtt")) {
                    subtitleDao.addSubtitle(movieDao.getById(movieId), multipartFile, language,
                            userAuthService.getUser(request).getUser());
                    return "redirect:/movie/" + movieId + "?subtitle";
                } else if (multipartFile.getOriginalFilename().endsWith(".srt")) {
                    subtitleDao.addSubtitle(movieDao.getById(movieId), subtitleConverter.convert(multipartFile),
                            language, userAuthService.getUser(request).getUser());
                } else {
                    return "redirect:/movie/" + movieId + "?wrongFile";
                }
            } catch (IOException e) {
                e.printStackTrace();
                return "redirect:/movie/" + movieId;
            }
        }
        return "redirect:/movie/" + movieId;
    }

    @ResponseBody
    @GetMapping(value = "{subtitleId}", produces = "text/vtt")
    public ResponseEntity<ByteArrayResource> getProfileFile(@PathVariable("subtitleId") Long subtitleId) {
        ByteArrayResource file = new ByteArrayResource(subtitleDao.getById(subtitleId).getFile());
        return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION,
                "attachment; filename=\"subtitle.vtt\"").body(file);
    }


}
