package ch.wetwer.moviedbapi.service.sugestion.model.yts;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import lombok.Data;

import java.util.List;

public class MovieResults {

    public List<Movie> getMovies() {
        return movies;
    }

    public void setMovies(List<Movie> movies) {
        this.movies = movies;
    }

    @SerializedName("movies")
    @Expose
    private List<Movie> movies = null;

}
