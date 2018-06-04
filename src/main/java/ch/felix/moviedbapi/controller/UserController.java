package ch.felix.moviedbapi.controller;

import ch.felix.moviedbapi.data.entity.User;
import ch.felix.moviedbapi.data.repository.UserRepository;
import java.util.List;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author Felix
 * @date 24.05.2018
 * <p>
 * Project: movie-db-api
 * Package: ch.felix.moviedbapi.controller
 **/

@Controller
@RequestMapping("user")
public class UserController {

    private UserRepository userRepository;


    public UserController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @GetMapping(produces = "application/json")
    public @ResponseBody
    List<User> getUsers() {
        return userRepository.findAll();
    }

    @GetMapping(value = "/{userId}", produces = "application/json")
    public @ResponseBody
    User getOneUser(@PathVariable("userId") String userId) {
        try {
            return userRepository.findUserById(Long.valueOf(userId));
        } catch (NullPointerException | NumberFormatException e) {
            e.printStackTrace();
            return null;
        }
    }

    @GetMapping(value = "/{userId}/role/{role}", produces = "application/json")
    public @ResponseBody
    String getOneUser(@PathVariable("userId") String userId,
                      @PathVariable("role") String role) {
        User user = userRepository.findUserById(Long.valueOf(userId));
        user.setRole(Integer.valueOf(role));
        userRepository.save(user);
        return "102";
    }

}
