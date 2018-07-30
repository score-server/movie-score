package ch.felix.moviedbapi.controller;

import ch.felix.moviedbapi.data.entity.User;
import ch.felix.moviedbapi.data.repository.UserRepository;
import ch.felix.moviedbapi.service.CookieService;
import ch.felix.moviedbapi.service.ShaService;

import java.util.Random;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

/**
 * @author Felix
 * @date 24.05.2018
 * <p>
 * Project: movie-db-api
 * Package: ch.felix.moviedbapi.controller
 **/

@Controller
@RequestMapping("login")
public class LoginController {

    private UserRepository userRepository;

    private CookieService cookieService;
    private ShaService shaService;

    public LoginController(UserRepository userRepository, CookieService cookieService, ShaService shaService) {
        this.userRepository = userRepository;
        this.cookieService = cookieService;
        this.shaService = shaService;
    }

    @GetMapping
    public String getLogin(Model model, HttpServletRequest request) {
        try {
            model.addAttribute("currentUser", cookieService.getCurrentUser(request));
        } catch (NullPointerException e) {
        }

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
            if (redirectParam.equals("null")) {
                return "redirect:/";
            } else {
                return "redirect:" + redirectParam;
            }
        } catch (NullPointerException e) {
            return "redirect:/login?error";
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
