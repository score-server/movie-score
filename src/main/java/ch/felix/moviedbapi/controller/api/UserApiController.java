package ch.felix.moviedbapi.controller.api;

import ch.felix.moviedbapi.data.entity.User;
import ch.felix.moviedbapi.data.repository.UserRepository;
import ch.felix.moviedbapi.service.UserIndicatorService;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("api/user")
public class UserApiController {

    private UserRepository userRepository;
    private UserIndicatorService userIndicatorService;

    public UserApiController(UserRepository userRepository, UserIndicatorService userIndicatorService) {
        this.userRepository = userRepository;
        this.userIndicatorService = userIndicatorService;
    }

    @GetMapping(produces = "application/json")
    public List<User> getUserList(@RequestParam(name = "name", required = false, defaultValue = "") String name,
                                  HttpServletRequest request) {
            return userRepository.findUsersByNameContaining(name);
    }

    @GetMapping(value = "{userid}", produces = "application/json")
    public User getUserList(@PathVariable(name = "userid") Long userid, HttpServletRequest request) {
        if (userIndicatorService.isUser(request)) {
            return userRepository.findUserById(userid);
        } else {
            return null;
        }
    }

    @GetMapping(value = "current", produces = "application/json")
    public User getCurrentUser(HttpServletRequest request) {
        return userIndicatorService.getUser(request).getUser();
    }
}
