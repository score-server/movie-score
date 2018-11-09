package ch.felix.moviedbapi.service;

import ch.felix.moviedbapi.data.dto.UserDto;
import ch.felix.moviedbapi.data.entity.User;
import org.springframework.stereotype.Service;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Wetwer
 * @project hermann
 */
@Service
public class CookieService {

    private UserDto userDto;

    public CookieService(UserDto userDto) {
        this.userDto = userDto;
    }

    public void setUserCookie(HttpServletResponse response, String sessionId) {
        Cookie userCookie = new Cookie("sessionId", sessionId);
        userCookie.setMaxAge(31536000);
        userCookie.setPath("/");
        response.addCookie(userCookie);
    }

    public User getCurrentUser(HttpServletRequest request) throws NullPointerException {
        for (Cookie cookie : request.getCookies()) {
            if (cookie.getName().equals("sessionId")) {
                User user = userDto.getBySessionId(cookie.getValue());
                if (user == null) {
                    throw new NullPointerException();
                }
                return user;
            }
        }
        throw new NullPointerException();
    }

    public void setFastLoginCookie(HttpServletResponse response, User user) {
        Cookie userCookie = new Cookie("fast", user.getAuthKey());
        userCookie.setMaxAge(3600);
        userCookie.setPath("/");
        response.addCookie(userCookie);
    }

    public User getFastLogin(HttpServletRequest request) {
        for (Cookie cookie : request.getCookies()) {
            if (cookie.getName().equals("fast")) {
                User user = userDto.getByAuthKey(cookie.getValue());
                if (user == null) {
                    return null;
                }
                return user;
            }
        }
        return null;
    }

}
