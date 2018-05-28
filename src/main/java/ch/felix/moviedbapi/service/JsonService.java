package ch.felix.moviedbapi.service;

import ch.felix.moviedbapi.data.entity.Movie;
import ch.felix.moviedbapi.data.entity.Request;
import ch.felix.moviedbapi.data.entity.User;

import java.util.List;

import org.springframework.stereotype.Service;


import static org.apache.commons.lang.StringEscapeUtils.escapeHtml;

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
                + "\"title\": \"" + escapeHtml(movie.getTitle()) + "\","
                + "\"year\": \"" + movie.getYear() + "\","
                + "\"quality\": \"" + movie.getQuality() + "\","
                + "\"video_path\": \"" + escapeHtml(movie.getVideoPath().replace("\\", "\\\\")) + "\","
                + "\"img_path\": \"" + escapeHtml(movie.getCaseImg()) + "\","
                + "\"description\": \"" + escapeHtml(movie.getDescript()) + "\","
                + "\"runtime\": \"" + movie.getRuntime() + "\","
                + "\"popularity\": " + movie.getPopularity() + "}";
    }

    public String getMovieList(List<Movie> movieList) {
        StringBuilder jsonSting = new StringBuilder("[");
        for (Movie movie : movieList) {
            jsonSting.append(getMovie(movie)).append(",");
        }
        jsonSting = new StringBuilder(jsonSting.substring(0, jsonSting.length() - 1));
        jsonSting.append("]");
        return jsonSting.toString();
    }

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
        jsonSting = new StringBuilder(jsonSting.substring(0, jsonSting.length() - 1));
        jsonSting.append("]");
        return jsonSting.toString();
    }

    public String getRequest(Request request) {
        return "{\"id\": " + request.getId() + ","
                + "\"request\": \"" + escapeHtml(request.getRequest()) + "\","
                + "\"active\": \"" + request.getActive() + "\","
                + "\"user_id\": \"" + request.getUserFk() + "\"}";
    }

    public String getRequestList(List<Request> requestList) {
        StringBuilder jsonSting = new StringBuilder("[");
        for (Request request : requestList) {
            jsonSting.append(getRequest(request)).append(",");
        }
        jsonSting = new StringBuilder(jsonSting.substring(0, jsonSting.length() - 1));
        jsonSting.append("]");
        return jsonSting.toString();
    }

}
