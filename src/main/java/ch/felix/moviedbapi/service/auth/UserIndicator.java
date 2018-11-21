package ch.felix.moviedbapi.service.auth;

import ch.felix.moviedbapi.data.entity.User;
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
