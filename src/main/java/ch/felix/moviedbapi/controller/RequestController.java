package ch.felix.moviedbapi.controller;

import ch.felix.moviedbapi.data.repository.RequestRepository;
import ch.felix.moviedbapi.service.JsonService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

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

    public RequestController(RequestRepository requestRepository, JsonService jsonService) {
        this.requestRepository = requestRepository;
        this.jsonService = jsonService;
    }

    @GetMapping
    public String getRequestList(Model model) {
        model.addAttribute("response", jsonService.getRequestList(requestRepository.findAll()));
        return "json";
    }

    @GetMapping(value = "/{requestId}")
    public String getOneRequest(@PathVariable("requestId") String requestParam,
                                Model model) {
        model.addAttribute("response", jsonService.getRequestList(
                requestRepository.findRequestsByUserFk(Long.valueOf(requestParam))));
        return "json";
    }
}
