package ch.wetwer.moviedbapi.controller.api;

import ch.wetwer.moviedbapi.data.user.Role;
import ch.wetwer.moviedbapi.data.user.User;
import ch.wetwer.moviedbapi.data.user.UserDao;
import ch.wetwer.moviedbapi.service.ActivityService;
import ch.wetwer.moviedbapi.service.auth.ShaService;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Random;

/**
 * @author Wetwer
 * @project movie-score
 */

@CrossOrigin
@RestController
@RequestMapping("api/user")
public class UserApiController {

    private UserDao userDto;

    private ShaService shaService;
    private ActivityService activityService;

    public UserApiController(UserDao userDto, ShaService shaService, ActivityService activityService) {
        this.userDto = userDto;
        this.shaService = shaService;
        this.activityService = activityService;
    }

    @GetMapping(produces = "application/json")
    public List<User> getUserList(@RequestParam(name = "name", required = false, defaultValue = "") String name,
                                  @RequestParam("sessionId") String sessionId) {
        if (userDto.getBySessionId(sessionId) != null) {
            return userDto.search(name);
        } else {
            return null;
        }
    }

    @GetMapping(value = "{userid}", produces = "application/json")
    public User getUserList(@PathVariable(name = "userid") Long userid,
                            @RequestParam("sessionId") String sessionId) {
        if (userDto.getBySessionId(sessionId) != null) {
            return userDto.getById(userid);
        } else {
            return null;
        }
    }

    @GetMapping(value = "current", produces = "application/json")
    public User getCurrentUser(@RequestParam("sessionId") String sessionId) {
        return userDto.getBySessionId(sessionId);
    }

    @PostMapping(value = "new")
    public String createUser(@RequestParam("name") String nameParam,
                             @RequestParam("sessionId") String sessionId) {
        if (userDto.getBySessionId(sessionId) != null) {
            User user = new User();
            user.setName(nameParam);
            user.setPasswordSha(shaService.encode(String.valueOf(new Random().nextInt())) + "-NOK");
            user.setRole(Role.USER);
            user.setAuthKey(shaService.encode(String.valueOf(new Random().nextInt())));
            activityService.log("Registered new User " + nameParam + " over API");
            userDto.save(user);
            return user.getAuthKey();
        } else {
            return null;
        }
    }
}
