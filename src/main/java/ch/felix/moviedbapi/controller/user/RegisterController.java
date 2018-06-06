package ch.felix.moviedbapi.controller.user;

import ch.felix.moviedbapi.data.entity.User;
import ch.felix.moviedbapi.data.repository.UserRepository;
import ch.felix.moviedbapi.service.ShaService;
import ch.felix.moviedbapi.service.ViolationService;
import javax.validation.ConstraintViolationException;
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

@RestController
@RequestMapping("register")
public class RegisterController {

    private UserRepository userRepository;

    private ShaService shaService;
    private ViolationService violationService;

    public RegisterController(UserRepository userRepository, ShaService shaService, ViolationService violationService) {
        this.userRepository = userRepository;
        this.shaService = shaService;
        this.violationService = violationService;
    }

    @PostMapping
    public String register(@RequestParam("name") String nameParam,
                           @RequestParam("password") String password) {
        if (userRepository.findUserByName(nameParam) == null) {
            User user = new User();

            try {
                user.setName(nameParam);
                user.setPasswordSha(shaService.encode(password));
                user.setRole(1);
                userRepository.save(user);
                System.out.println("Registered User - " + nameParam);
                return "101";
            } catch (ConstraintViolationException e) {
                return "206 " + violationService.getViolation(e);
            }
        } else {
            return "203";
        }
    }

}
