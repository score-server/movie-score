package ch.felix.moviedbapi.controller;

import ch.felix.moviedbapi.data.entity.Request;
import ch.felix.moviedbapi.data.entity.User;
import ch.felix.moviedbapi.data.repository.RequestRepository;
import ch.felix.moviedbapi.data.repository.UserRepository;
import ch.felix.moviedbapi.service.CookieService;

import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintViolationException;

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

    private CookieService cookieService;

    public RequestController(RequestRepository requestRepository, UserRepository userRepository,
                             CookieService cookieService) {
        this.requestRepository = requestRepository;
        this.userRepository = userRepository;
        this.cookieService = cookieService;
    }

    @GetMapping(produces = "application/json")
    public String getRequestList(Model model, HttpServletRequest request) {
        try {
            User user = cookieService.getCurrentUser(request);
            model.addAttribute("currentUser", user);
            if (user.getRole() != 2) {
                return "redirect:/";
            }
        } catch (NullPointerException e) {
            return "redirect:/";
        }
        model.addAttribute("requests", requestRepository.findAll());
        model.addAttribute("page", "requestList");
        return "template";
    }

    @GetMapping(value = "create", produces = "application/json")
    public String getCreateForm(Model model, HttpServletRequest request) {
        try {
            model.addAttribute("currentUser", cookieService.getCurrentUser(request));
        } catch (NullPointerException e) {
            return "redirect:/login?redirect=/request/create";
        }
        model.addAttribute("page", "createRequest");
        return "template";
    }

    @PostMapping("create/{userId}")
    public String createRequest(@PathVariable("userId") String userId, @RequestParam("request") String requestParam) {
        try {
            Request request = new Request();
            request.setRequest(requestParam);
            request.setUser(userRepository.findUserById(Long.valueOf(userId)));
            request.setActive("1");
            requestRepository.save(request);
            log.info("Saved Request - " + requestParam);
            return "redirect:/user/" + userId + "?request";
        } catch (NullPointerException | ConstraintViolationException e) {
            return "redirect:/user/" + userId;
        }

    }

    @PostMapping("{requestId}/close")
    public String closeRequest(@PathVariable("requestId") String requestParam) {
        try {
            Request request = requestRepository.findRequestById(Long.valueOf(requestParam));
            request.setActive("0");
            requestRepository.save(request);
            return "redirect:/request";//Added
        } catch (NullPointerException e) {
            e.printStackTrace();
            return "redirect:/request";
        }
    }

    @PostMapping("{requestId}/open")
    public String openRequest(@PathVariable("requestId") String requestParam) {
        try {
            Request request = requestRepository.findRequestById(Long.valueOf(requestParam));
            request.setActive("1");
            requestRepository.save(request);
            return "redirect:/request";//Added
        } catch (NullPointerException e) {
            e.printStackTrace();
            return "redirect:/request";
        }
    }

}
