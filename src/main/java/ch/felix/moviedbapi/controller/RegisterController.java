package ch.felix.moviedbapi.controller;

import ch.felix.moviedbapi.data.entity.User;
import ch.felix.moviedbapi.data.repository.UserRepository;
import ch.felix.moviedbapi.service.CookieService;
import ch.felix.moviedbapi.service.ShaService;

import javax.servlet.http.HttpServletRequest;
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
    private CookieService cookieService;

    public RegisterController(UserRepository userRepository, ShaService shaService, CookieService cookieService) {
        this.userRepository = userRepository;
        this.shaService = shaService;
        this.cookieService = cookieService;
    }

    @GetMapping
    public String getRegister(Model model, HttpServletRequest request) {
        try {
            User currentUser = cookieService.getCurrentUser(request);
            if (currentUser.getRole() != 2) {
                return "redirect:/?";
            }

            model.addAttribute("currentUser", currentUser);
        } catch (NullPointerException e) {
            return "redirect:/login?redirect=/register";
        }

        model.addAttribute("page", "register");
        return "template";
    }

    @PostMapping
    public String register(@RequestParam("name") String nameParam, @RequestParam("password") String password) {
        if (userRepository.findUserByName(nameParam) == null) {
            try {
                User user = new User();
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
