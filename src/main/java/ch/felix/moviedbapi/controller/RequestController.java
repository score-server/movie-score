package ch.felix.moviedbapi.controller;

import ch.felix.moviedbapi.data.entity.Request;
import ch.felix.moviedbapi.data.repository.RequestRepository;
import ch.felix.moviedbapi.data.repository.UserRepository;
import ch.felix.moviedbapi.service.CookieService;
import ch.felix.moviedbapi.service.ViolationService;
import java.util.List;
import javax.validation.ConstraintViolationException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Felix
 * @date 24.05.2018
 * <p>
 * Project: movie-db-api
 * Package: ch.felix.moviedbapi.controller
 **/

@RestController
@RequestMapping("request")
public class RequestController {

    private RequestRepository requestRepository;
    private UserRepository userRepository;

    private ViolationService violationService;

    public RequestController(RequestRepository requestRepository, UserRepository userRepository, CookieService cookieService, ViolationService violationService) {
        this.requestRepository = requestRepository;
        this.userRepository = userRepository;
        this.violationService = violationService;
    }

    @GetMapping(produces = "application/json")
    public List<Request> getRequestList() {
        return requestRepository.findAll();
    }

    @GetMapping(value = "/{requestId}", produces = "application/json")
    public Request getOneRequest(@PathVariable("requestId") String requestParam) {
        return requestRepository.findRequestById(Long.valueOf(requestParam));
    }

    @PostMapping("/add")
    public String createRequest(@RequestParam("userId") String userId,
                                @RequestParam("request") String requestParam) {
        try {
            Request request = new Request();
            request.setRequest(requestParam);
            request.setUser(userRepository.findUserById(Long.valueOf(userId)));
            request.setActive("1");
            requestRepository.save(request);
            System.out.println("Saved Request - " + requestParam);
            return "103";//Added
        } catch (NullPointerException e) {
            return "204";//Not found
        } catch (ConstraintViolationException e) {
            return "205 " + violationService.getViolation(e);
        }

    }

    @PostMapping("/{requestId}/close")
    public String closeRequest(@PathVariable("requestId") String requestParam) {
        try {
            Request request = requestRepository.findRequestById(Long.valueOf(requestParam));
            request.setActive("0");
            requestRepository.save(request);
            return "103";//Added
        } catch (NullPointerException e) {
            return "204";//User not logged in
        }
    }

}
