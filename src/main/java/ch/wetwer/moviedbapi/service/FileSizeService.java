package ch.wetwer.moviedbapi.service;

import java.io.File;

/**
 * @author Wetwer
 * @project movie-db-api
 * @package ch.wetwer.moviedbapi.service
 * @created 27.12.2018
 **/
public class FileSizeService {

    public long getFileSize(File file) {
        long fileSizeInBytes = file.length();
        long fileSizeInKB = fileSizeInBytes / 1024;
        return fileSizeInKB / 1024;
    }

}
