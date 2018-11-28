package ch.felix.moviedbapi.service.importer;

import ch.felix.moviedbapi.model.tmdb.MovieJson;
import ch.felix.moviedbapi.model.tmdb.Search;
import ch.felix.moviedbapi.model.tmdb.SerieJson;
import ch.felix.moviedbapi.model.tmdb.TrailerResult;
import ch.felix.moviedbapi.service.filehandler.WebHandler;
import com.google.gson.Gson;
import org.springframework.stereotype.Service;

/**
 * @author Wetwer
 * @project movie-db
 */
@Service
public class SearchMovieService {

    private final String API_KEY = "c9cb1f249178495875c17631a8040bfa";

    int findMovieId(String movieName, String year) {
        Search search = new Gson().fromJson(
                new WebHandler("https://api.themoviedb.org/3/search/movie?api_key=" + API_KEY + "&query="
                        + movieName.replace(" ", "%20") + "&year=" + year).getContent(), Search.class);
        if (search != null) {
            try {
                return search.getResults().get(0).getId();
            } catch (IndexOutOfBoundsException e) {
                e.printStackTrace();
                return 0;
            }
        }
        return 0;
    }

    int findSeriesId(String seriesName) {
        Search search = new Gson().fromJson(
                new WebHandler("https://api.themoviedb.org/3/search/tv?api_key=" + API_KEY + "&query="
                        + seriesName.replace(" ", "%20")).getContent(), Search.class);
        if (search != null) {
            try {
                return search.getResults().get(0).getId();
            } catch (IndexOutOfBoundsException e) {
                e.printStackTrace();
                return 0;
            }
        }
        return 0;
    }

    int findSeriesId(String seriesName, String year) {
        Search search = new Gson().fromJson(
                new WebHandler("https://api.themoviedb.org/3/search/tv?api_key=" + API_KEY + "&query="
                        + seriesName.replace(" ", "%20") + "&year=" + year).getContent(), Search.class);
        if (search != null) {
            try {
                return search.getResults().get(0).getId();
            } catch (IndexOutOfBoundsException e) {
                e.printStackTrace();
                return 0;
            }
        }
        return 0;
    }

    String getTrailer(int movieId) {
        try {
            return new Gson().fromJson(new WebHandler("https://api.themoviedb.org/3/movie/" + String.valueOf(movieId)
                    + "/videos?api_key=" + API_KEY + "&language=en-US").getContent(), TrailerResult.class)
                    .getTrailers().get(0).getKey();
        } catch (NullPointerException | IndexOutOfBoundsException e) {
            e.printStackTrace();
            return "mvM2eyk1frk";
        }
    }

    MovieJson getMovieInfo(int id) {
        return new Gson().fromJson(new WebHandler("https://api.themoviedb.org/3/movie/" + String.valueOf(id)
                + "?api_key=" + API_KEY + "&language=en-US").getContent(), MovieJson.class);
    }

    SerieJson getSerieInfo(int id) {
        return new Gson().fromJson(new WebHandler("https://api.themoviedb.org/3/tv/" + String.valueOf(id)
                + "?api_key=" + API_KEY + "&language=en-US").getContent(), SerieJson.class);
    }

}
