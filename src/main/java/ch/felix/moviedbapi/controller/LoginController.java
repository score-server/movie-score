package ch.felix.moviedbapi.controller;

import ch.felix.moviedbapi.data.entity.User;
import ch.felix.moviedbapi.data.repository.UserRepository;
import ch.felix.moviedbapi.service.CookieService;
import ch.felix.moviedbapi.service.ShaService;
import java.util.Random;
import javax.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

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

    @PostMapping
    public String login(@RequestParam("name") String nameParam,
                        @RequestParam("password") String passwordParam,
                        Model model, HttpServletResponse response) {
        User user = userRepository.findUserByNameAndAndPasswordSha(nameParam, shaService.encode(passwordParam));

        try {
            String sessionId = shaService.encode(String.valueOf(new Random().nextInt()));

            cookieService.setUserCookie(response, sessionId);

            user.setSessionId(sessionId);
            userRepository.save(user);

            model.addAttribute("response", "{\"response\":\"1\"}");//Logged in
        } catch (NullPointerException e) {
            model.addAttribute("response", "{\"response\":\"2\"}");//Wrong Login
        }

        return "json";
    }

}
