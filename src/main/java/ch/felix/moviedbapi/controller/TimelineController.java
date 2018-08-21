package ch.felix.moviedbapi.controller;

import ch.felix.moviedbapi.data.entity.ListMovie;
import ch.felix.moviedbapi.data.entity.Timeline;
import ch.felix.moviedbapi.data.entity.User;
import ch.felix.moviedbapi.data.repository.ListMovieRepository;
import ch.felix.moviedbapi.data.repository.MovieRepository;
import ch.felix.moviedbapi.data.repository.TimelineRepository;
import ch.felix.moviedbapi.service.ActivityService;
import ch.felix.moviedbapi.service.UserIndicatorService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("timeline")
public class TimelineController {

    private TimelineRepository timelineRepository;
    private MovieRepository movieRepository;
    private ListMovieRepository listMovieRepository;

    private UserIndicatorService userIndicatorService;
    private ActivityService activityService;

    public TimelineController(TimelineRepository timelineRepository, MovieRepository movieRepository,
                              ListMovieRepository listMovieRepository, UserIndicatorService userIndicatorService, ActivityService activityService) {
        this.timelineRepository = timelineRepository;
        this.movieRepository = movieRepository;
        this.listMovieRepository = listMovieRepository;
        this.userIndicatorService = userIndicatorService;
        this.activityService = activityService;
    }

    @GetMapping("edit/{timelineId}")
    public String editTimeline(@PathVariable("timelineId") String timeLineId,
                               Model model, HttpServletRequest request) {

        if (userIndicatorService.isAdministrator(model, request)) {
            Timeline timeLine = timelineRepository.findTimelineById(Long.valueOf(timeLineId));
            model.addAttribute("timeline", timeLine);
            model.addAttribute("movies", movieRepository.findMoviesByTitleContainingOrderByTitle(""));
            getNextPlace(model, timeLine);

            model.addAttribute("page", "editTimeline");
            return "template";
        } else {
            return "redirect:/list";
        }
    }

    @PostMapping("edit/{timelineId}")
    public String saveNewMovie(@PathVariable("timelineId") String timeLineId,
                               @RequestParam("place") String place,
                               @RequestParam("movie") String movieId,
                               HttpServletRequest request) {
        if (userIndicatorService.isAdministrator(request)) {
            ListMovie listMovie = new ListMovie();
            listMovie.setPlace(Integer.valueOf(place));
            listMovie.setMovie(movieRepository.findMovieById(Long.valueOf(movieId)));
            listMovie.setTimeline(timelineRepository.findTimelineById(Long.valueOf(timeLineId)));
            listMovieRepository.save(listMovie);
            return "redirect:/timeline/edit/" + timeLineId;
        } else {
            return "redirect:/login?redirect=/timeline/edit/" + timeLineId;
        }
    }

    @PostMapping("delete/movie/{movieParId}")
    public String deleteFromList(@PathVariable("movieParId") String movieParId,
                                 HttpServletRequest request) {

        if (userIndicatorService.isAdministrator(request)) {
            ListMovie listMovie = listMovieRepository.findListMovieById(Long.valueOf(movieParId));
            listMovieRepository.delete(listMovie);
            return "redirect:/timeline/edit/" + listMovie.getTimeline().getId();
        } else {
            return "redirect:/list";
        }


    }

    @GetMapping("new")
    public String getCreateForm(HttpServletRequest request, Model model) {
        if (userIndicatorService.isAdministrator(model, request)) {
            model.addAttribute("page", "createTimeline");
            return "template";
        } else {
            return "redirect:/list";
        }
    }


    @PostMapping("new")
    public String createList(@RequestParam("title") String title,
                             @RequestParam("description") String description,
                             HttpServletRequest request) {
        if (userIndicatorService.isAdministrator(request)) {
            User user = userIndicatorService.getUser(request).getUser();

            Timeline timeline = new Timeline();
            timeline.setTitle(title);
            timeline.setUser(user);
            timeline.setDescription(description);
            timelineRepository.save(timeline);

            activityService.log(user.getName() + " created list " + title);
            return "redirect:/list?list";
        } else {
            return "redirect:/list";
        }
    }

    @PostMapping("delete/{timelineId}")
    public String deleteTimeline(@PathVariable("timelineId") String timeLineId,
                                 Model model, HttpServletRequest request) {
        if (userIndicatorService.isAdministrator(model, request)) {
            timelineRepository.delete(timelineRepository.findTimelineById(Long.valueOf(timeLineId)));
            return "redirect:/list?deleted";
        } else {
            return "redirect:/list/" + timeLineId + "?notdeleted";
        }
    }


    private void getNextPlace(Model model, Timeline timeLine) {
        try {
            model.addAttribute("nextPlace",
                    timeLine.getListMovies().get(timeLine.getListMovies().size() - 1).getPlace() + 1);
        } catch (NullPointerException | IndexOutOfBoundsException e) {
            model.addAttribute("nextPlace", 1);
        }
    }
}
