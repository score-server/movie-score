package ch.felix.moviedbapi.controller;

import ch.felix.moviedbapi.data.entity.User;
import ch.felix.moviedbapi.data.repository.TimelineRepository;
import ch.felix.moviedbapi.data.repository.UserRepository;
import ch.felix.moviedbapi.service.ActivityService;
import ch.felix.moviedbapi.service.SearchService;
import ch.felix.moviedbapi.service.ShaService;
import ch.felix.moviedbapi.service.UserIndicatorService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Wetwer
 * @project movie-db
 */

@Slf4j
@Controller
@RequestMapping("user")
public class UserController {

    private UserRepository userRepository;
    private TimelineRepository timelineRepository;

    private ShaService shaService;
    private SearchService searchService;
    private UserIndicatorService userIndicatorService;
    private ActivityService activityService;


    public UserController(UserRepository userRepository, TimelineRepository timelineRepository, ShaService shaService,
                          SearchService searchService, UserIndicatorService userIndicatorService, ActivityService activityService) {
        this.userRepository = userRepository;
        this.timelineRepository = timelineRepository;
        this.shaService = shaService;
        this.searchService = searchService;
        this.userIndicatorService = userIndicatorService;
        this.activityService = activityService;
    }

    @GetMapping(produces = "application/json")
    public String getUserList(@RequestParam(name = "search", required = false, defaultValue = "") String search,
                              Model model, HttpServletRequest request) {
        if (userIndicatorService.isUser(model, request)) {
            model.addAttribute("users", searchService.searchUser(search));
            model.addAttribute("search", search);
            model.addAttribute("page", "userList");
            return "template";
        } else {
            return "redirect:/login?redirect=/user";
        }
    }

    @GetMapping(value = "{userId}", produces = "application/json")
    public String getOneUser(@PathVariable("userId") String userId, Model model, HttpServletRequest request) {
        if (userIndicatorService.isUser(model, request)) {
            User user = userRepository.findUserById(Long.valueOf(userId));

            model.addAttribute("user", user);
            model.addAttribute("requests", user.getRequests());
            model.addAttribute("timelines", timelineRepository.findTimelinesByUser(user));
            model.addAttribute("page", "user");
            return "template";
        } else {
            return "redirect:/";
        }
    }

    @PostMapping(value = "{userId}/role/{role}", produces = "application/json")
    public String setRole(@PathVariable("userId") String userId, @PathVariable("role") String role,
                          HttpServletRequest request) {
        if (userIndicatorService.isAdministrator(request)) {
            User user = userRepository.findUserById(Long.valueOf(userId));
            user.setRole(Integer.valueOf(role));
            userRepository.save(user);
            return "redirect:/user/" + userId + "?role";
        } else {
            return "redirect:/user/" + userId + "?error";
        }
    }

    @PostMapping(value = "{userId}/delete", produces = "application/json")
    public String deleteUser(@PathVariable("userId") String userId,
                             HttpServletRequest request) {
        if (userIndicatorService.isAdministrator(request)) {
            User user = userRepository.findUserById(Long.valueOf(userId));
            userRepository.delete(user);
            activityService.log(user.getName() + " removed");
            return "redirect:/user/?deleted";
        } else {
            return "redirect:/user/" + userId + "?error";
        }
    }

    @PostMapping("{userId}/name")
    public String setUsername(@PathVariable("userId") String userId, @RequestParam("name") String newName,
                              Model model, HttpServletRequest request) {
        User user = userRepository.findUserById(Long.valueOf(userId));
        String oldName = user.getName();
        if (userIndicatorService.isCurrentUser(model, request, user)) {
            user.setName(newName);
            userRepository.save(user);
            activityService.log(oldName + " changed Username to " + newName);
            return "redirect:/user/" + userId + "?username";
        } else {
            return "redirect:/user/" + userId + "?error";
        }
    }

    @PostMapping("{userId}/password")
    public String setPassword(@PathVariable("userId") String userId,
                              @RequestParam("old") String oldPassword,
                              @RequestParam("new") String newPassword, Model model, HttpServletRequest request) {

        User user = userRepository.findUserByIdAndPasswordSha(Long.valueOf(userId), shaService.encode(oldPassword));

        if (userIndicatorService.isCurrentUser(model, request, user)) {
            user.setPasswordSha(shaService.encode(newPassword));
            userRepository.save(user);
            activityService.log(user.getName() + " changed Password");
            return "redirect:/user/" + userId + "?password";
        } else {
            return "redirect:/user/" + userId + "?error";
        }
    }

}
