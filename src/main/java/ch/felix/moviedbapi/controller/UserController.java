package ch.felix.moviedbapi.controller;

import ch.felix.moviedbapi.data.entity.User;
import ch.felix.moviedbapi.data.repository.UserRepository;
import ch.felix.moviedbapi.service.JsonService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

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

    private JsonService jsonService;

    public UserController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @GetMapping
    public String getUsers(Model model) {
        model.addAttribute("response",
                jsonService
                        .getUserList(
                                userRepository.findAll()));
        return "json";
    }

    @GetMapping(value = "/{userId}")
    public String getOneUser(@PathVariable("userId") String userId,
                             Model model) {

        model.addAttribute("response", jsonService.getUser(userRepository.findUserById(Long.valueOf(userId))));
        return "json";
    }

    @GetMapping(value = "/{userId}/role/{role}")
    public String getOneUser(@PathVariable("userId") String userId,
                             @PathVariable("role") String role,
                             Model model) {
        User user = userRepository.findUserById(Long.valueOf(userId));
        user.setRole(Integer.valueOf(role));
        userRepository.save(user);
        model.addAttribute("response", "{\"response\":\"1\"}");//Role set
        return "json";
    }

}
