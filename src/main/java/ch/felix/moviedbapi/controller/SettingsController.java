package ch.felix.moviedbapi.controller;


import ch.felix.moviedbapi.data.entity.User;
import ch.felix.moviedbapi.service.CookieService;
import ch.felix.moviedbapi.service.SettingsService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("settings")
public class SettingsController {

    private SettingsService settingsService;
    private CookieService cookieService;

    public SettingsController(SettingsService settingsService, CookieService cookieService) {
        this.settingsService = settingsService;
        this.cookieService = cookieService;
    }

    @GetMapping
    private String getSettings(Model model, HttpServletRequest request) {
        try {
            User user = cookieService.getCurrentUser(request);
            model.addAttribute("currentUser", user);
            if (user.getRole() != 2) {
                return "redirect:/";
            }
        } catch (NullPointerException e) {
            return "redirect:/";
        }

        model.addAttribute("moviePath", settingsService.getKey("moviePath"));
        model.addAttribute("seriePath", settingsService.getKey("seriePath"));
        model.addAttribute("running", settingsService.getKey("import").equals("1"));

        model.addAttribute("page", "settings");
        return "template";
    }

}
