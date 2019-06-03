package ch.wetwer.moviedbapi.model;

import lombok.Data;

/**
 * @author Wetwer
 * @project movie-score
 */

@Data
public class VideoModel {

    private Long id;
    private String title;
    private String caseImg;
    private String backgroundImg;
    private Double voteAverage;
    private String quality;
    private String year;
    private String type;

}
