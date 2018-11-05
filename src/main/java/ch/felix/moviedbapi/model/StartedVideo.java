package ch.felix.moviedbapi.model;

import lombok.Data;

@Data
public class StartedVideo {

    private VideoModel videoModel;
    private float progress;

}
