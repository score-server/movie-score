package ch.wetwer.moviedbapi.service.auth;

import ch.wetwer.moviedbapi.data.user.User;
import ch.wetwer.moviedbapi.data.user.UserDao;
import org.springframework.stereotype.Service;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Wetwer
 * @project movie-score
 */

@Service
public class CookieService {

    private UserDao userDao;

    public CookieService(UserDao userDao) {
        this.userDao = userDao;
    }

    public void setUserCookie(HttpServletResponse response, String sessionId) {
        Cookie userCookie = new Cookie("sessionId", sessionId);
        userCookie.setMaxAge(2629743);
        userCookie.setPath("/");
        response.addCookie(userCookie);
    }

    public User getCurrentUser(HttpServletRequest request) throws NullPointerException {
        for (Cookie cookie : request.getCookies()) {
            if (cookie.getName().equals("sessionId")) {
                User user = userDao.getBySessionId(cookie.getValue());
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
                User user = userDao.getByAuthKey(cookie.getValue());
                if (user == null) {
                    return null;
                }
                return user;
            }
        }
        return null;
    }

    public String getSessionId(HttpServletRequest request) {
        for (Cookie cookie : request.getCookies()) {
            if (cookie.getName().equals("sessionId")) {
                return cookie.getValue();
            }
        }
        return null;
    }
}
