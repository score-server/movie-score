package ch.wetwer.moviedbapi.service.auth;

import ch.wetwer.moviedbapi.data.entity.User;
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
