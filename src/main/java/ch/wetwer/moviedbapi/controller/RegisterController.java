package ch.wetwer.moviedbapi.controller;

import ch.wetwer.moviedbapi.data.groupinvite.GroupDao;
import ch.wetwer.moviedbapi.data.groupinvite.GroupInvite;
import ch.wetwer.moviedbapi.data.user.Role;
import ch.wetwer.moviedbapi.data.user.User;
import ch.wetwer.moviedbapi.data.user.UserDao;
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
 * @project movie-score
 */

@Slf4j
@Controller
@RequestMapping("register")
public class RegisterController {

    private UserDao userDao;
    private GroupDao groupDao;

    private ShaService shaService;
    private UserAuthService userAuthService;
    private ActivityService activityService;
    private CookieService cookieService;
    private SessionService sessionService;

    public RegisterController(UserDao userDao, GroupDao groupDao, ShaService shaService,
                              UserAuthService userAuthService, ActivityService activityService,
                              CookieService cookieService, SessionService sessionService) {
        this.userDao = userDao;
        this.groupDao = groupDao;
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
            return "redirect:/?access";
        }
    }

    @GetMapping("{groupKey}")
    public String getGroupRegister(@PathVariable("groupKey") String groupKey, Model model, HttpServletRequest request) {
        if (userAuthService.isUser(request)) {
            return "redirect:/?login";
        } else {
            userAuthService.allowGuest(model, request);

            for (GroupInvite groupInvite : groupDao.getAll()) {
                if (groupInvite.getName().equals(groupKey)) {
                    if (groupInvite.isActive()) {
                        model.addAttribute("groupKey", groupKey);
                        model.addAttribute("page", "fullRegister");
                        return "template";
                    }
                }
            }
            return "redirect:/?access";
        }

    }

    @PostMapping
    public String register(@RequestParam("name") String nameParam, HttpServletRequest request) {
        User adminUser = userAuthService.getUser(request).getUser();
        if (userAuthService.isAdministrator(request)) {
            userAuthService.log(this.getClass(), request);
            if (userDao.searchEqual(nameParam).size() == 0) {
                User user = new User();
                user.setName(nameParam);
                user.setPasswordSha(shaService.encode(String.valueOf(new Random().nextInt())) + "-NOK");
                user.setRole(Role.USER);
                String authkey = shaService.encodeShort(String.valueOf(new Random().nextInt()));
                user.setAuthKey(authkey);
                userDao.save(user);
                activityService.log(nameParam + " registered by " + adminUser.getName(), adminUser);
                return "redirect:/register?added=" + authkey;
            } else {
                return "redirect:/register?exists";
            }
        } else {
            return "redirect:/?access";
        }
    }

    @PostMapping("{groupKey}")
    public String groupRegister(@RequestParam("name") String nameParam,
                                @RequestParam("password") String password,
                                @RequestParam("confirm") String confirm,
                                @RequestParam(name = "player", required = false, defaultValue = "") String player,
                                @PathVariable("groupKey") String groupKey,
                                HttpServletResponse response) {
        if (password.equals(confirm)) {
            if (userDao.searchEqual(nameParam).size() == 0) {
                GroupInvite group = groupDao.getByName(groupKey);
                if (group.isActive()) {
                    User user = new User();
                    user.setName(nameParam);
                    user.setPasswordSha(shaService.encode(password));
                    user.setRole(Role.USER);
                    user.setVideoPlayer(player);
                    String authkey = shaService.encodeShort(String.valueOf(new Random().nextInt()));
                    user.setAuthKey(authkey);
                    user.setGroup(group);

                    userDao.save(user);
                    activityService.log(nameParam + " registered with groupkey " + groupKey, user);

                    String sessionId = shaService.encode(String.valueOf(new Random().nextInt()));
                    cookieService.setUserCookie(response, sessionId);
                    sessionService.addSession(user, sessionId);
                    user.setLastLogin(new Timestamp(new Date().getTime()));
                    userDao.save(user);
                    activityService.log(user.getName() + " logged in", user);

                    return "redirect:/";
                }
            } else {
                return "redirect:/register/" + groupKey + "?exists";
            }
        }
        return "redirect:/register/" + groupKey + "?password";
    }
}
