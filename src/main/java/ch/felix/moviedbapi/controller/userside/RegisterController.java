package ch.felix.moviedbapi.controller.userside;

import ch.felix.moviedbapi.data.entity.User;
import ch.felix.moviedbapi.data.repository.UserRepository;
import ch.felix.moviedbapi.service.ShaService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

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

    @PostMapping(produces = "application/json")
    public @ResponseBody
    String register(@RequestParam("name") String nameParam,
                    @RequestParam("password") String password) {
        if (userRepository.findUserByName(nameParam) == null) {
            User user = new User();
            user.setName(nameParam);
            user.setPasswordSha(shaService.encode(password));
            user.setRole(1);
            userRepository.save(user);
            System.out.println("Registered User - " + nameParam);
            return "101";
        } else {
            return "203";
        }
    }

}
