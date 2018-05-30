package ch.felix.moviedbapi.service.json;

import ch.felix.moviedbapi.data.entity.User;
import java.util.List;
import org.springframework.stereotype.Service;

import static org.apache.commons.lang.StringEscapeUtils.escapeHtml;

/**
 * @author Felix
 * @date 30.05.2018
 * <p>
 * Project: movie-db-api
 * Package: ch.felix.moviedbapi.service.json
 **/

@Service
public class UserJsonService {
    public String getUser(User user) {
        return "{\"id\": " + user.getId() + ","
               + "\"name\": \"" + escapeHtml(user.getName()) + "\","
               + "\"role\": \"" + user.getRole() + "\","
               + "\"session_id\": \"" + user.getSessionId() + "\"}";
    }

    public String getUserList(List<User> userList) {
        StringBuilder jsonSting = new StringBuilder("[");
        for (User user : userList) {
            jsonSting.append(getUser(user)).append(",");
        }
        jsonSting = finishJson(jsonSting);
        return jsonSting.toString();
    }
    private StringBuilder finishJson(StringBuilder jsonSting) {
        jsonSting = new StringBuilder(jsonSting.substring(0, jsonSting.length() - 1));
        jsonSting.append("]");
        return jsonSting;
    }
}
