package ch.wetwer.moviedbapi.model.tmdb;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import lombok.Data;

import java.util.List;


/**
 * @author Wetwer
 * @project movie-score
 */
@Data
public class Search {

    @SerializedName("results")
    @Expose
    private List<Result> results = null;
}
