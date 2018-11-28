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
public class SerieJson {

    @SerializedName("id")
    @Expose
    private Integer id;

    @SerializedName("genres")
    @Expose
    private List<GenreJson> genres = null;

    @SerializedName("name")
    @Expose
    private String name;

    @SerializedName("overview")
    @Expose
    private String overview;

    @SerializedName("popularity")
    @Expose
    private Double popularity;

    @SerializedName("poster_path")
    @Expose
    private String posterPath;

    @SerializedName("vote_average")
    @Expose
    private Double voteAverage;

    @SerializedName("vote_count")
    @Expose
    private Integer voteCount;

    @SerializedName("backdrop_path")
    @Expose
    private String backdropPath;
}