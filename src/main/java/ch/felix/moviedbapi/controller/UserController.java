package ch.felix.moviedbapi.controller;

import ch.felix.moviedbapi.data.entity.User;
import ch.felix.moviedbapi.data.repository.UserRepository;
import ch.felix.moviedbapi.service.ShaService;
import ch.felix.moviedbapi.service.ViolationService;

import java.util.List;
import javax.validation.ConstraintViolationException;

import com.sun.org.apache.xpath.internal.operations.Mod;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
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

@Controller
@RequestMapping("user")
public class UserController {

    private UserRepository userRepository;

    private ShaService shaService;


    public UserController(UserRepository userRepository, ShaService shaService) {
        this.userRepository = userRepository;
        this.shaService = shaService;
    }

    @GetMapping(produces = "application/json")
    public String getUserList(Model model) {
        model.addAttribute("users", userRepository.findAll());
        model.addAttribute("page", "userList");
        return "template";
    }

    @GetMapping(value = "{userId}", produces = "application/json")
    public String getOneUser(@PathVariable("userId") String userId, Model model) {
        model.addAttribute("user", userRepository.findUserById(Long.valueOf(userId)));
        model.addAttribute("page", "user");
        return "template";
    }

    @GetMapping(value = "search", produces = "application/json")
    public String searchUsers(@RequestParam(value = "search", required = false, defaultValue = "") String searchParam,
                              Model model) {
        try {
            model.addAttribute("users", userRepository.findUsersByNameContaining(searchParam));
            model.addAttribute("page", "searchUser");
            return "template";
        } catch (NullPointerException | NumberFormatException e) {
            e.printStackTrace();
            return "redirect:/user";
        }
    }

    @PostMapping(value = "{userId}/role/{role}", produces = "application/json")
    public String setRole(@PathVariable("userId") String userId, @PathVariable("role") String role) {
        try {
            User user = userRepository.findUserById(Long.valueOf(userId));
            user.setRole(Integer.valueOf(role));
            userRepository.save(user);
            return "redirect:/user/" + userId;
        } catch (NumberFormatException e) {
            return "redirect:/user/" + userId + "?error=Could not set Role";
        }
    }

    @PostMapping("{userId}/name")
    public String setUsername(@PathVariable("userId") String userId, @RequestParam("name") String newName) {
        try {
            User user = userRepository.findUserById(Long.valueOf(userId));
            user.setName(newName);
            userRepository.save(user);
            return "redirect:/user/" + userId;
        } catch (ConstraintViolationException | NumberFormatException e) {
            return "redirect:/user/" + userId;
        }


    }

    @PostMapping("{userId}/password")
    public String setPassword(@PathVariable("userId") String userId, @RequestParam("password") String newPassword) {
        try {
            User user = userRepository.findUserById(Long.valueOf(userId));
            user.setPasswordSha(shaService.encode(newPassword));
            userRepository.save(user);
            return "redirect:/user/" + userId;
        } catch (ConstraintViolationException | NumberFormatException e) {
            return "redirect:/user/" + userId;
        }

    }

}
