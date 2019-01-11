package ch.wetwer.moviedbapi.controller;

import ch.wetwer.moviedbapi.service.auth.UserAuthService;
import ch.wetwer.moviedbapi.service.filehandler.PropertiesHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Wetwer
 * @project movie-db-api
 * @package ch.wetwer.moviedbapi.controller
 * @created 04.01.2019
 **/

@Controller
@RequestMapping("/download")
public class DownloadController {

    private UserAuthService userAuthService;

    public DownloadController(UserAuthService userAuthService) {
        this.userAuthService = userAuthService;
    }

    @GetMapping
    public String getDowloads(Model model, HttpServletRequest request) {
        if (userAuthService.isUser(model, request)) {
            model.addAttribute("windows", new PropertiesHandler("settings.properties").getProperty("windows"));
            model.addAttribute("mac", new PropertiesHandler("settings.properties").getProperty("mac"));
            model.addAttribute("page", "download");
            return "template";
        }
        return "redirect:/?access";
    }

    @PostMapping("windows")
    public String setWindowsPath(@RequestParam("path") String path, HttpServletRequest request) {
        if (userAuthService.isAdministrator(request)) {
            PropertiesHandler propertiesHandler = new PropertiesHandler("settings.properties");
            propertiesHandler.setValue("windows", path);
            return "redirect:/download?changed=Windows";
        }
        return "redirect:/?access";
    }

    @PostMapping("mac")
    public String setMacPath(@RequestParam("path") String path, HttpServletRequest request) {
        if (userAuthService.isAdministrator(request)) {
            PropertiesHandler propertiesHandler = new PropertiesHandler("settings.properties");
            propertiesHandler.setValue("mac", path);
            return "redirect:/download?changed=Mac";
        }
        return "redirect:/?access";
    }

}
