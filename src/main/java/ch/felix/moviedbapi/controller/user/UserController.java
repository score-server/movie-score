package ch.felix.moviedbapi.controller.user;

import ch.felix.moviedbapi.data.entity.User;
import ch.felix.moviedbapi.data.repository.UserRepository;
import ch.felix.moviedbapi.service.ShaService;
import ch.felix.moviedbapi.service.ViolationService;
import java.util.List;
import javax.validation.ConstraintViolationException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Felix
 * @date 24.05.2018
 * <p>
 * Project: movie-db-api
 * Package: ch.felix.moviedbapi.controller
 **/

@RestController
@RequestMapping("user")
public class UserController {

    private UserRepository userRepository;

    private ShaService shaService;
    private ViolationService violationService;


    public UserController(UserRepository userRepository, ShaService shaService, ViolationService violationService) {
        this.userRepository = userRepository;
        this.shaService = shaService;
        this.violationService = violationService;
    }

    @GetMapping(produces = "application/json")
    public List<User> getUsers() {
        return userRepository.findAll();
    }

    @GetMapping(value = "{userId}", produces = "application/json")
    public User getOneUser(@PathVariable("userId") String userId) {
        try {
            return userRepository.findUserById(Long.valueOf(userId));
        } catch (NullPointerException | NumberFormatException e) {
            e.printStackTrace();
            return null;
        }
    }

    @GetMapping(value = "search/{search}", produces = "application/json")
    public List<User> searchUsers(@PathVariable("search") String searchParam) {
        try {
            return userRepository.findUsersByNameContaining(searchParam);
        } catch (NullPointerException | NumberFormatException e) {
            e.printStackTrace();
            return null;
        }
    }

    @PostMapping(value = "{userId}/role/{role}", produces = "application/json")
    public String setRole(@PathVariable("userId") String userId,
                          @PathVariable("role") String role) {
        try {
            User user = userRepository.findUserById(Long.valueOf(userId));
            user.setRole(Integer.valueOf(role));
            userRepository.save(user);
            return "102";
        } catch (NumberFormatException e) {
            return "205";
        }
    }

    @PostMapping("{userId}/name")
    public String setUsername(@PathVariable("userId") String userId,
                              @RequestParam("name") String newName) {
        try {
            User user = userRepository.findUserById(Long.valueOf(userId));
            user.setName(newName);
            userRepository.save(user);
            return "102";
        } catch (ConstraintViolationException e) {
            return "205 " + violationService.getViolation(e);
        }catch (NumberFormatException e) {
            return "204";
        }


    }

    @PostMapping("{userId}/password")
    public String setPassword(@PathVariable("userId") String userId,
                              @RequestParam("password") String newPassword) {
        try {
            User user = userRepository.findUserById(Long.valueOf(userId));
            user.setPasswordSha(shaService.encode(newPassword));
            userRepository.save(user);
            return "102";
        } catch (ConstraintViolationException e) {
            return "205 " + violationService.getViolation(e);
        }catch (NumberFormatException e) {
            return "204";
        }

    }

}
