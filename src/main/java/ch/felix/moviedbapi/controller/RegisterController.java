package ch.felix.moviedbapi.controller;

import ch.felix.moviedbapi.data.entity.User;
import ch.felix.moviedbapi.data.repository.UserRepository;
import ch.felix.moviedbapi.service.CookieService;
import ch.felix.moviedbapi.service.ShaService;

import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintViolationException;

import ch.felix.moviedbapi.service.UserIndicatorService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

/**
 * @author Wetwer
 * @project movie-db
 */
@Slf4j
@Controller
@RequestMapping("register")
public class RegisterController {

    private UserRepository userRepository;

    private ShaService shaService;
    private CookieService cookieService;
    private UserIndicatorService userIndicatorService;

    public RegisterController(UserRepository userRepository, ShaService shaService, CookieService cookieService,
                              UserIndicatorService userIndicatorService) {
        this.userRepository = userRepository;
        this.shaService = shaService;
        this.cookieService = cookieService;
        this.userIndicatorService = userIndicatorService;
    }

    @GetMapping
    public String getRegister(Model model, HttpServletRequest request) {
        if (userIndicatorService.isAdministrator(model, request)) {
            model.addAttribute("page", "register");
            return "template";
        } else {
            return "redirect:/";
        }
    }

    @PostMapping
    public String register(@RequestParam("name") String nameParam,
                           @RequestParam("password") String password,
                           HttpServletRequest request) {
        if (userIndicatorService.isAdministrator(request)) {
            if (userRepository.findUserByName(nameParam) == null) {
                User user = new User();
                user.setName(nameParam);
                user.setPasswordSha(shaService.encode(password));
                user.setRole(1);
                userRepository.save(user);
                log.info("Registered User - " + nameParam);
                return "redirect:/user?added";
            } else {
                return "redirect:/register?exists";
            }
        } else {
            return "redirect:/user";
        }

    }

}
