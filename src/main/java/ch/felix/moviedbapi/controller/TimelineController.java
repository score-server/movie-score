package ch.felix.moviedbapi.controller;

import ch.felix.moviedbapi.data.entity.ListMovie;
import ch.felix.moviedbapi.data.entity.Timeline;
import ch.felix.moviedbapi.data.entity.User;
import ch.felix.moviedbapi.data.repository.ListMovieRepository;
import ch.felix.moviedbapi.data.repository.MovieRepository;
import ch.felix.moviedbapi.data.repository.TimelineRepository;
import ch.felix.moviedbapi.service.CookieService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Wetwer
 * @project movie-db
 */
@Controller
@RequestMapping("list")
public class TimelineController {

    private TimelineRepository timelineRepository;
    private MovieRepository movieRepository;
    private ListMovieRepository listMovieRepository;

    private CookieService cookieService;

    public TimelineController(TimelineRepository timelineRepository, MovieRepository movieRepository,
                              ListMovieRepository listMovieRepository, CookieService cookieService) {
        this.timelineRepository = timelineRepository;
        this.movieRepository = movieRepository;
        this.listMovieRepository = listMovieRepository;
        this.cookieService = cookieService;
    }

    @GetMapping
    public String getTimelines(@RequestParam(name = "search", required = false, defaultValue = "") String search,
                               Model model, HttpServletRequest request) {
        try {
            model.addAttribute("currentUser", cookieService.getCurrentUser(request));
        } catch (NullPointerException e) {
        }

        model.addAttribute("timelines", timelineRepository.findTimelinesByTitleContaining(search));

        model.addAttribute("search", search);

        model.addAttribute("page", "timelineList");
        return "template";
    }

    @GetMapping("{timelineId}")
    public String getOneTimeLine(@PathVariable("timelineId") String timeLineId,
                                 Model model, HttpServletRequest request) {
        try {
            model.addAttribute("currentUser", cookieService.getCurrentUser(request));
        } catch (NullPointerException e) {
        }

        model.addAttribute("timeline", timelineRepository.findTimelineById(Long.valueOf(timeLineId)));

        model.addAttribute("page", "timeline");
        return "template";
    }

    @GetMapping("{timelineId}/edit")
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
            model.addAttribute("timeline", timelineRepository.findTimelineById(Long.valueOf(timeLineId)));
            model.addAttribute("movies", movieRepository.findMoviesByTitleContainingOrderByTitle(""));
            model.addAttribute("page", "editTimeline");
            return "template";
        }
        return "redirect:/list/" + timeLineId;
    }

    @PostMapping("{timelineId}/edit")
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
        return "redirect:/list/" + timeLineId + "/edit";
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
            return "redirect:/list/" + listMovie.getTimeline().getId() + "/edit";
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
                             HttpServletRequest request) {
        try {
            if (cookieService.getCurrentUser(request).getRole() != 2) {
                return "redirect:/list";
            }
        } catch (NullPointerException e) {
            return "redirect:/login?redirect";
        }

        Timeline timeline = new Timeline();
        timeline.setTitle(title);
        timeline.setUser(cookieService.getCurrentUser(request));
        timeline.setDescription(description);
        timelineRepository.save(timeline);

        return "redirect:/list";
    }

}
