package ch.felix.moviedbapi.model;

import ch.felix.moviedbapi.data.entity.User;
import lombok.Data;

@Data
public class UserIndicator {

    private boolean loggedIn;

    private User user;

}
