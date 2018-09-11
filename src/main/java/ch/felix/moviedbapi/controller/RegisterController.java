package ch.felix.moviedbapi.controller;

import ch.felix.moviedbapi.data.entity.User;
import ch.felix.moviedbapi.data.repository.UserRepository;
import ch.felix.moviedbapi.service.ActivityService;
import ch.felix.moviedbapi.service.ShaService;
import ch.felix.moviedbapi.service.UserIndicatorService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import java.util.Random;

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
    private UserIndicatorService userIndicatorService;
    private ActivityService activityService;

    public RegisterController(UserRepository userRepository, ShaService shaService,
                              UserIndicatorService userIndicatorService, ActivityService activityService) {
        this.userRepository = userRepository;
        this.shaService = shaService;
        this.userIndicatorService = userIndicatorService;
        this.activityService = activityService;
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
    public String register(@RequestParam("name") String nameParam, HttpServletRequest request) {
        User adminUser = userIndicatorService.getUser(request).getUser();

        if (userIndicatorService.isAdministrator(request)) {
            if (userRepository.findUserByName(nameParam) == null) {
                User user = new User();
                user.setName(nameParam);
                user.setPasswordSha(shaService.encode(String.valueOf(new Random().nextInt())));
                user.setRole(1);
                user.setAuthKey(shaService.encode(String.valueOf(new Random().nextInt())));
                userRepository.save(user);
                activityService.log(nameParam + " registered by " + adminUser.getName(), adminUser);
                return "redirect:/user?added";
            } else {
                return "redirect:/register?exists";
            }
        } else {
            return "redirect:/user";
        }

    }

}
