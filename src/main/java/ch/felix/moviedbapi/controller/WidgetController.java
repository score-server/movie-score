package ch.felix.moviedbapi.controller;

import ch.felix.moviedbapi.service.AvalibleService;
import ch.felix.moviedbapi.service.auth.UserAuthService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("status")
public class WidgetController {

    private UserAuthService userAuthService;
    private AvalibleService avalibleService;

    public WidgetController(UserAuthService userAuthService, AvalibleService avalibleService) {
        this.userAuthService = userAuthService;
        this.avalibleService = avalibleService;
    }

    @GetMapping
    public String getServerStatus(Model model) {
        model.addAttribute("minecraft", avalibleService.checkOnline("scorewinner.ch", 25565));
        model.addAttribute("steam", avalibleService.checkOnline("scorewinner.ch", 7777));
        model.addAttribute("pterodactyl", avalibleService.checkOnline("games.scorewinner.ch", 80));
        model.addAttribute("moviedb", avalibleService.checkOnline("movie.scorewinner.ch", 80));
        model.addAttribute("hermann", avalibleService.checkOnline("scorewinner.ch", 8090));
        return "widget/status.html";
    }

    @GetMapping("user")
    public String getCurrentUser(Model model, HttpServletRequest request) {
        userAuthService.allowGuest(model, request);
        return "widget/user.html";
    }
}
