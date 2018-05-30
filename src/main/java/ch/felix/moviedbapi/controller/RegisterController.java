package ch.felix.moviedbapi.controller;

import ch.felix.moviedbapi.data.entity.User;
import ch.felix.moviedbapi.data.repository.UserRepository;
import ch.felix.moviedbapi.service.CookieService;
import ch.felix.moviedbapi.service.ShaService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

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

    private CookieService cookieService;
    private ShaService shaService;

    public RegisterController(UserRepository userRepository, CookieService cookieService, ShaService shaService) {
        this.userRepository = userRepository;
        this.cookieService = cookieService;
        this.shaService = shaService;
    }

    @PostMapping(produces = "application/json")
    public String register(@RequestParam("name") String nameParam,
                           @RequestParam("password") String password,
                           Model model) {
        if (userRepository.findUserByName(nameParam) == null) {
            User user = new User();
            user.setName(nameParam);
            user.setPasswordSha(shaService.encode(password));
            user.setRole(1);
            userRepository.save(user);
            model.addAttribute("response", "{\"response\":\"101\"}");//Added
        } else {
            model.addAttribute("response", "{\"response\":\"203\"}");//User already exists
        }
        return "json";
    }

}
