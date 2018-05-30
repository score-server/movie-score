package ch.felix.moviedbapi.controller;

import ch.felix.moviedbapi.data.entity.Request;
import ch.felix.moviedbapi.data.repository.RequestRepository;
import ch.felix.moviedbapi.service.CookieService;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author Felix
 * @date 24.05.2018
 * <p>
 * Project: movie-db-api
 * Package: ch.felix.moviedbapi.controller
 **/

@Controller
@RequestMapping("request")
public class RequestController {

    private RequestRepository requestRepository;

    private CookieService cookieService;

    public RequestController(RequestRepository requestRepository, CookieService cookieService) {
        this.requestRepository = requestRepository;
        this.cookieService = cookieService;
    }

    @GetMapping(produces = "application/json")
    public @ResponseBody
    List<Request> getRequestList() {
        return requestRepository.findAll();
    }

    @GetMapping(value = "/{requestId}", produces = "application/json")
    public @ResponseBody
    Request getOneRequest(@PathVariable("requestId") String requestParam) {
        return requestRepository.findRequestById(Long.valueOf(requestParam));
    }

    @PostMapping(value = "/add", produces = "application/json")
    public @ResponseBody
    String createRequest(@RequestParam("request") String requestParam, Model model,
                         HttpServletRequest httpRequest) {
        try {
            Request request = new Request();
            request.setRequest(requestParam);
            request.setUserFk(cookieService.getCurrentUser(httpRequest));
            request.setActive("1");
            requestRepository.save(request);
            return "101";//Added
        } catch (NullPointerException e) {
            return "202";//User not logged in
        }
    }

    @GetMapping(value = "/{requestId}/close", produces = "application/json")
    public @ResponseBody
    String closeRequest(@PathVariable("requestId") String requestParam, Model model) {
        try {
            Request request = requestRepository.findRequestById(Long.valueOf(requestParam));
            request.setActive("0");
            requestRepository.save(request);
            return "101";//Added
        } catch (NullPointerException e) {
            return "202";//User not logged in
        }
    }

}
