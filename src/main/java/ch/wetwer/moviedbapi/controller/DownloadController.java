package ch.wetwer.moviedbapi.controller;

import ch.wetwer.moviedbapi.service.auth.UserAuthService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

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
            model.addAttribute("page", "download");
            return "template";
        }
        return "redirect:/";
    }

}
