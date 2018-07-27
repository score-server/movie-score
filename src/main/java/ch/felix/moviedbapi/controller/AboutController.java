package ch.felix.moviedbapi.controller;

import ch.felix.moviedbapi.service.CookieService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("about")
public class AboutController {

    private CookieService cookieService;

    public AboutController(CookieService cookieService) {
        this.cookieService = cookieService;
    }

    @GetMapping
    public String getAboutPage(Model model, HttpServletRequest request) {
        try {
            model.addAttribute("currentUser", cookieService.getCurrentUser(request));
        } catch (NullPointerException e) {
        }

        model.addAttribute("page", "about");
        return "template";
    }


}
