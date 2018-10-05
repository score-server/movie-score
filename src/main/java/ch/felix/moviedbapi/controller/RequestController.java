package ch.felix.moviedbapi.controller;

import ch.felix.moviedbapi.data.dto.UserDto;
import ch.felix.moviedbapi.data.entity.Request;
import ch.felix.moviedbapi.data.entity.User;
import ch.felix.moviedbapi.data.repository.RequestRepository;
import ch.felix.moviedbapi.service.ActivityService;
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
@RequestMapping("request")
public class RequestController {

    private RequestRepository requestRepository;
    private UserDto userDto;

    private UserIndicatorService userIndicatorService;
    private ActivityService activityService;

    public RequestController(RequestRepository requestRepository, UserDto userDto, UserIndicatorService userIndicatorService, ActivityService activityService) {
        this.requestRepository = requestRepository;
        this.userDto = userDto;
        this.userIndicatorService = userIndicatorService;
        this.activityService = activityService;
    }

    @GetMapping(value = "create")
    public String getCreateForm(Model model, HttpServletRequest request) {
        if (userIndicatorService.isUser(model, request)) {
            model.addAttribute("page", "createRequest");
            return "template";
        } else {
            return "redirect:/login?redirect=/request/create";
        }

    }

    @PostMapping("create/{userId}")
    public String createRequest(@PathVariable("userId") String userId, @RequestParam("request") String requestParam,
                                HttpServletRequest request) {
        if (userIndicatorService.isUser(request)) {
            User user = userDto.getById(Long.valueOf(userId));

            Request movieRequest = new Request();
            movieRequest.setRequest(requestParam);
            movieRequest.setUser(user);
            movieRequest.setActive("1");
            requestRepository.save(movieRequest);
            activityService.log(user.getName() + " created Request for " + requestParam, user);
            return "redirect:/user/" + userId + "?request";
        } else {
            return "redirect:/user/" + userId;
        }
    }

    @PostMapping("{requestId}/close")
    public String closeRequest(@PathVariable("requestId") String requestParam, HttpServletRequest request) {
        if (userIndicatorService.isAdministrator(request)) {
            Request movieRequest = requestRepository.findRequestById(Long.valueOf(requestParam));
            movieRequest.setActive("0");
            requestRepository.save(movieRequest);
            return "redirect:/settings#request";
        } else {
            return "redirect:/settings?error";
        }
    }

    @PostMapping("{requestId}/open")
    public String openRequest(@PathVariable("requestId") String requestParam, HttpServletRequest request) {
        if (userIndicatorService.isAdministrator(request)) {
            Request movieRequest = requestRepository.findRequestById(Long.valueOf(requestParam));
            movieRequest.setActive("1");
            requestRepository.save(movieRequest);
            return "redirect:/settings#request";
        } else {
            return "redirect:/settings?error";
        }
    }

    @PostMapping("{requestId}/delete")
    public String deleteRequest(@PathVariable("requestId") String requestParam, HttpServletRequest request) {
        if (userIndicatorService.isAdministrator(request)) {
            Request movieRequest = requestRepository.findRequestById(Long.valueOf(requestParam));
            requestRepository.delete(movieRequest);
            return "redirect:/settings#request";
        } else {
            return "redirect:/settings?error";
        }
    }

}
