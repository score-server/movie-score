package ch.wetwer.moviedbapi.service;

import org.springframework.stereotype.Service;

/**
 * @author Wetwer
 * @project movie-score
 * @package ch.wetwer.moviedbapi.service
 * @created 23.02.2020
 **/

@Service
public class FileNameService {

    public String getFileCorrected(String fileName) {
        if (fileName.endsWith(".mp4")) {

            fileName = fileName.replace(".mp4", "[end]");

            String[] urls = {
                    "-[YTS.LT]", "-[YTS.MX]", "-[YTS.AG]", "-[YTS.AM]", ".YIFY",
                    "x264.AAC5.1", "x264.AAC", "x264.BOKUTOX", "x264",
                    ".BluRay.", ".WEBRip.", ".BrRip."
            };

            for (String replacable : urls) {
                fileName = fileName.replace(replacable, "");
            }

            fileName = fileName.replace(".", " ");
            fileName = fileName.replace("[end]", ".mp4");

            return fileName;
        }
        return fileName;
    }
}
