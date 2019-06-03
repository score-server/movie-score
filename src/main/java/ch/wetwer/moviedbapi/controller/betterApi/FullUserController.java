package ch.wetwer.moviedbapi.controller.betterApi;

import ch.wetwer.moviedbapi.data.user.User;
import ch.wetwer.moviedbapi.data.user.UserDao;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Wetwer
 * @project movie-score
 * @package ch.wetwer.moviedbapi.controller.betterApi
 * @created 29.03.2019
 **/

@CrossOrigin
@RestController
@RequestMapping("api/2/user")
public class FullUserController {

    private UserDao userDao;

    public FullUserController(UserDao userDao) {
        this.userDao = userDao;
    }

    @GetMapping(produces = "application/json")
    public User getUser(@RequestParam("sessionId") String sessionId) {
        return userDao.getBySessionId(sessionId);
    }

    @ResponseBody
    @GetMapping("img/{userId}")
    public ResponseEntity<ByteArrayResource> getProfileFile(@PathVariable("userId") Long userId) {
        ByteArrayResource file = new ByteArrayResource(
                userDao.getById(userId).getProfileImg());
        return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION,
                "attachment; filename=\"" + file.getFilename() + "\"").body(file);
    }

}
