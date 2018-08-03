package ch.felix.moviedbapi.controller;

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
public class ListController {

    private TimelineRepository timelineRepository;

    private CookieService cookieService;

    public ListController(TimelineRepository timelineRepository, CookieService cookieService) {
        this.timelineRepository = timelineRepository;
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
}
