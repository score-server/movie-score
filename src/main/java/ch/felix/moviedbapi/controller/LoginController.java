package ch.felix.moviedbapi.controller;

import ch.felix.moviedbapi.data.entity.User;
import ch.felix.moviedbapi.data.repository.UserRepository;
import ch.felix.moviedbapi.service.ActivityService;
import ch.felix.moviedbapi.service.CookieService;
import ch.felix.moviedbapi.service.ShaService;
import ch.felix.moviedbapi.service.UserIndicatorService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Random;

/**
 * @author Wetwer
 * @project movie-db
 */

@Slf4j
@Controller
@RequestMapping("login")
public class LoginController {

    private UserRepository userRepository;

    private CookieService cookieService;
    private ShaService shaService;
    private UserIndicatorService userIndicatorService;
    private ActivityService activityService;

    public LoginController(UserRepository userRepository, CookieService cookieService, ShaService shaService, UserIndicatorService userIndicatorService, ActivityService activityService) {
        this.userRepository = userRepository;
        this.cookieService = cookieService;
        this.shaService = shaService;
        this.userIndicatorService = userIndicatorService;
        this.activityService = activityService;
    }

    @GetMapping
    public String getLogin(Model model, HttpServletRequest request) {
        userIndicatorService.allowGuest(model, request);

        model.addAttribute("page", "login");
        return "template";
    }

    @PostMapping
    public String login(@RequestParam(name = "redirect", required = false, defaultValue = "") String redirectParam,
                        @RequestParam("name") String nameParam,
                        @RequestParam("password") String passwordParam,
                        HttpServletResponse response) {
        User user = userRepository.findUserByNameAndPasswordSha(nameParam, shaService.encode(passwordParam));
        try {
            String sessionId = shaService.encode(String.valueOf(new Random().nextInt()));
            cookieService.setUserCookie(response, sessionId);
            user.setSessionId(sessionId);
            userRepository.save(user);
            activityService.log(user.getName() + " logged in", user);
            switch (redirectParam) {
                case "null":
                    return "redirect:/";
                case "score":
                    return "redirect:http://scorewinner.ch";
                default:
                    return "redirect:" + redirectParam;
            }
        } catch (NullPointerException e) {
            e.printStackTrace();
            return "redirect:/login?error&user=" + nameParam;
        }
    }

    @PostMapping("logout")
    public String logout(HttpServletRequest request) {
        if (userIndicatorService.isUser(request)) {
            try {
                User user = cookieService.getCurrentUser(request);
                String sessionId = shaService.encode(String.valueOf(new Random().nextInt()));
                user.setSessionId(sessionId);
                userRepository.save(user);
                activityService.log(user.getName() + " logged out", user);
                return "redirect:/?logout";
            } catch (NullPointerException e) {
                e.printStackTrace();
                return "redirect:/";
            }
        } else {
            return "redirect:/";
        }

    }
}
