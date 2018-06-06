package ch.felix.moviedbapi.controller.user;

import ch.felix.moviedbapi.data.entity.User;
import ch.felix.moviedbapi.data.repository.UserRepository;
import ch.felix.moviedbapi.service.CookieService;
import javax.servlet.http.HttpServletRequest;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Felix
 * @date 24.05.2018
 * <p>
 * Project: movie-db-api
 * Package: ch.felix.moviedbapi.controller
 **/

@RestController
@RequestMapping("currentUser")
public class CurrentUserController {

    private UserRepository userRepository;

    private CookieService cookieService;

    public CurrentUserController(UserRepository userRepository, CookieService cookieService) {
        this.userRepository = userRepository;
        this.cookieService = cookieService;
    }

    @GetMapping(produces = "application/json")
    public
    User getCurrentUser(HttpServletRequest request) {
        try {
            return userRepository.findUserBySessionId(String.valueOf(cookieService.getCurrentUser(request)));
        } catch (NullPointerException e) {
            e.printStackTrace();
            return null;
        }
    }
}
