package ch.felix.moviedbapi.controller;


import ch.felix.moviedbapi.service.SettingsService;
import ch.felix.moviedbapi.service.UserIndicatorService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Wetwer
 * @project movie-db
 */
@Controller
@RequestMapping("settings")
public class SettingsController {

    private SettingsService settingsService;
    private UserIndicatorService userIndicatorService;

    public SettingsController(SettingsService settingsService, UserIndicatorService userIndicatorService) {
        this.settingsService = settingsService;
        this.userIndicatorService = userIndicatorService;
    }

    @GetMapping
    private String getSettings(Model model, HttpServletRequest request) {
        if (userIndicatorService.isAdministrator(model, request)) {

            model.addAttribute("moviePath", settingsService.getKey("moviePath"));
            model.addAttribute("seriePath", settingsService.getKey("seriePath"));
            model.addAttribute("importProgress", settingsService.getKey("importProgress"));
            try {
                model.addAttribute("running", settingsService.getKey("import").equals("1"));
            } catch (NullPointerException e) {
                settingsService.setValue("import", "0");
                model.addAttribute("running", settingsService.getKey("import").equals("1"));
            }

            model.addAttribute("page", "settings");
            return "template";
        } else {
            return "redirect:/";
        }
    }

}
