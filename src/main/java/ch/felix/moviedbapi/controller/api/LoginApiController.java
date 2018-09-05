package ch.felix.moviedbapi.controller.api;

import ch.felix.moviedbapi.data.entity.User;
import ch.felix.moviedbapi.data.repository.UserRepository;
import ch.felix.moviedbapi.service.ActivityService;
import ch.felix.moviedbapi.service.CookieService;
import ch.felix.moviedbapi.service.ShaService;
import ch.felix.moviedbapi.service.UserIndicatorService;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Random;

@CrossOrigin
@RestController
@RequestMapping("api/login")
public class LoginApiController {

    private UserRepository userRepository;

    private ShaService shaService;
    private CookieService cookieService;
    private ActivityService activityService;
    private UserIndicatorService userIndicatorService;

    public LoginApiController(UserRepository userRepository, ShaService shaService, CookieService cookieService,
                              ActivityService activityService, UserIndicatorService userIndicatorService) {
        this.userRepository = userRepository;
        this.shaService = shaService;
        this.cookieService = cookieService;
        this.activityService = activityService;
        this.userIndicatorService = userIndicatorService;
    }

    @PostMapping
    public String login(@RequestParam("name") String name, @RequestParam("password") String password,
                        HttpServletResponse response) {
        User user = userRepository.findUserByNameAndPasswordSha(name, shaService.encode(password));
        try {
            String sessionId = shaService.encode(String.valueOf(new Random().nextInt()));
            cookieService.setUserCookie(response, sessionId);
            user.setSessionId(sessionId);
            userRepository.save(user);
            activityService.log(user.getName() + " logged in", user);
            return "ok";
        } catch (NullPointerException e) {
            e.printStackTrace();
            return "nok";
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
                return "ok";
            } catch (NullPointerException e) {
                e.printStackTrace();
                return "nok";
            }
        } else {
            return "nok";
        }
    }
}
