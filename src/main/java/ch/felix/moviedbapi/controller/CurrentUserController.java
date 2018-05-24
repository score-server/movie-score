package ch.felix.moviedbapi.controller;

import ch.felix.moviedbapi.data.entity.User;
import ch.felix.moviedbapi.data.repository.UserRepository;
import ch.felix.moviedbapi.service.CookieService;
import ch.felix.moviedbapi.service.JsonService;
import javax.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author Felix
 * @date 24.05.2018
 * <p>
 * Project: movie-db-api
 * Package: ch.felix.moviedbapi.controller
 **/

@Controller
@RequestMapping("currentUser")
public class CurrentUserController {

    private UserRepository userRepository;

    private CookieService cookieService;
    private JsonService jsonService;

    public CurrentUserController(UserRepository userRepository, CookieService cookieService, JsonService jsonService) {
        this.userRepository = userRepository;
        this.cookieService = cookieService;
        this.jsonService = jsonService;
    }

    @GetMapping

    public String getCurrentUser(Model model, HttpServletRequest request) {
        try {
            User user = userRepository.findUserBySessionId(String.valueOf(cookieService.getCurrentUser(request)));
            model.addAttribute("response", jsonService.getUser(user));
        } catch (NullPointerException e) {
            model.addAttribute("response", "{\"Error\":\"2\"}");//User not logged in
        }

        return "json";
    }

}
