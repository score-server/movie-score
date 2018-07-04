package ch.felix.moviedbapi.service;

import ch.felix.moviedbapi.data.entity.User;
import ch.felix.moviedbapi.data.repository.UserRepository;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Service;

/**
 * @PROJECT Hermann
 */


@Service
public class CookieService {

    private UserRepository userRepository;

    public CookieService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public void setUserCookie(HttpServletResponse response, String sessionId) {
        Cookie userCookie = new Cookie("session", sessionId);
        userCookie.setMaxAge(36000);
        userCookie.setPath("/");
        response.addCookie(userCookie);
    }

    /**
     * Returns Current User
     * <p>
     * Wenn cookie user heisst und der Wert nicht null ist gebe den username zurück
     */
    public User getCurrentUser(HttpServletRequest request) throws NullPointerException {
        for (Cookie cookie : request.getCookies()) {
            if (cookie.getName().equals("session")) {
                User user = userRepository.findUserBySessionId(cookie.getValue());
                if (user == null) {
                    throw new NullPointerException();
                }
                return user;
            }
        }
        throw new NullPointerException();
    }
}
