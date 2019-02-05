package ch.wetwer.moviedbapi.controller.reactive;

import ch.wetwer.moviedbapi.data.episode.Episode;
import ch.wetwer.moviedbapi.data.episode.EpisodeDao;
import ch.wetwer.moviedbapi.data.movie.Movie;
import ch.wetwer.moviedbapi.data.movie.MovieDao;
import ch.wetwer.moviedbapi.data.time.Time;
import ch.wetwer.moviedbapi.data.time.TimeDao;
import ch.wetwer.moviedbapi.data.user.User;
import ch.wetwer.moviedbapi.data.user.UserDao;
import ch.wetwer.moviedbapi.service.auth.UserAuthService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import java.sql.Timestamp;
import java.util.Date;

@Controller
@RequestMapping("time")
public class TimeController {

    private UserDao userDao;
    private MovieDao movieDao;
    private TimeDao timeDao;
    private EpisodeDao episodeDao;

    private UserAuthService userAuthService;

    public TimeController(UserDao userDao, MovieDao movieDao,
                          TimeDao timeDao, EpisodeDao episodeDao,
                          UserAuthService userAuthService) {
        this.userDao = userDao;
        this.movieDao = movieDao;
        this.timeDao = timeDao;
        this.episodeDao = episodeDao;
        this.userAuthService = userAuthService;
    }

    @PostMapping("movie")
    public String setTime(@RequestParam("userId") Long userId, @RequestParam("movieId") Long movieId,
                          @RequestParam("time") Float timeParam, HttpServletRequest request) {
        if (userAuthService.isUser(request)) {
            Movie movie = movieDao.getById(movieId);
            User user = userDao.getById(userId);

            Time time = timeDao.getByUserAndMovie(user, movie);
            try {
                time.getId();
                time.setTime(timeParam);
                time.setTimestamp(getTimestamp());
                timeDao.save(time);
            } catch (NullPointerException e) {
                time = new Time();
                time.setTime(timeParam);
                time.setMovie(movie);
                time.setUser(user);
                time.setTimestamp(getTimestamp());
                timeDao.save(time);
            }
        }
        return "null";
    }

    @PostMapping("episode")
    public String setTimeforEpisode(@RequestParam("userId") Long userId, @RequestParam("episodeId") Long episodeId,
                                    @RequestParam("time") Float timeParam, HttpServletRequest request) {
        if (userAuthService.isUser(request)) {
            Episode episode = episodeDao.getById(episodeId);
            User user = userDao.getById(userId);

            Time time = timeDao.getByUserAndEpisode(user, episode);
            try {
                time.getId();
                time.setTime(timeParam);
                time.setTimestamp(getTimestamp());
                timeDao.save(time);
            } catch (NullPointerException e) {
                time = new Time();
                time.setTime(timeParam);
                time.setEpisode(episode);
                time.setUser(user);
                time.setTimestamp(getTimestamp());
                timeDao.save(time);
            }
        }
        return "null";
    }

    @PostMapping("delete/{timeId}")
    public String deleteTime(@PathVariable("timeId") Long timeId, HttpServletRequest request) {
        Time time = timeDao.getById(timeId);
        if (time.getUser() == userAuthService.getUser(request).getUser()) {
            timeDao.delete(time);
            return "redirect:/";
        }
        return "redirect:/?error";
    }

    private Timestamp getTimestamp() {
        return new Timestamp(new Date().getTime());
    }

}
