package ch.felix.moviedbapi.controller;

import ch.felix.moviedbapi.data.entity.Request;
import ch.felix.moviedbapi.data.repository.RequestRepository;
import ch.felix.moviedbapi.service.CookieService;
import ch.felix.moviedbapi.service.JsonService;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

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

    private JsonService jsonService;
    private CookieService cookieService;

    public RequestController(RequestRepository requestRepository, JsonService jsonService,
                             CookieService cookieService) {
        this.requestRepository = requestRepository;
        this.jsonService = jsonService;
        this.cookieService = cookieService;
    }

    @GetMapping(produces = "application/json")
    public String getRequestList(Model model) {
        model.addAttribute("response", jsonService.getRequestList(requestRepository.findAll()));
        return "json";
    }

    @GetMapping(value = "/{requestId}", produces = "application/json")
    public String getOneRequest(@PathVariable("requestId") String requestParam, Model model) {
        model.addAttribute("response", jsonService.getRequestList(
                requestRepository.findRequestsByUserFk(Long.valueOf(requestParam))));
        return "json";
    }

    @PostMapping(value = "/add", produces = "application/json")
    public String createRequest(@RequestParam("request") String requestParam, Model model,
                                HttpServletRequest httpRequest) {
        try {
            Request request = new Request();
            request.setRequest(requestParam);
            request.setUserFk(cookieService.getCurrentUser(httpRequest));
            request.setActive("1");
            requestRepository.save(request);
            model.addAttribute("response", "{\"response\":\"1\"}");//Request added
        } catch (NullPointerException e) {
            model.addAttribute("response", "{\"response\":\"2\"}");//Not logged in
        }
        return "json";
    }

    @GetMapping(value = "/{requestId}/close", produces = "application/json")
    public String closeRequest(@PathVariable("requestId") String requestParam, Model model) {
        try {
            Request request = requestRepository.findRequestById(Long.valueOf(requestParam));
            request.setActive("0");
            requestRepository.save(request);
            model.addAttribute("response", "{\"response\":\"1\"}");//Request closed
        } catch (NullPointerException e) {
            model.addAttribute("response", "{\"response\":\"2\"}");//Requst not found
        }
        return "json";
    }

}
