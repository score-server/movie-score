package ch.felix.moviedbapi.controller;

import ch.felix.moviedbapi.data.entity.User;
import ch.felix.moviedbapi.data.repository.UserRepository;
import ch.felix.moviedbapi.service.ShaService;
import ch.felix.moviedbapi.service.ViolationService;

import javax.validation.ConstraintViolationException;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

/**
 * @author Felix
 * @date 24.05.2018
 * <p>
 * Project: movie-db-api
 * Package: ch.felix.moviedbapi.controller
 **/

@Controller
@RequestMapping("register")
public class RegisterController {

    private UserRepository userRepository;

    private ShaService shaService;

    public RegisterController(UserRepository userRepository, ShaService shaService) {
        this.userRepository = userRepository;
        this.shaService = shaService;
    }

    @GetMapping
    public String getRegister(Model model) {
        model.addAttribute("page", "register");
        return "template";
    }

    @PostMapping
    public String register(@RequestParam("name") String nameParam, @RequestParam("password") String password) {
        if (userRepository.findUserByName(nameParam) == null) {
            User user = new User();

            try {
                user.setName(nameParam);
                user.setPasswordSha(shaService.encode(password));
                user.setRole(1);
                userRepository.save(user);
                System.out.println("Registered User - " + nameParam);
                return "redirect:/login";
            } catch (ConstraintViolationException e) {
                return "redirect:/register";
            }
        } else {
            return "redirect:/register";
        }
    }

}
