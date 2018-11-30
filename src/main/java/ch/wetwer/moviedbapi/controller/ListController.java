package ch.wetwer.moviedbapi.controller;

import ch.wetwer.moviedbapi.data.dao.TimeLineDao;
import ch.wetwer.moviedbapi.service.auth.UserAuthService;
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

    private TimeLineDao timeLineDto;

    private UserAuthService userAuthService;

    public ListController(TimeLineDao timeLineDto, UserAuthService userAuthService) {
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
    public String getOneTimeLine(@PathVariable("timelineId") Long timeLineId,
                                 Model model, HttpServletRequest request) {
        if (userAuthService.isUser(model, request)) {
            model.addAttribute("timeline", timeLineDto.getById(timeLineId));
            model.addAttribute("page", "timeline");
            return "template";
        } else {
            return "redirect:/login?redirect:/list/" + timeLineId;
        }

    }
}
