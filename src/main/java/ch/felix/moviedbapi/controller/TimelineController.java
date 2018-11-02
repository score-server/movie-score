package ch.felix.moviedbapi.controller;

import ch.felix.moviedbapi.data.dto.ListMovieDto;
import ch.felix.moviedbapi.data.dto.MovieDto;
import ch.felix.moviedbapi.data.dto.TimeLineDto;
import ch.felix.moviedbapi.data.entity.ListMovie;
import ch.felix.moviedbapi.data.entity.Timeline;
import ch.felix.moviedbapi.data.entity.User;
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

    private MovieDto movieDto;
    private TimeLineDto timeLineDto;
    private ListMovieDto listMovieDto;

    private UserIndicatorService userIndicatorService;
    private ActivityService activityService;

    public TimelineController(MovieDto movieDto, TimeLineDto timeLineDto,
                              ListMovieDto listMovieDto, UserIndicatorService userIndicatorService,
                              ActivityService activityService) {
        this.movieDto = movieDto;
        this.timeLineDto = timeLineDto;
        this.listMovieDto = listMovieDto;
        this.userIndicatorService = userIndicatorService;
        this.activityService = activityService;
    }

    @GetMapping("edit/{timelineId}")
    public String editTimeline(@PathVariable("timelineId") String timeLineId,
                               Model model, HttpServletRequest request) {

        if (userIndicatorService.isUser(model, request)) {
            Timeline timeLine = timeLineDto.getById(Long.valueOf(timeLineId));
            if (isCurrentUser(request, timeLine) || isAdministrator(request)) {
                model.addAttribute("timeline", timeLine);
                model.addAttribute("movies", movieDto.getOrderByTitle());
                getNextPlace(model, timeLine);

                model.addAttribute("page", "editTimeline");
                return "template";
            }
        }
        return "redirect:/list";
    }

    @PostMapping("edit/{timelineId}")
    public String saveNewMovie(@PathVariable("timelineId") String timeLineId,
                               @RequestParam("place") String place,
                               @RequestParam("movie") String movieId,
                               HttpServletRequest request) {
        if (userIndicatorService.isUser(request)) {
            Timeline timeline = timeLineDto.getById(Long.valueOf(timeLineId));
            if (isCurrentUser(request, timeline) || isAdministrator(request)) {
                ListMovie listMovie = new ListMovie();
                listMovie.setPlace(Integer.valueOf(place));
                listMovie.setMovie(movieDto.getById(Long.valueOf(movieId)));
                listMovie.setTimeline(timeline);
                listMovieDto.save(listMovie);
                return "redirect:/timeline/edit/" + timeLineId;
            }
        }
        return "redirect:/" + timeLineId;
    }

    @PostMapping("editatt/{timelineId}")
    public String editListAttributes(@PathVariable("timelineId") String timeLineId,
                                     @RequestParam("title") String title,
                                     @RequestParam("description") String description,
                                     HttpServletRequest request) {
        if (userIndicatorService.isUser(request)) {
            Timeline timeline = timeLineDto.getById(Long.valueOf(timeLineId));
            if (isCurrentUser(request, timeline) || isAdministrator(request)) {
                timeline.setTitle(title);
                timeline.setDescription(description);
                timeLineDto.save(timeline);
                return "redirect:/timeline/edit/" + timeLineId;
            }
        }
        return "redirect:/" + timeLineId;
    }

    @PostMapping("delete/movie/{movieParId}")
    public String deleteFromList(@PathVariable("movieParId") String movieParId,
                                 HttpServletRequest request) {

        if (userIndicatorService.isUser(request)) {
            ListMovie listMovie = listMovieDto.getById(Long.valueOf(movieParId));
            if (isCurrentUser(request, listMovie.getTimeline()) || isAdministrator(request)) {
                listMovieDto.delete(listMovie);
                return "redirect:/timeline/edit/" + listMovie.getTimeline().getId();
            }
        }
        return "redirect:/list";
    }

    @GetMapping("new")
    public String getCreateForm(HttpServletRequest request, Model model) {
        if (userIndicatorService.isUser(model, request)) {
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
        if (userIndicatorService.isUser(request)) {
            User user = userIndicatorService.getUser(request).getUser();

            Timeline timeline = new Timeline();
            timeline.setTitle(title);
            timeline.setUser(user);
            timeline.setDescription(description);
            timeLineDto.save(timeline);

            activityService.log(user.getName() + " created list " + title, user);
            return "redirect:/list?list";
        } else {
            return "redirect:/list";
        }
    }

    @PostMapping("delete/{timelineId}")
    public String deleteTimeline(@PathVariable("timelineId") String timeLineId,
                                 Model model, HttpServletRequest request) {
        if (userIndicatorService.isUser(model, request)) {
            Timeline timeline = timeLineDto.getById(Long.valueOf(timeLineId));
            if (isCurrentUser(request, timeline) || isAdministrator(request)) {
                timeLineDto.delete(timeline);
                return "redirect:/list?deleted";
            }
        }
        return "redirect:/list/" + timeLineId + "?notdeleted";
    }

    private boolean isAdministrator(HttpServletRequest request) {
        return userIndicatorService.isAdministrator(request);
    }


    private boolean isCurrentUser(HttpServletRequest request, Timeline timeline) {
        return userIndicatorService.getUser(request).getUser() == timeline.getUser();
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
