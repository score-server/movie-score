package ch.wetwer.moviedbapi.service.auth;

import ch.wetwer.moviedbapi.data.user.User;
import lombok.Data;

/**
 * @author Wetwer
 * @project movie-db
 */

@Data
public class UserIndicator {

    private boolean loggedIn;

    private User user;

}
