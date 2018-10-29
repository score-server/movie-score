package ch.felix.moviedbapi.controller.reactive;

import ch.felix.moviedbapi.data.dto.MovieDto;
import ch.felix.moviedbapi.data.dto.TimeDto;
import ch.felix.moviedbapi.data.dto.UserDto;
import ch.felix.moviedbapi.data.entity.Episode;
import ch.felix.moviedbapi.data.entity.Movie;
import ch.felix.moviedbapi.data.entity.Time;
import ch.felix.moviedbapi.data.entity.User;
import ch.felix.moviedbapi.data.repository.EpisodeRepository;
import ch.felix.moviedbapi.service.UserIndicatorService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import java.sql.Timestamp;
import java.util.Date;

@Controller
@RequestMapping("time")
public class TimeController {

    private UserDto userDto;
    private MovieDto movieDto;
    private TimeDto timeDto;
    private EpisodeRepository episodeRepository;

    private UserIndicatorService userIndicatorService;

    public TimeController(UserDto userDto, MovieDto movieDto,
                          TimeDto timeDto, EpisodeRepository episodeRepository,
                          UserIndicatorService userIndicatorService) {
        this.userDto = userDto;
        this.movieDto = movieDto;
        this.timeDto = timeDto;
        this.episodeRepository = episodeRepository;
        this.userIndicatorService = userIndicatorService;
    }

    @PostMapping("movie")
    public String setTime(@RequestParam("userId") Long userId, @RequestParam("movieId") Long movieId,
                          @RequestParam("time") Float timeParam, HttpServletRequest request) {
        if (userIndicatorService.isUser(request)) {
            Movie movie = movieDto.getById(movieId);
            User user = userDto.getById(userId);

            Time time = timeDto.getByUserAndMovie(user, movie);
            try {
                time.getId();
                time.setTime(timeParam);
                time.setTimestamp(getTimestamp());
                timeDto.save(time);
            } catch (NullPointerException e) {
                time = new Time();
                time.setTime(timeParam);
                time.setMovie(movie);
                time.setUser(user);
                time.setTimestamp(getTimestamp());
                timeDto.save(time);
            }
        }
        return "null";
    }

    @PostMapping("episode")
    public String setTimeforEpisode(@RequestParam("userId") Long userId, @RequestParam("episodeId") Long episodeId,
                                    @RequestParam("time") Float timeParam, HttpServletRequest request) {
        if (userIndicatorService.isUser(request)) {
            Episode episode = episodeRepository.findEpisodeById(episodeId);
            User user = userDto.getById(userId);

            Time time = timeDto.getByUserAndEpisode(user, episode);
            try {
                time.getId();
                time.setTime(timeParam);
                time.setTimestamp(getTimestamp());
                timeDto.save(time);
            } catch (NullPointerException e) {
                time = new Time();
                time.setTime(timeParam);
                time.setEpisode(episode);
                time.setUser(user);
                time.setTimestamp(getTimestamp());
                timeDto.save(time);
            }
        }
        return "null";
    }

    private Timestamp getTimestamp() {
        return new Timestamp(new Date().getTime());
    }

}
