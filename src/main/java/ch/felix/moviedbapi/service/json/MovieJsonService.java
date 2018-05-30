package ch.felix.moviedbapi.service.json;

import ch.felix.moviedbapi.data.entity.Movie;
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
public class MovieJsonService {

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
        jsonSting = finishJson(jsonSting);
        return jsonSting.toString();
    }

    private StringBuilder finishJson(StringBuilder jsonSting) {
        jsonSting = new StringBuilder(jsonSting.substring(0, jsonSting.length() - 1));
        jsonSting.append("]");
        return jsonSting;
    }

}
