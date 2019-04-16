package ch.wetwer.moviedbapi.service.sugestion;

import ch.wetwer.moviedbapi.service.filehandler.WebHandler;
import ch.wetwer.moviedbapi.service.sugestion.model.yts.Movie;
import ch.wetwer.moviedbapi.service.sugestion.model.yts.Results;
import com.google.gson.Gson;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author Wetwer
 * @project movie-score
 * @package ch.wetwer.moviedbapi.service
 * @created 16.04.2019
 **/

@Service
public class SugestionService {

    public List<Movie> getAvalible(String search) {
        WebHandler webHandler = new WebHandler("https://yts.am/api/v2/list_movies.json?query_term=" + search);
        return new Gson().fromJson(webHandler.getContent(), Results.class).getMovieResults().getMovies();
    }

}
