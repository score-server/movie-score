package ch.wetwer.moviedbapi.model;

import ch.wetwer.moviedbapi.data.entity.Time;
import lombok.Data;

@Data
public class StartedVideo {

    private VideoModel videoModel;
    private float progress;
    private Time time;

}
