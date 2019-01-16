package ch.wetwer.moviedbapi.service;

/**
 * @author Wetwer
 * @project movie-db-api
 * @package ch.wetwer.moviedbapi.service
 * @created 27.12.2018
 **/
public class FileSizeService {

    public long getFileSize(Long size) {
        long fileSizeInBytes = size;
        long fileSizeInKB = fileSizeInBytes / 1024;
        return fileSizeInKB / 1024;
    }

}
