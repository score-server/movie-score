package ch.felix.moviedbapi.service;

import ch.felix.moviedbapi.data.entity.Movie;
import ch.felix.moviedbapi.data.entity.Request;
import ch.felix.moviedbapi.data.entity.User;
import java.util.List;
import org.springframework.stereotype.Service;

/**
 * @author Felix
 * @date 23.05.2018
 * <p>
 * Project: movie-db-api
 * Package: ch.felix.moviedbapi.service
 **/

@Service
public class JsonService {

    public String getMovie(Movie movie) {
        return "{\"id\": " + movie.getId() + ","
               + "\"title\": \"" + movie.getTitle() + "\","
               + "\"year\": \"" + movie.getYear() + "\","
               + "\"quality\": \"" + movie.getQuality() + "\","
               + "\"video_path\": \"" + movie.getVideoPath() + "\","
               + "\"img_path\": \"" + movie.getCaseImg() + "\","
               + "\"description\": \"" + movie.getDescript() + "\","
               + "\"runtime\": \"" + movie.getRuntime() + "\","
               + "\"popularity\": " + movie.getPopularity() + "}";
    }

    public String getMovieList(List<Movie> movieList) {
        String jsonSting = "[";
        for (Movie movie : movieList) {
            jsonSting = jsonSting + getMovie(movie) + ",";
        }
        jsonSting = jsonSting.substring(0, jsonSting.length() - 1);
        jsonSting = jsonSting + "]";
        return jsonSting;
    }

    public String getUser(User user) {
        return "{\"id\": " + user.getId() + ","
               + "\"title\": \"" + user.getName() + "\","
               + "\"year\": \"" + user.getRole() + "\","
               + "\"quality\": \"" + user.getSessionId() + "\",}";
    }

    public String getUserList(List<User> userList) {
        String jsonSting = "[";
        for (User user : userList) {
            jsonSting = jsonSting + getUser(user) + ",";
        }
        jsonSting = jsonSting.substring(0, jsonSting.length() - 1);
        jsonSting = jsonSting + "]";
        return jsonSting;
    }

    public String getRequest(Request request) {
        return "{\"id\": " + request.getId() + ","
               + "\"request\": \"" + request.getRequest() + "\","
               + "\"active\": \"" + request.getActive() + "\","
               + "\"userId\": \"" + request.getUserFk() + "\",}";
    }

    public String getRequestList(List<Request> requestList) {
        String jsonSting = "[";
        for (Request request : requestList) {
            jsonSting = jsonSting + getRequest(request) + ",";
        }
        jsonSting = jsonSting.substring(0, jsonSting.length() - 1);
        jsonSting = jsonSting + "]";
        return jsonSting;
    }

}
