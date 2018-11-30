package ch.wetwer.moviedbapi.model.tmdb;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import lombok.Data;

/**
 * @author Wetwer
 * @project movie-db
 */
@Data
public class Result {

    @SerializedName("id")
    @Expose
    private Integer id;
}
