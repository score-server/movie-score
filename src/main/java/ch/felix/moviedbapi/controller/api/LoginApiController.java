package ch.felix.moviedbapi.controller.api;

import ch.felix.moviedbapi.data.dto.UserDto;
import ch.felix.moviedbapi.data.entity.User;
import ch.felix.moviedbapi.service.ActivityService;
import ch.felix.moviedbapi.service.CookieService;
import ch.felix.moviedbapi.service.ShaService;
import ch.felix.moviedbapi.service.UserAuthService;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.util.Random;

@CrossOrigin
@RestController
@RequestMapping("api/login")
public class LoginApiController {

    private UserDto userDto;

    private ShaService shaService;
    private CookieService cookieService;
    private ActivityService activityService;
    private UserAuthService userAuthService;

    public LoginApiController(UserDto userDto, ShaService shaService, CookieService cookieService,
                              ActivityService activityService, UserAuthService userAuthService) {
        this.userDto = userDto;
        this.shaService = shaService;
        this.cookieService = cookieService;
        this.activityService = activityService;
        this.userAuthService = userAuthService;
    }

    @PostMapping
    public String login(@RequestParam("name") String name, @RequestParam("password") String password,
                        HttpServletResponse response) {
        User user = userDto.login(name, shaService.encode(password));
        try {
            String sessionId = shaService.encode(String.valueOf(new Random().nextInt()));
            user.setSessionId(sessionId);
            cookieService.setUserCookie(response, sessionId);
            userDto.save(user);
            activityService.log(user.getName() + " logged in", user);
            return user.getSessionId();
        } catch (NullPointerException e) {
            e.printStackTrace();
            return "nok";
        }
    }

    @PostMapping("logout")
    public String logout(@RequestParam("sessionId") String sessionId) {
        if (userAuthService.isUser(sessionId)) {
            try {
                User user = userDto.getBySessionId(sessionId);
                user.setSessionId(shaService.encode(String.valueOf(new Random().nextInt())));
                userDto.save(user);
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
