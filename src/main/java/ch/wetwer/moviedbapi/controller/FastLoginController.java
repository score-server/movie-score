package ch.wetwer.moviedbapi.controller;

import ch.wetwer.moviedbapi.data.user.User;
import ch.wetwer.moviedbapi.data.user.UserDao;
import ch.wetwer.moviedbapi.service.ActivityService;
import ch.wetwer.moviedbapi.service.auth.CookieService;
import ch.wetwer.moviedbapi.service.auth.SessionService;
import ch.wetwer.moviedbapi.service.auth.ShaService;
import ch.wetwer.moviedbapi.service.auth.UserAuthService;
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

@Controller
@RequestMapping("fastlogin")
public class FastLoginController {

    private UserDao userDao;

    private UserAuthService userAuthService;
    private CookieService cookieService;
    private ActivityService activityService;
    private ShaService shaService;
    private SessionService sessionService;

    public FastLoginController(UserDao userDao, UserAuthService userAuthService, CookieService cookieService,
                               ShaService shaService, ActivityService activityService, SessionService sessionService) {

        this.userDao = userDao;
        this.userAuthService = userAuthService;
        this.cookieService = cookieService;
        this.shaService = shaService;
        this.activityService = activityService;
        this.sessionService = sessionService;
    }

    @GetMapping
    public String getFastLogin(Model model, HttpServletRequest request) {
        userAuthService.allowGuest(model, request);
        model.addAttribute("page", "fastlogin");
        return "template";
    }

    @GetMapping("{authkey}")
    public String checkFastLogin(@PathVariable("authkey") String authkey, Model model, HttpServletRequest request,
                                 HttpServletResponse response) {
        if (userAuthService.isUser(request)) {
            return "redirect:/?login";
        } else {
            userAuthService.allowGuest(model, request);
            for (User user : userDao.getAll()) {
                if (user.getAuthKey() == null) {
                    return "redirect:/fastlogin?error";
                } else if (authkey.equals(user.getAuthKey())) {
                    cookieService.setFastLoginCookie(response, user);
                    userDao.save(user);
                    activityService.log(user.getName() + " used Authkeylink", user);
                    return "redirect:/fastlogin/settings";
                }
            }
            return "redirect:/login?error";
        }
    }

    @PostMapping
    public String checkAuth(@RequestParam("authkey") String authkey, HttpServletResponse response) {

        for (User user : userDao.getAll()) {
            if (user.getAuthKey() == null) {
                return "redirect:/fastlogin?error";
            } else if (authkey.equals(user.getAuthKey())) {
                cookieService.setFastLoginCookie(response, user);
                userDao.save(user);
                activityService.log(user.getName() + " logged in with Authkey", user);
                return "redirect:/fastlogin/settings";
            }
        }
        return "redirect:/fastlogin?error";
    }

    @GetMapping("settings")
    public String getSettings(Model model, HttpServletRequest request) {
        if (cookieService.getFastLogin(request) != null) {
            model.addAttribute("user", cookieService.getFastLogin(request));
            model.addAttribute("page", "fastsettings");
            return "template";
        }
        return "redirect:/fastlogin?authkey";
    }

    @PostMapping("settings/{userId}")
    public String saveSettings(@PathVariable("userId") Long userId, @RequestParam("name") String nameParam,
                               @RequestParam("password") String passwordParam, @RequestParam("confirm") String confirm,
                               @RequestParam(name = "player", required = false, defaultValue = "plyr") String player,
                               HttpServletRequest request, HttpServletResponse response) {
        if (cookieService.getFastLogin(request) != null && passwordParam.equals(confirm)) {
            User user = userDao.getById(userId);
            user.setName(nameParam);
            user.setPasswordSha(shaService.encode(passwordParam));
            user.setVideoPlayer(player);
            user.setAuthKey(shaService.encode(String.valueOf(new Random().nextInt())));

            String sessionId = shaService.encode(String.valueOf(new Random().nextInt()));
            cookieService.setUserCookie(response, sessionId);
            sessionService.addSession(user, sessionId);
            user.setLastLogin(new Timestamp(new Date().getTime()));
            try {
                userDao.save(user);
            } catch (Exception e) {
                return "redirect:/fastlogin/settings?exists";
            }
            activityService.log(user.getName() + " registered with fastlogin", user);

            userDao.save(user);
            return "redirect:/";
        }
        return "redirect:/fastlogin/settings?error";
    }


}
