package ch.wetwer.moviedbapi;

import org.codehaus.plexus.util.xml.pull.XmlPullParserException;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

import java.io.IOException;

/**
 * @author Wetwer
 * @project movie-db
 */
@EnableAsync
@SpringBootApplication
public class MovieDb {

    public static void main(String[] args) throws IOException, XmlPullParserException {
        SpringApplication.run(MovieDb.class, args);
    }
}

