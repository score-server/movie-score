package ch.felix.moviedbapi.service.importer;

import ch.felix.moviedbapi.jsonmodel.tmdb.MovieJson;
import ch.felix.moviedbapi.jsonmodel.tmdb.Search;
import ch.felix.moviedbapi.jsonmodel.tmdb.SerieJson;
import ch.felix.moviedbapi.service.filehandler.WebHandler;
import com.google.gson.Gson;
import org.springframework.stereotype.Service;

/**
 * @author Felix
 * @date 11.05.2018
 * <p>
 * Project: tmdbApi
 **/
@Service
public class SearchMovieService {

    private final String API_KEY = "c9cb1f249178495875c17631a8040bfa";

    public int findMovieId(String movieName, String year) {
        Search search = new Gson().fromJson(
                new WebHandler("https://api.themoviedb.org/3/search/movie?api_key=" + API_KEY + "&query="
                               + movieName.replace(" ", "%20") + "&year=" + year).getContent(), Search.class);
        if (search != null) {
            try {
                return search.getResults().get(0).getId();
            } catch (IndexOutOfBoundsException e) {
                return 0;
            }
        }
        return 0;
    }

    public int findSeriesId(String seriesName, String year) {
        Search search = new Gson().fromJson(
                new WebHandler("https://api.themoviedb.org/3/search/tv?api_key=" + API_KEY + "&query="
                               + seriesName.replace(" ", "%20") + "&year=" + year).getContent(), Search.class);
        if (search != null) {
            try {
                return search.getResults().get(0).getId();
            } catch (IndexOutOfBoundsException e) {
                return 0;
            }
        }
        return 0;
    }


    public MovieJson getMovieInfo(int id) {
        return new Gson().fromJson(new WebHandler("https://api.themoviedb.org/3/movie/" + String.valueOf(id)
                                                  + "?api_key=" + API_KEY + "&language=en-US").getContent(), MovieJson.class);
    }

    public SerieJson getSerieInfo(int id) {
        return new Gson().fromJson(new WebHandler("https://api.themoviedb.org/3/tv/" + String.valueOf(id)
                                                  + "?api_key=" + API_KEY + "&language=en-US").getContent(), SerieJson.class);
    }

}
