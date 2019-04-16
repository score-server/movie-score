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
public class Movie {

    @SerializedName("id")
    @Expose
    private Integer id;

    @SerializedName("imdb_code")
    @Expose
    private String imdbCode;

    @SerializedName("title_english")
    @Expose
    private String titleEnglish;

    @SerializedName("year")
    @Expose
    private Integer year;

    @SerializedName("rating")
    @Expose
    private Double rating;

    @SerializedName("medium_cover_image")
    @Expose
    private String mediumCoverImage;

}
