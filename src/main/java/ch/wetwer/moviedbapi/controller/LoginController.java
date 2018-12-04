package ch.wetwer.moviedbapi.controller;

import ch.wetwer.moviedbapi.data.dao.UserDao;
import ch.wetwer.moviedbapi.data.entity.User;
import ch.wetwer.moviedbapi.service.ActivityService;
import ch.wetwer.moviedbapi.service.auth.CookieService;
import ch.wetwer.moviedbapi.service.auth.SessionService;
import ch.wetwer.moviedbapi.service.auth.ShaService;
import ch.wetwer.moviedbapi.service.auth.UserAuthService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.Timestamp;
import java.util.Date;
import java.util.Random;

/**
 * @author Wetwer
 * @project movie-db
 */

@Slf4j
@Controller
@RequestMapping("login")
public class LoginController {

    private UserDao userDto;

    private CookieService cookieService;
    private ShaService shaService;
    private UserAuthService userAuthService;
    private ActivityService activityService;
    private SessionService sessionService;

    public LoginController(UserDao userDto, CookieService cookieService, ShaService shaService,
                           UserAuthService userAuthService, ActivityService activityService,
                           SessionService sessionService) {
        this.userDto = userDto;
        this.cookieService = cookieService;
        this.shaService = shaService;
        this.userAuthService = userAuthService;
        this.activityService = activityService;
        this.sessionService = sessionService;
    }

    @GetMapping
    public String getLogin(Model model, HttpServletRequest request) {
        userAuthService.allowGuest(model, request);
        model.addAttribute("page", "login");
        return "template";
    }

    @PostMapping
    public String login(@RequestParam(name = "redirect", required = false, defaultValue = "") String redirectParam,
                        @RequestParam("name") String nameParam,
                        @RequestParam("password") String passwordParam,
                        HttpServletResponse response) {
        User user = userDto.login(nameParam, shaService.encode(passwordParam));

        try {
            userDto.exists(user);
            String sessionId = shaService.encode(String.valueOf(new Random().nextInt()));
            cookieService.setUserCookie(response, sessionId);
            sessionService.addSession(user, sessionId);
            user.setLastLogin(new Timestamp(new Date().getTime()));
            userDto.save(user);
            activityService.log(user.getName() + " logged in", user);
            switch (redirectParam) {
                case "null":
                    return "redirect:/";
                case "score":
                    return "redirect:http://scorewinner.ch";
                default:
                    return "redirect:" + redirectParam;
            }
        } catch (NullPointerException e) {
            return "redirect:/login?error&user=" + nameParam;
        }
    }

    @PostMapping("logout")
    public String logout(HttpServletRequest request) {
        if (userAuthService.isUser(request)) {
            try {
                User user = cookieService.getCurrentUser(request);
                sessionService.logout(cookieService.getSessionId(request));
                userDto.save(user);
                activityService.log(user.getName() + " logged out", user);
                return "redirect:/login?logout";
            } catch (NullPointerException e) {
                e.printStackTrace();
                return "redirect:/";
            }
        } else {
            return "redirect:/";
        }

    }

    @PostMapping("logout/{sessionId}")
    public String logout(@PathVariable("sessionId") String sessionId, HttpServletRequest request) {
        if (userAuthService.isAdministrator(request)) {
            try {
                User user = userDto.getBySessionId(sessionId);
                sessionService.logout(cookieService.getSessionId(request));
                return "redirect:/user/" + user.isSexabig();
            } catch (NullPointerException e) {
                e.printStackTrace();
                return "redirect:/";
            }
        } else {
            return "redirect:/";
        }

    }
}
