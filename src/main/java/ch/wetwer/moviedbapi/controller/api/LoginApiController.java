package ch.wetwer.moviedbapi.controller.api;

import ch.wetwer.moviedbapi.data.user.User;
import ch.wetwer.moviedbapi.data.user.UserDao;
import ch.wetwer.moviedbapi.service.ActivityService;
import ch.wetwer.moviedbapi.service.auth.CookieService;
import ch.wetwer.moviedbapi.service.auth.SessionService;
import ch.wetwer.moviedbapi.service.auth.ShaService;
import ch.wetwer.moviedbapi.service.auth.UserAuthService;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.util.Random;

/**
 * @author Wetwer
 * @project movie-db
 */

@CrossOrigin
@RestController
@RequestMapping("api/login")
public class LoginApiController {

    private UserDao userDao;

    private ShaService shaService;
    private CookieService cookieService;
    private ActivityService activityService;
    private UserAuthService userAuthService;
    private SessionService sessionService;

    public LoginApiController(UserDao userDao, ShaService shaService, CookieService cookieService,
                              ActivityService activityService, UserAuthService userAuthService,
                              SessionService sessionService) {
        this.userDao = userDao;
        this.shaService = shaService;
        this.cookieService = cookieService;
        this.activityService = activityService;
        this.userAuthService = userAuthService;
        this.sessionService = sessionService;
    }

    @PostMapping(produces = "application/json")
    public String login(@RequestParam("name") String name, @RequestParam("password") String password,
                        HttpServletResponse response) {
        User user = userDao.login(name, shaService.encode(password));
        try {
            System.out.println(user.getName());
            String sessionId = shaService.encode(String.valueOf(new Random().nextInt()));
            sessionService.addSession(user, sessionId);
            cookieService.setUserCookie(response, sessionId);
            userDao.save(user);
            activityService.log(user.getName() + " logged in over API", user);
            return sessionId;
        } catch (NullPointerException e) {
            e.printStackTrace();
            return "nok";
        }
    }

    @PostMapping("logout")
    public String logout(@RequestParam("sessionId") String sessionId) {
        if (userAuthService.isUser(sessionId)) {
            try {
                User user = userDao.getBySessionId(sessionId);
                sessionService.logout(sessionId);
                activityService.log(user.getName() + " logged out over API", user);
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
