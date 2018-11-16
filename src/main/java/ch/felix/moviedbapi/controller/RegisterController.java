package ch.felix.moviedbapi.controller;

import ch.felix.moviedbapi.data.dto.GroupDto;
import ch.felix.moviedbapi.data.dto.UserDto;
import ch.felix.moviedbapi.data.entity.GroupInvite;
import ch.felix.moviedbapi.data.entity.User;
import ch.felix.moviedbapi.service.ActivityService;
import ch.felix.moviedbapi.service.CookieService;
import ch.felix.moviedbapi.service.SessionService;
import ch.felix.moviedbapi.service.ShaService;
import ch.felix.moviedbapi.service.UserAuthService;
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
@RequestMapping("register")
public class RegisterController {

    private UserDto userDto;
    private GroupDto groupDto;

    private ShaService shaService;
    private UserAuthService userAuthService;
    private ActivityService activityService;
    private CookieService cookieService;
    private SessionService sessionService;

    public RegisterController(UserDto userDto, GroupDto groupDto, ShaService shaService,
                              UserAuthService userAuthService, ActivityService activityService,
                              CookieService cookieService, SessionService sessionService) {
        this.userDto = userDto;
        this.groupDto = groupDto;
        this.shaService = shaService;
        this.userAuthService = userAuthService;
        this.activityService = activityService;
        this.cookieService = cookieService;
        this.sessionService = sessionService;
    }

    @GetMapping
    public String getRegister(Model model, HttpServletRequest request) {
        if (userAuthService.isAdministrator(model, request)) {
            model.addAttribute("page", "register");
            return "template";
        } else {
            return "redirect:/";
        }
    }

    @GetMapping("{groupKey}")
    public String getGroupRegister(@PathVariable("groupKey") String groupKey, Model model, HttpServletRequest request) {
        userAuthService.allowGuest(model, request);

        for (GroupInvite groupInvite : groupDto.getAll()) {
            if (groupInvite.getName().equals(groupKey)) {
                model.addAttribute("groupKey", groupKey);
                model.addAttribute("page", "fullRegister");
                return "template";
            }
        }
        return "redirect:/";
    }

    @PostMapping
    public String register(@RequestParam("name") String nameParam, HttpServletRequest request) {
        User adminUser = userAuthService.getUser(request).getUser();
        if (userAuthService.isAdministrator(request)) {
            if (userDto.search(nameParam).size() == 0) {
                User user = new User();
                user.setName(nameParam);
                user.setPasswordSha(shaService.encode(String.valueOf(new Random().nextInt())) + "-NOK");
                user.setRole(1);
                String authkey = shaService.encode(String.valueOf(new Random().nextInt())).substring(1, 7);
                user.setAuthKey(authkey);
                user.setSexabig(false);
                userDto.save(user);
                activityService.log(nameParam + " registered by " + adminUser.getName(), adminUser);
                return "redirect:/register?added=" + authkey;
            } else {
                return "redirect:/register?exists";
            }
        } else {
            return "redirect:/user";
        }
    }

    @PostMapping("{groupKey}")
    public String registerFull(@RequestParam("name") String nameParam,
                               @RequestParam("password") String password,
                               @RequestParam("confirm") String confirm,
                               @RequestParam(name = "player", required = false, defaultValue = "") String player,
                               @PathVariable("groupKey") String groupKey,
                               HttpServletResponse response) {
        if (password.equals(confirm)) {
            if (userDto.search(nameParam).size() == 0) {
                User user = new User();
                user.setName(nameParam);
                user.setPasswordSha(shaService.encode(password));
                user.setRole(1);
                user.setVideoPlayer(player);
                String authkey = shaService.encode(String.valueOf(new Random().nextInt())).substring(1, 7);
                user.setAuthKey(authkey);
                userDto.save(user);
                activityService.log(nameParam + " registered with groupkey " + groupKey, user);

                String sessionId = shaService.encode(String.valueOf(new Random().nextInt()));
                cookieService.setUserCookie(response, sessionId);
                sessionService.addSession(user, sessionId);
                user.setLastLogin(new Timestamp(new Date().getTime()));
                userDto.save(user);
                activityService.log(user.getName() + " logged in", user);

                return "redirect:/";
            } else {
                return "redirect:/register/" + groupKey + "?exists";
            }
        }
        return "redirect:/register/" + groupKey + "?password";
    }
}
