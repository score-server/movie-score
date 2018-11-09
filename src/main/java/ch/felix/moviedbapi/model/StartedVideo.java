package ch.felix.moviedbapi.model;

import ch.felix.moviedbapi.data.entity.Time;
import lombok.Data;

@Data
public class StartedVideo {

    private VideoModel videoModel;
    private float progress;
    private Time time;

}
