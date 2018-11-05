package ch.felix.moviedbapi.controller;

import ch.felix.moviedbapi.data.dto.TimeLineDto;
import ch.felix.moviedbapi.service.UserAuthService;
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

    private TimeLineDto timeLineDto;

    private UserAuthService userAuthService;

    public ListController(TimeLineDto timeLineDto, UserAuthService userAuthService) {
        this.timeLineDto = timeLineDto;
        this.userAuthService = userAuthService;
    }

    @GetMapping
    public String getLists(@RequestParam(name = "search", required = false, defaultValue = "") String search,
                           Model model, HttpServletRequest request) {
        if (userAuthService.isUser(model, request)) {
            model.addAttribute("timelines", timeLineDto.searchTimeLine(search));
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
        if (userAuthService.isUser(model, request)) {
            model.addAttribute("timeline", timeLineDto.getById(Long.valueOf(timeLineId)));
            model.addAttribute("page", "timeline");
            return "template";
        } else {
            return "redirect:/login?redirect:/list/" + timeLineId;
        }

    }
}
