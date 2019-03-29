package ch.wetwer.moviedbapi.controller.betterApi;

import ch.wetwer.moviedbapi.data.user.User;
import ch.wetwer.moviedbapi.data.user.UserDao;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Wetwer
 * @project movie-score
 * @package ch.wetwer.moviedbapi.controller.betterApi
 * @created 29.03.2019
 **/

@CrossOrigin
@RestController
@RequestMapping("api/2/user")
public class FullUserController {

    private UserDao userDao;

    public FullUserController(UserDao userDao) {
        this.userDao = userDao;
    }

    @GetMapping(produces = "application/json")
    public User getUser(@RequestParam("sessionId") String sessionId) {
        return userDao.getBySessionId(sessionId);
    }

}
