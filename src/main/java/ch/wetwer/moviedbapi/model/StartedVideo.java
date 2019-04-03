package ch.wetwer.moviedbapi.model;

import ch.wetwer.moviedbapi.data.time.Time;
import lombok.Data;

/**
 * @author Wetwer
 * @project movie-score
 */

@Data
public class StartedVideo {

    private VideoModel videoModel;
    private float progress;
    private Time time;

}
