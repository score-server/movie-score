package ch.felix.moviedbapi.controller;

import ch.felix.moviedbapi.data.dao.MovieDao;
import ch.felix.moviedbapi.data.dao.SubtitleDao;
import ch.felix.moviedbapi.data.entity.Subtitle;
import ch.felix.moviedbapi.service.auth.UserAuthService;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * @author Wetwer
 * @project movie-db-api
 * @package ch.felix.moviedbapi.controller
 * @created 26.11.2018
 **/

@Controller
@RequestMapping("subtitle")
public class SubtitleController {

    private MovieDao movieDao;
    private SubtitleDao subtitleDao;

    private UserAuthService userAuthService;

    public SubtitleController(MovieDao movieDao, SubtitleDao subtitleDao, UserAuthService userAuthService) {
        this.movieDao = movieDao;
        this.subtitleDao = subtitleDao;
        this.userAuthService = userAuthService;
    }

    @GetMapping("add")
    public String getAddSubtitlesForm(Model model, HttpServletRequest request) {
        if (userAuthService.isUser(model, request)) {
            model.addAttribute("movies", movieDao.getAll());
            model.addAttribute("page", "subtitle");
            return "template";
        }
        return "redirect:/movie";
    }

    @PostMapping("add")
    public String addSubtitles(@RequestParam("movieId") Long movieId,
                               @RequestParam("file") MultipartFile multipartFile,
                               @RequestParam("language") String language,
                               HttpServletRequest request) {
        if (userAuthService.isUser(request)) {
            try {
                if (multipartFile.getOriginalFilename().endsWith(".srt")) {
                    Subtitle subtitle = new Subtitle();
                    subtitle.setMovie(movieDao.getById(movieId));
                    subtitle.setUser(userAuthService.getUser(request).getUser());
                    subtitle.setFile(multipartFile.getBytes());
                    subtitle.setLanguage(language);
                    subtitleDao.save(subtitle);
                    return "redirect:/movie/" + movieId + "?subtitle";
                } else {
                    return "redirect:/movie/" + movieId + "?wrongFile";
                }
            } catch (IOException e) {
                e.printStackTrace();
                return "redirect:/movie/" + movieId;
            }
        } else {
            return "redirect:/movie/" + movieId;
        }
    }

    @ResponseBody
    @GetMapping("{subtitleId}")
    public ResponseEntity<ByteArrayResource> getProfileFile(@PathVariable("subtitleId") Long subtitleId) {
        ByteArrayResource file = new ByteArrayResource(subtitleDao.getById(subtitleId).getFile());
        return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION,
                "attachment; filename=\"" + file.getFilename() + "\"").body(file);
    }


}
