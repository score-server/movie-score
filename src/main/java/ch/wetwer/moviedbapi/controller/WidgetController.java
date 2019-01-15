package ch.wetwer.moviedbapi.controller;

import ch.wetwer.moviedbapi.service.ComponentAvalibleService;
import ch.wetwer.moviedbapi.service.auth.UserAuthService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("status")
public class WidgetController {

    private UserAuthService userAuthService;
    private ComponentAvalibleService componentAvalibleService;

    public WidgetController(UserAuthService userAuthService, ComponentAvalibleService componentAvalibleService) {
        this.userAuthService = userAuthService;
        this.componentAvalibleService = componentAvalibleService;
    }

    @GetMapping
    public String getServerStatus(Model model) {
        model.addAttribute("minecraft", componentAvalibleService.checkOnline("scorewinner.ch", 25565));
        model.addAttribute("steam", componentAvalibleService.checkOnline("scorewinner.ch", 7777));
        model.addAttribute("pterodactyl", componentAvalibleService.checkOnline("games.scorewinner.ch", 80));
        model.addAttribute("moviedb", componentAvalibleService.checkOnline("movie.scorewinner.ch", 80));
        model.addAttribute("hermann", componentAvalibleService.checkOnline("scorewinner.ch", 8090));
        return "widget/status.html";
    }

    @GetMapping("user")
    public String getCurrentUser(Model model, HttpServletRequest request) {
        userAuthService.allowGuest(model, request);
        return "widget/user.html";
    }
}
