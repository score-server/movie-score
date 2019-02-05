package ch.wetwer.moviedbapi.data.uploadFile;

/**
 * @author Wetwer
 * @project movie-db-web
 * @package ch.wetwer.moviedbapi.data.uploadFile
 * @created 01.02.2019
 **/
public enum VideoType {

    MOVIE,
    EPISODE,
    UNDEFINED;

    public static VideoType getType(String type) {
        switch (type) {
            case "movie":
                return MOVIE;
            case "episode":
                return EPISODE;
            default:
                return UNDEFINED;
        }
    }
}
