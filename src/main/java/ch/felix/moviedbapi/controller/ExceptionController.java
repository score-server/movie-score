package ch.felix.moviedbapi.controller;

import ch.felix.moviedbapi.service.auth.UserAuthService;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.NoHandlerFoundException;

import javax.servlet.http.HttpServletRequest;

@ControllerAdvice
public class ExceptionController {

    private UserAuthService userAuthService;

    public ExceptionController(UserAuthService userAuthService) {
        this.userAuthService = userAuthService;
    }

    @ExceptionHandler(NoHandlerFoundException.class)
    public String handleError404(HttpServletRequest request, Model model) {
        userAuthService.allowGuest(model, request);
        model.addAttribute("page", "404");
        return "template";
    }

}
