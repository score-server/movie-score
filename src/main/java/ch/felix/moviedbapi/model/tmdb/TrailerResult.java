package ch.felix.moviedbapi.model.tmdb;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import lombok.Data;

import java.util.List;

/**
 * @author Wetwer
 * @project movie-db
 */
@Data
public class TrailerResult {

    @SerializedName("id")
    @Expose
    private Integer id;

    @SerializedName("results")
    @Expose
    private List<Trailer> trailers = null;
}


