package ch.felix.moviedbapi.controller;

import ch.felix.moviedbapi.data.dto.UserDto;
import ch.felix.moviedbapi.data.entity.User;
import ch.felix.moviedbapi.data.repository.ActivityLogRepository;
import ch.felix.moviedbapi.data.repository.CommentRepository;
import ch.felix.moviedbapi.data.repository.LikesRepository;
import ch.felix.moviedbapi.data.repository.RequestRepository;
import ch.felix.moviedbapi.data.repository.TimelineRepository;
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
import java.util.Random;

/**
 * @author Wetwer
 * @project movie-db
 */

@Slf4j
@Controller
@RequestMapping("user")
public class UserController {

    private TimelineRepository timelineRepository;
    private UserDto userDto;

    private ShaService shaService;
    private SearchService searchService;
    private UserIndicatorService userIndicatorService;
    private ActivityService activityService;
    private ActivityLogRepository activityLogRepository;
    private RequestRepository requestRepository;
    private CommentRepository commentRepository;
    private LikesRepository likesRepository;


    public UserController(TimelineRepository timelineRepository, UserDto userDto, ShaService shaService,
                          SearchService searchService, UserIndicatorService userIndicatorService,
                          ActivityService activityService, ActivityLogRepository activityLogRepository,
                          RequestRepository requestRepository, CommentRepository commentRepository,
                          LikesRepository likesRepository) {
        this.timelineRepository = timelineRepository;
        this.userDto = userDto;
        this.shaService = shaService;
        this.searchService = searchService;
        this.userIndicatorService = userIndicatorService;
        this.activityService = activityService;
        this.activityLogRepository = activityLogRepository;
        this.requestRepository = requestRepository;
        this.commentRepository = commentRepository;
        this.likesRepository = likesRepository;
    }

    @GetMapping
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

    @GetMapping(value = "{userId}")
    public String getOneUser(@PathVariable("userId") String userId, Model model, HttpServletRequest request) {
        if (userIndicatorService.isUser(model, request)) {
            User user = userDto.getById(Long.valueOf(userId));

            model.addAttribute("user", user);
            model.addAttribute("requests", user.getRequests());
            model.addAttribute("timelines", timelineRepository.findTimelinesByUser(user));
            model.addAttribute("activities",
                    activityLogRepository.findActivityLogsByUserOrderByTimestampDesc(user));
            if (user.getPasswordSha().endsWith("-NOK")) {
                model.addAttribute("registered", false);
            } else {
                model.addAttribute("registered", true);
            }


            model.addAttribute("page", "user");
            return "template";
        } else {
            return "redirect:/";
        }
    }

    @PostMapping(value = "{userId}/role/{role}")
    public String setRole(@PathVariable("userId") String userId, @PathVariable("role") String role,
                          HttpServletRequest request) {
        User currentUser = userIndicatorService.getUser(request).getUser();

        if (userIndicatorService.isAdministrator(request)) {
            User user = userDto.getById(Long.valueOf(userId));
            user.setRole(Integer.valueOf(role));
            userDto.save(user);
            activityService.log(currentUser.getName() + " changed role of " + user.getName() + " to " + role, user);
            return "redirect:/user/" + userId + "?role";
        } else {
            return "redirect:/user/" + userId + "?error";
        }
    }

    @PostMapping("{userId}/name")
    public String setUsername(@PathVariable("userId") String userId, @RequestParam("name") String newName,
                              Model model, HttpServletRequest request) {
        User user = userDto.getById(Long.valueOf(userId));
        String oldName = user.getName();
        if (userIndicatorService.isCurrentUser(model, request, user)) {
            user.setName(newName);
            userDto.save(user);
            activityService.log(oldName + " changed Username to " + newName, user);
            return "redirect:/user/" + userId + "?username";
        } else {
            return "redirect:/user/" + userId + "?error";
        }
    }

    @PostMapping("{userId}/password")
    public String setPassword(@PathVariable("userId") String userId,
                              @RequestParam("old") String oldPassword,
                              @RequestParam("new") String newPassword, Model model, HttpServletRequest request) {

        User user = userDto.getByIdAndPasswordSha(Long.valueOf(userId), shaService.encode(oldPassword));

        if (userIndicatorService.isCurrentUser(model, request, user)) {
            user.setPasswordSha(shaService.encode(newPassword));
            userDto.save(user);
            activityService.log(user.getName() + " changed Password", user);
            return "redirect:/user/" + userId + "?password";
        } else {
            return "redirect:/user/" + userId + "?error";
        }
    }

    @PostMapping("{userId}/player")
    public String setPlayer(@PathVariable("userId") String userId,
                            @RequestParam("player") String videoPlayer, Model model, HttpServletRequest request) {

        User user = userDto.getById(Long.valueOf(userId));

        if (userIndicatorService.isCurrentUser(model, request, user)) {
            user.setVideoPlayer(videoPlayer);
            userDto.save(user);
            activityService.log(user.getName() + " set Video Player to " + videoPlayer, user);
            switch (videoPlayer) {
                case "plyr":
                    return "redirect:/user/" + userId + "?player=Plyr.io";
                case "html5":
                    return "redirect:/user/" + userId + "?player=HTML 5 Player";
                default:
                    return "redirect:/user/" + userId;
            }
        } else {
            return "redirect:/user/" + userId + "?error";
        }
    }

    @PostMapping("generate/{userId}")
    public String generateKey(@PathVariable("userId") Long userId, HttpServletRequest request) {
        if (userIndicatorService.isAdministrator(request)) {
            User user = userDto.getById(userId);
            user.setAuthKey(shaService.encode(String.valueOf(new Random().nextInt())));
            userDto.save(user);
        }
        return "redirect:/user/" + userId;
    }
}
