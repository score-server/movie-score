package ch.felix.moviedbapi.controller;

import ch.felix.moviedbapi.data.entity.Request;
import ch.felix.moviedbapi.data.repository.RequestRepository;
import ch.felix.moviedbapi.data.repository.UserRepository;

import javax.servlet.http.HttpServletRequest;

import ch.felix.moviedbapi.service.UserIndicatorService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author Wetwer
 * @project movie-db
 */
@Slf4j
@Controller
@RequestMapping("request")
public class RequestController {

    private RequestRepository requestRepository;
    private UserRepository userRepository;

    private UserIndicatorService userIndicatorService;

    public RequestController(RequestRepository requestRepository, UserRepository userRepository,
                             UserIndicatorService userIndicatorService) {
        this.requestRepository = requestRepository;
        this.userRepository = userRepository;
        this.userIndicatorService = userIndicatorService;
    }

    @GetMapping(produces = "application/json")
    public String getRequestList(Model model, HttpServletRequest request) {
        if (userIndicatorService.isAdministrator(model, request)) {
            model.addAttribute("requests", requestRepository.findAll());
            model.addAttribute("page", "requestList");
            return "template";
        } else {
            return "redirect:/";
        }
    }

    @GetMapping(value = "create", produces = "application/json")
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
            Request movieRequest = new Request();
            movieRequest.setRequest(requestParam);
            movieRequest.setUser(userRepository.findUserById(Long.valueOf(userId)));
            movieRequest.setActive("1");
            requestRepository.save(movieRequest);
            log.info("Saved Request - " + requestParam);
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
            return "redirect:/request";
        } else {
            return "redirect:/request?error";
        }
    }

    @PostMapping("{requestId}/open")
    public String openRequest(@PathVariable("requestId") String requestParam, HttpServletRequest request) {
        if (userIndicatorService.isAdministrator(request)) {
            Request movieRequest = requestRepository.findRequestById(Long.valueOf(requestParam));
            movieRequest.setActive("1");
            requestRepository.save(movieRequest);
            return "redirect:/request";
        } else {
            return "redirect:/request";
        }
    }

}
