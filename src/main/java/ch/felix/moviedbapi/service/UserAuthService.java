package ch.felix.moviedbapi.service;

import ch.felix.moviedbapi.data.dto.UserDto;
import ch.felix.moviedbapi.data.entity.User;
import ch.felix.moviedbapi.model.UserIndicator;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Wetwer
 * @project movie-db
 */
@Service
public class UserAuthService {

    private UserDto userDto;

    private CookieService cookieService;

    public UserAuthService(UserDto userDto, CookieService cookieService) {
        this.userDto = userDto;
        this.cookieService = cookieService;
    }

    public UserIndicator getUser(HttpServletRequest request) {
        UserIndicator userIndicator = new UserIndicator();
        try {
            userIndicator.setUser(cookieService.getCurrentUser(request));
            userIndicator.setLoggedIn(true);
        } catch (NullPointerException ignored) {
            userIndicator.setLoggedIn(false);
        }
        return userIndicator;
    }

    public void allowGuest(Model model, HttpServletRequest request) {
        UserIndicator userIndicator = getUser(request);

        if (userIndicator.isLoggedIn()) {
            model.addAttribute("currentUser", userIndicator.getUser());
        }
    }

    public boolean isUser(Model model, HttpServletRequest request) {
        UserIndicator userIndicator = getUser(request);

        if (userIndicator.isLoggedIn()) {
            model.addAttribute("currentUser", userIndicator.getUser());
            return true;
        }

        return false;
    }

    public boolean isUser(HttpServletRequest request) {
        UserIndicator userIndicator = getUser(request);

        return userIndicator.isLoggedIn();
    }

    public boolean isUser(String sessionId) {
        User user = userDto.getBySessionId(sessionId);

        return user != null;
    }


    public boolean isAdministrator(Model model, HttpServletRequest request) {
        UserIndicator userIndicator = getUser(request);

        if (userIndicator.isLoggedIn()) {
            model.addAttribute("currentUser", userIndicator.getUser());
            return userIndicator.getUser().getRole() == 2;
        }
        return false;
    }

    public boolean isAdministrator(HttpServletRequest request) {
        UserIndicator userIndicator = getUser(request);

        if (userIndicator.isLoggedIn()) {
            return userIndicator.getUser().getRole() == 2;
        }
        return false;
    }

    public boolean isCurrentUser(Model model, HttpServletRequest request, User user) {
        UserIndicator userIndicator = getUser(request);

        if (userIndicator.isLoggedIn()) {
            model.addAttribute("currentUser", userIndicator.getUser());
            return userIndicator.getUser() == user;
        }
        return false;
    }

    public boolean isCurrentUser(HttpServletRequest request, User user) {
        UserIndicator userIndicator = getUser(request);

        if (userIndicator.isLoggedIn()) {
            return userIndicator.getUser() == user;
        }
        return false;
    }

    public String getCurrentSessionId(HttpServletRequest request) {
        return cookieService.getSessionId(request);
    }
}
