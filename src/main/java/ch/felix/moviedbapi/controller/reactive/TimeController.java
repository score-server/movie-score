package ch.felix.moviedbapi.controller.reactive;

import ch.felix.moviedbapi.data.entity.Episode;
import ch.felix.moviedbapi.data.entity.Movie;
import ch.felix.moviedbapi.data.entity.Time;
import ch.felix.moviedbapi.data.entity.User;
import ch.felix.moviedbapi.data.repository.EpisodeRepository;
import ch.felix.moviedbapi.data.repository.MovieRepository;
import ch.felix.moviedbapi.data.repository.TimeRepository;
import ch.felix.moviedbapi.data.repository.UserRepository;
import ch.felix.moviedbapi.service.UserIndicatorService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("time")
public class TimeController {

    private UserRepository userRepository;
    private MovieRepository movieRepository;
    private TimeRepository timeRepository;
    private EpisodeRepository episodeRepository;

    private UserIndicatorService userIndicatorService;

    public TimeController(UserRepository userRepository, MovieRepository movieRepository,
                          TimeRepository timeRepository, EpisodeRepository episodeRepository,
                          UserIndicatorService userIndicatorService) {
        this.userRepository = userRepository;
        this.movieRepository = movieRepository;
        this.timeRepository = timeRepository;
        this.episodeRepository = episodeRepository;
        this.userIndicatorService = userIndicatorService;
    }

    @PostMapping("movie")
    public String setTime(@RequestParam("userId") Long userId, @RequestParam("movieId") Long movieId,
                          @RequestParam("time") Float timeParam, HttpServletRequest request) {
        System.out.println(timeParam + " u:" + userId + " m:" + movieId);
        if (userIndicatorService.isUser(request)) {
            Movie movie = movieRepository.findMovieById(movieId);
            User user = userRepository.findUserById(userId);

            Time time = timeRepository.findTimeByUserAndMovie(user, movie);
            try {
                time.getId();
                time.setTime(timeParam);
                timeRepository.save(time);
            } catch (NullPointerException e) {
                time = new Time();
                time.setTime(timeParam);
                time.setMovie(movie);
                time.setUser(user);
                timeRepository.save(time);
            }
        }
        return "null";
    }

    @PostMapping("episode")
    public String setTimeforEpisode(@RequestParam("userId") Long userId, @RequestParam("episodeId") Long episodeId,
                                    @RequestParam("time") Float timeParam, HttpServletRequest request) {
        if (userIndicatorService.isUser(request)) {
            Episode episode = episodeRepository.findEpisodeById(episodeId);
            User user = userRepository.findUserById(userId);

            Time time = timeRepository.findTimeByUserAndEpisode(user, episode);
            try {
                time.getId();
                time.setTime(timeParam);
                timeRepository.save(time);
            } catch (NullPointerException e) {
                time = new Time();
                time.setTime(timeParam);
                time.setEpisode(episode);
                time.setUser(user);
                timeRepository.save(time);
            }
        }
        return "null";
    }

}
