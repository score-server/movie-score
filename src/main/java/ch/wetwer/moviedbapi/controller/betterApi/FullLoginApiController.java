package ch.wetwer.moviedbapi.controller.betterApi;

import ch.wetwer.moviedbapi.data.session.Session;
import ch.wetwer.moviedbapi.data.user.User;
import ch.wetwer.moviedbapi.data.user.UserDao;
import ch.wetwer.moviedbapi.service.ActivityService;
import ch.wetwer.moviedbapi.service.auth.SessionService;
import ch.wetwer.moviedbapi.service.auth.ShaService;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Random;

/**
 * @author Wetwer
 * @project movie-score
 * @package ch.wetwer.moviedbapi.controller.betterApi
 * @created 29.03.2019
 **/

@CrossOrigin
@RestController
@RequestMapping("api/2/login")
public class FullLoginApiController {

    private UserDao userDao;

    private ShaService shaService;
    private SessionService sessionService;
    private ActivityService activityService;

    public FullLoginApiController(UserDao userDao, ShaService shaService, ActivityService activityService,
                                  SessionService sessionService) {
        this.userDao = userDao;
        this.shaService = shaService;
        this.activityService = activityService;
        this.sessionService = sessionService;
    }


    @PostMapping(produces = "application/json")
    public Session login(@RequestParam("name") String name, @RequestParam("password") String password) {
        User user = userDao.login(name, shaService.encode(password));
        try {
            String sessionId = shaService.encode(String.valueOf(new Random().nextInt()));
            Session session = sessionService.addSession(user, sessionId);
            userDao.save(user);
            activityService.log(user.getName() + " logged in over API", user);
            return session;
        } catch (NullPointerException e) {
            e.printStackTrace();
            return null;
        }
    }
}
