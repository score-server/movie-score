package ch.felix.moviedbapi.controller;

import ch.felix.moviedbapi.data.repository.TimelineRepository;
import ch.felix.moviedbapi.service.UserIndicatorService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Wetwer
 * @project movie-db
 */
@Controller
@RequestMapping("list")
public class ListController {

    private TimelineRepository timelineRepository;

    private UserIndicatorService userIndicatorService;

    public ListController(TimelineRepository timelineRepository, UserIndicatorService userIndicatorService) {
        this.timelineRepository = timelineRepository;
        this.userIndicatorService = userIndicatorService;
    }

    @GetMapping
    public String getLists(@RequestParam(name = "search", required = false, defaultValue = "") String search,
                           Model model, HttpServletRequest request) {
        if (userIndicatorService.isUser(model, request)) {
            model.addAttribute("timelines", timelineRepository.findTimelinesByTitleContaining(search));
            model.addAttribute("search", search);
            model.addAttribute("page", "timelineList");
            return "template";
        } else {
            return "redirect:login/?redirect=/list";
        }

    }

    @GetMapping("{timelineId}")
    public String getOneTimeLine(@PathVariable("timelineId") String timeLineId,
                                 Model model, HttpServletRequest request) {
        if (userIndicatorService.isUser(model, request)) {
            model.addAttribute("timeline", timelineRepository.findTimelineById(Long.valueOf(timeLineId)));
            model.addAttribute("page", "timeline");
            return "template";
        } else {
            return "redirect:/login?redirect:/list/" + timeLineId;
        }

    }
}
