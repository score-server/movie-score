package ch.felix.moviedbapi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * @author Wetwer
 * @project movie-db
 */
@EnableScheduling
@EnableAsync
@SpringBootApplication
public class MovieDbApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(MovieDbApiApplication.class, args);
    }
}

