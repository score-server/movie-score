package ch.wetwer.moviedbapi.controller;

import ch.wetwer.moviedbapi.data.episode.EpisodeDao;
import ch.wetwer.moviedbapi.data.movie.MovieDao;
import ch.wetwer.moviedbapi.data.request.Request;
import ch.wetwer.moviedbapi.data.request.RequestDao;
import ch.wetwer.moviedbapi.data.user.User;
import ch.wetwer.moviedbapi.data.user.UserDao;
import ch.wetwer.moviedbapi.service.ActivityService;
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

/**
 * @author Wetwer
 * @project movie-score
 */

@Slf4j
@Controller
@RequestMapping("request")
public class RequestController {

    private RequestDao requestDao;
    private UserDao userDao;
    private MovieDao movieDao;
    private EpisodeDao episodeDao;

    private UserAuthService userAuthService;
    private ActivityService activityService;

    public RequestController(RequestDao requestDao, UserDao userDao, MovieDao movieDao, EpisodeDao episodeDao,
                             UserAuthService userAuthService, ActivityService activityService) {
        this.requestDao = requestDao;
        this.userDao = userDao;
        this.movieDao = movieDao;
        this.episodeDao = episodeDao;
        this.userAuthService = userAuthService;
        this.activityService = activityService;
    }

    @GetMapping(value = "create")
    public String getCreateForm(Model model, HttpServletRequest request) {
        if (userAuthService.isUser(model, request)) {
            userAuthService.log(this.getClass(), request);
            model.addAttribute("movies", movieDao.getAll());
            model.addAttribute("page", "createRequest");
            return "template";
        } else {
            return "redirect:/login?redirect=/request/create";
        }

    }

    @PostMapping("create/{userId}")
    public String createRequest(@PathVariable("userId") Long userId, @RequestParam("request") String requestParam,
                                @RequestParam("type") String type, HttpServletRequest request) {
        if (userAuthService.isUser(request)) {
            User user = userDao.getById(userId);

            Request movieRequest = new Request();
            switch (type) {
                case "user":
                    movieRequest.setRequest("User Request: " + requestParam);
                    break;
                default:
                    movieRequest.setRequest(requestParam);
                    break;
            }

            movieRequest.setUser(user);
            movieRequest.setActive("1");
            requestDao.save(movieRequest);
            activityService.log(user.getName() + " created Request for " + requestParam, user);
            return "redirect:/user/" + userId + "?request";
        } else {
            return "redirect:/user/" + userId;
        }
    }

    @PostMapping("create/takedown")
    public String createTakedownRequest(@RequestParam("movie") Long movieId,
                                        @RequestParam("email") String email, HttpServletRequest request) {
        if (userAuthService.isUser(request)) {
            User user = userAuthService.getUser(request).getUser();
            Request movieRequest = new Request();
            movieRequest.setRequest("Takedown Request: "
                    + movieDao.getById(movieId).getTitle() + " " + email);
            movieRequest.setUser(user);
            movieRequest.setActive("1");
            requestDao.save(movieRequest);
            activityService.log(user.getName() + " created Takedown Request for Movie " + movieId, user);
            return "redirect:/user/" + user.getId() + "?request";
        } else {
            return "redirect:/";
        }
    }

    @PostMapping("{requestId}/close")
    public String closeRequest(@PathVariable("requestId") Long requestId, HttpServletRequest request) {
        if (userAuthService.isAdministrator(request)) {
            Request movieRequest = requestDao.getById(requestId);
            movieRequest.setActive("0");
            requestDao.save(movieRequest);
            return "redirect:/settings#request";
        } else {
            return "redirect:/settings?error";
        }
    }

    @PostMapping("{requestId}/open")
    public String openRequest(@PathVariable("requestId") Long requestId, HttpServletRequest request) {
        if (userAuthService.isAdministrator(request)) {
            Request movieRequest = requestDao.getById(requestId);
            movieRequest.setActive("1");
            requestDao.save(movieRequest);
            return "redirect:/settings#request";
        } else {
            return "redirect:/settings?error";
        }
    }

    @PostMapping("{requestId}/delete")
    public String deleteRequest(@PathVariable("requestId") Long requestId, HttpServletRequest request) {
        Request movieRequest = requestDao.getById(requestId);
        if (userAuthService.isAdministrator(request)) {
            requestDao.delete(movieRequest);
            return "redirect:/settings#request";
        } else if (userAuthService.isCurrentUser(request, movieRequest.getUser())) {
            requestDao.delete(movieRequest);
            return "redirect:/user/" + movieRequest.getUser().getId() + "?removedRequest";
        } else {
            return "redirect:/";
        }
    }

    @PostMapping("{requestId}/edit")
    public String editRequest(@PathVariable("requestId") Long requestId,
                              @RequestParam("request") String newRequest,
                              HttpServletRequest request) {
        Request movieRequest = requestDao.getById(requestId);
        if (userAuthService.isAdministrator(request)) {
            movieRequest.setRequest(newRequest);
            requestDao.save(movieRequest);
            return "redirect:/settings#request";
        } else if (userAuthService.isCurrentUser(request, movieRequest.getUser())) {
            movieRequest.setRequest(newRequest);
            requestDao.save(movieRequest);
            userAuthService.log(this.getClass(), movieRequest.getUser());
            return "redirect:/user/" + movieRequest.getUser().getId() + "?requestChanged";
        } else {
            return "redirect:/";
        }
    }

}
