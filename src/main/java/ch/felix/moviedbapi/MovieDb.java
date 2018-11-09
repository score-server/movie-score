package ch.felix.moviedbapi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * @author Wetwer
 * @project movie-db
 */
@EnableAsync
@SpringBootApplication
public class MovieDb {

    public static void main(String[] args) {
        SpringApplication.run(MovieDb.class, args);
    }
}

