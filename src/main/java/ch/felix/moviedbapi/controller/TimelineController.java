package ch.felix.moviedbapi.controller;

import ch.felix.moviedbapi.data.entity.ListMovie;
import ch.felix.moviedbapi.data.entity.Timeline;
import ch.felix.moviedbapi.data.entity.User;
import ch.felix.moviedbapi.data.repository.ListMovieRepository;
import ch.felix.moviedbapi.data.repository.MovieRepository;
import ch.felix.moviedbapi.data.repository.TimelineRepository;
import ch.felix.moviedbapi.service.CookieService;
import ch.felix.moviedbapi.service.UserIndicatorService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("timeline")
public class TimelineController {

    private TimelineRepository timelineRepository;
    private MovieRepository movieRepository;
    private ListMovieRepository listMovieRepository;

    private CookieService cookieService;
    private UserIndicatorService userIndicatorService;

    public TimelineController(TimelineRepository timelineRepository, MovieRepository movieRepository,
                              ListMovieRepository listMovieRepository, CookieService cookieService, UserIndicatorService userIndicatorService) {
        this.timelineRepository = timelineRepository;
        this.movieRepository = movieRepository;
        this.listMovieRepository = listMovieRepository;
        this.cookieService = cookieService;
        this.userIndicatorService = userIndicatorService;
    }

    @GetMapping("edit/{timelineId}")
    public String editTimeline(@PathVariable("timelineId") String timeLineId,
                               Model model, HttpServletRequest request) {
        try {
            if (cookieService.getCurrentUser(request).getRole() != 2) {
                return "redirect:/list";
            }
            model.addAttribute("currentUser", cookieService.getCurrentUser(request));
        } catch (NullPointerException e) {
            return "redirect:/login?redirect=/list/" + timeLineId + "/edit";
        }
        Timeline timeline = timelineRepository.findTimelineById(Long.valueOf(timeLineId));
        User currentUser = cookieService.getCurrentUser(request);


        if (currentUser == timeline.getUser() || currentUser.getRole() == 2) {
            Timeline timeLine = timelineRepository.findTimelineById(Long.valueOf(timeLineId));
            model.addAttribute("timeline", timeLine);
            model.addAttribute("movies", movieRepository.findMoviesByTitleContainingOrderByTitle(""));
            try {
                model.addAttribute("nextPlace",
                        timeLine.getListMovies().get(timeLine.getListMovies().size() - 1).getPlace() + 1);
            } catch (NullPointerException | IndexOutOfBoundsException e) {
                model.addAttribute("nextPlace", 1);
            }

            model.addAttribute("page", "editTimeline");
            return "template";
        }
        return "redirect:/list/" + timeLineId;
    }

    @PostMapping("edit/{timelineId}")
    public String saveNewMovie(@PathVariable("timelineId") String timeLineId,
                               @RequestParam("place") String place,
                               @RequestParam("movie") String movieId,
                               HttpServletRequest request) {
        try {
            if (cookieService.getCurrentUser(request).getRole() != 2) {
                return "redirect:/list";
            }
        } catch (NullPointerException e) {
            return "redirect:/login?redirect";
        }
        Timeline timeline = timelineRepository.findTimelineById(Long.valueOf(timeLineId));
        User currentUser = cookieService.getCurrentUser(request);
        if (currentUser == timeline.getUser() || currentUser.getRole() == 2) {
            ListMovie listMovie = new ListMovie();
            listMovie.setPlace(Integer.valueOf(place));
            listMovie.setMovie(movieRepository.findMovieById(Long.valueOf(movieId)));
            listMovie.setTimeline(timelineRepository.findTimelineById(Long.valueOf(timeLineId)));
            listMovieRepository.save(listMovie);
        }
        return "redirect:/timeline/edit/" + timeLineId;
    }

    @PostMapping("delete/movie/{movieParId}")
    public String deleteFromList(@PathVariable("movieParId") String movieParId,
                                 HttpServletRequest request) {
        try {
            if (cookieService.getCurrentUser(request).getRole() != 2) {
                return "redirect:/list";
            }
        } catch (NullPointerException e) {
            return "redirect:/login?redirect";
        }
        Timeline timeline = listMovieRepository.findListMovieById(Long.valueOf(movieParId)).getTimeline();
        User currentUser = cookieService.getCurrentUser(request);
        if (currentUser == timeline.getUser() || currentUser.getRole() == 2) {
            ListMovie listMovie = listMovieRepository.findListMovieById(Long.valueOf(movieParId));
            listMovieRepository.delete(listMovie);
            return "redirect:/timeline/edit/" + listMovie.getTimeline().getId();
        }
        return "redirect:/list";
    }

    @GetMapping("new")
    public String getCreateForm(HttpServletRequest request, Model model) {
        try {
            if (cookieService.getCurrentUser(request).getRole() != 2) {
                return "redirect:/list";
            }
        } catch (NullPointerException e) {
            return "redirect:/login?redirect";
        }

        model.addAttribute("page", "createTimeline");
        return "template";
    }


    @PostMapping("new")
    public String createList(@RequestParam("title") String title,
                             @RequestParam("description") String description,
                             Model model, HttpServletRequest request) {
        if (userIndicatorService.disallowUserAccess(model, request)) {
            return "redirect:/list";
        }

        Timeline timeline = new Timeline();
        timeline.setTitle(title);
        timeline.setUser(cookieService.getCurrentUser(request));
        timeline.setDescription(description);
        timelineRepository.save(timeline);

        return "redirect:/list";
    }

    @PostMapping("delete/{timelineId}")
    public String deleteTimeline(@PathVariable("timelineId") String timeLineId,
                                 Model model, HttpServletRequest request) {
        if (userIndicatorService.disallowUserAccess(model, request)) {
            return "redirect:/list/" + timeLineId + "?notdeleted";
        }
        timelineRepository.delete(timelineRepository.findTimelineById(Long.valueOf(timeLineId)));
        return "redirect:/list?deleted";
    }

}
