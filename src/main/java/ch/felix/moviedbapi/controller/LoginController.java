package ch.felix.moviedbapi.controller;

import ch.felix.moviedbapi.data.entity.User;
import ch.felix.moviedbapi.data.repository.UserRepository;
import ch.felix.moviedbapi.service.CookieService;
import ch.felix.moviedbapi.service.ShaService;

import java.util.Random;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import ch.felix.moviedbapi.service.UserIndicatorService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

/**
 * @author Wetwer
 * @project movie-db
 */
@Controller
@RequestMapping("login")
public class LoginController {

    private UserRepository userRepository;

    private CookieService cookieService;
    private ShaService shaService;
    private UserIndicatorService userIndicatorService;

    public LoginController(UserRepository userRepository, CookieService cookieService, ShaService shaService, UserIndicatorService userIndicatorService) {
        this.userRepository = userRepository;
        this.cookieService = cookieService;
        this.shaService = shaService;
        this.userIndicatorService = userIndicatorService;
    }

    @GetMapping
    public String getLogin(Model model, HttpServletRequest request) {
        userIndicatorService.allowGuestAccess(model, request);

        model.addAttribute("page", "login");
        return "template";
    }

    @PostMapping
    public String login(@RequestParam(name = "redirect", required = false, defaultValue = "") String redirectParam,
                        @RequestParam("name") String nameParam,
                        @RequestParam("password") String passwordParam,
                        HttpServletResponse response, Model model) {
        User user = userRepository.findUserByNameAndPasswordSha(nameParam, shaService.encode(passwordParam));
        try {
            String sessionId = shaService.encode(String.valueOf(new Random().nextInt()));
            cookieService.setUserCookie(response, sessionId);
            user.setSessionId(sessionId);
            userRepository.save(user);
            if (redirectParam.equals("null")) {
                return "redirect:/";
            } else {
                return "redirect:" + redirectParam;
            }
        } catch (NullPointerException e) {
            return "redirect:/login?error&user=" + nameParam;
        }
    }

    @PostMapping("logout")
    public String logout(HttpServletRequest request) {
        try {
            User user = cookieService.getCurrentUser(request);
            String sessionId = shaService.encode(String.valueOf(new Random().nextInt()));
            user.setSessionId(sessionId);
            userRepository.save(user);
            return "redirect:/?logout";
        } catch (NullPointerException e) {
            return "redirect:/";
        }
    }
}
