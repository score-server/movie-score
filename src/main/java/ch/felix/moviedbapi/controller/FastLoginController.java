package ch.felix.moviedbapi.controller;

import ch.felix.moviedbapi.data.dto.UserDto;
import ch.felix.moviedbapi.data.entity.User;
import ch.felix.moviedbapi.service.ActivityService;
import ch.felix.moviedbapi.service.CookieService;
import ch.felix.moviedbapi.service.ShaService;
import ch.felix.moviedbapi.service.UserAuthService;
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

@Controller
@RequestMapping("fastlogin")
public class FastLoginController {

    private UserDto userDto;

    private UserAuthService userAuthService;
    private CookieService cookieService;
    private ActivityService activityService;
    private ShaService shaService;

    public FastLoginController(UserDto userDto, UserAuthService userAuthService, CookieService cookieService,
                               ShaService shaService, ActivityService activityService) {
        this.userDto = userDto;
        this.userAuthService = userAuthService;
        this.cookieService = cookieService;
        this.shaService = shaService;
        this.activityService = activityService;
    }

    @GetMapping
    public String getFastLogin(Model model, HttpServletRequest request) {
        userAuthService.allowGuest(model, request);
        model.addAttribute("page", "fastlogin");
        return "template";
    }

    @GetMapping("{authkey}")
    public String checkFastLogin(@PathVariable("authkey") String authkey, Model model,
                                 HttpServletRequest request, HttpServletResponse response) {
        userAuthService.allowGuest(model, request);
        for (User user : userDto.getAll()) {
            if (user.getAuthKey() == null) {
            } else if (authkey.equals(user.getAuthKey())) {
                cookieService.setFastLoginCookie(response, user);
                userDto.save(user);
                activityService.log(user.getName() + " logged in with Authkey", user);
                return "redirect:/fastlogin/settings";
            }
        }
        return "redirect:/login?error";
    }

    @PostMapping
    public String checkAuth(@RequestParam("authkey") String authkey, HttpServletResponse response) {
        for (User user : userDto.getAll()) {
            if (user.getAuthKey() == null) {
            } else if (authkey.equals(user.getAuthKey())) {
                cookieService.setFastLoginCookie(response, user);
                userDto.save(user);
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
            User user = userDto.getById(userId);
            user.setName(nameParam);
            user.setPasswordSha(shaService.encode(passwordParam));
            user.setVideoPlayer(player);
            user.setAuthKey(shaService.encode(String.valueOf(new Random().nextInt())));

            String sessionId = shaService.encode(String.valueOf(new Random().nextInt()));
            cookieService.setUserCookie(response, sessionId);
            user.setSessionId(sessionId);
            user.setLastLogin(new Timestamp(new Date().getTime()));
            try {
                userDto.save(user);
            } catch (Exception e) {
                return "redirect:/fastlogin/settings?exists";
            }
            activityService.log(user.getName() + " set Settings", user);

            userDto.save(user);
            return "redirect:/";
        }
        return "redirect:/fastlogin/settings?error";
    }
}
