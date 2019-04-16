package ch.wetwer.moviedbapi.service.sugestion.model.yts;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import lombok.Data;

/**
 * @author Wetwer
 * @project movie-score
 * @package ch.wetwer.moviedbapi.service.sugestion
 * @created 16.04.2019
 **/

@Data
public class Results {

    @SerializedName("data")
    @Expose
    private MovieResults movieResults;

}
