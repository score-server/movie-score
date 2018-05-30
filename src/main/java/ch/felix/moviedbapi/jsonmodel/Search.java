package ch.felix.moviedbapi.jsonmodel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import java.util.List;

/**
 * @author Felix
 * @date 11.05.2018
 * <p>
 * Project: tmdbApi
 * Package: model
 **/
public class Search {

    @SerializedName("results")
    @Expose
    private List<Result> results = null;

    public List<Result> getResults() {
        return results;
    }

    public void setResults(List<Result> results) {
        this.results = results;
    }

}
