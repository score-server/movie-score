package ch.felix.moviedbapi.service.importer;

import ch.felix.moviedbapi.data.entity.Movie;
import ch.felix.moviedbapi.data.repository.MovieRepository;
import ch.felix.moviedbapi.jsonmodel.tmdb.MovieJson;
import ch.felix.moviedbapi.service.SettingsService;

import java.io.File;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * @author Wetwer
 * @project movie-db
 */
@Slf4j
@Service
public class MovieImportService extends ImportService {

    private MovieRepository movieRepository;

    private GenreImportService genreImportService;

    public MovieImportService(MovieRepository movieRepository, SettingsService settingsService,
                              GenreImportService genreImportService) {
        super(settingsService);
        this.movieRepository = movieRepository;
        this.genreImportService = genreImportService;
    }

    private String getName(String s) {
        return s.replace(" " + getYear(s) + " " + getQuality(s), "");
    }

    private String getQuality(String s) {
        String[] splits = s.split(" ");
        return splits[splits.length - 1];
    }

    private String getYear(String s) {
        String[] splits = s.split(" ");
        return splits[splits.length - 2];
    }

    @Override
    public void filterFile(File movieFile) {
        String filename = movieFile.getName().replace(".mp4", "");

        Movie movie = movieRepository.findMovieByTitle(getName(filename));

        if (movie == null) {
            movie = new Movie();
            movie.setTitle(getName(filename));
            movie.setQuality(getQuality(filename));
            movie.setYear(getYear(filename));
            movie.setVideoPath(movieFile.getPath());
            try {
                Thread.sleep(300);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            SearchMovieService searchMovieService = new SearchMovieService();
            int movieId = searchMovieService.findMovieId(movie.getTitle(), movie.getYear());
            MovieJson movieJson = searchMovieService.getMovieInfo(movieId);
            try {
                Thread.sleep(200);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            movie.setTrailerKey(searchMovieService.getTrailer(movieId));


            try {
                movie.setDescript(movieJson.getOverview());
                movie.setPopularity(movieJson.getPopularity());
                movie.setRuntime(movieJson.getRuntime());
                movie.setCaseImg("https://image.tmdb.org/t/p/original" + movieJson.getPoster_path());
                movie.setBackgroundImg("https://image.tmdb.org/t/p/original" + movieJson.getBackdropPath());
                movie.setVoteAverage(movieJson.getVoteAverage());
                movieRepository.save(movie);
                genreImportService.setGenre(movie, movieJson.getGenres());
                log.info("Movieadd - Added " + movie.getTitle());
            } catch (NullPointerException e) {
                log.error("Movieadd - Can't add " + getName(filename));
                e.printStackTrace();
            }
        } else {
            super.filesToUpdate.add(movieFile);
        }
    }

    @Override
    public void filterUpdateFile(File movieFile) {
        String filename = movieFile.getName().replace(".mp4", "");
        Movie movie = movieRepository.findMovieByTitle(getName(filename));

        SearchMovieService searchMovieService = new SearchMovieService();
        int movieId = searchMovieService.findMovieId(movie.getTitle(), movie.getYear());
        MovieJson movieJson = searchMovieService.getMovieInfo(movieId);
        try {
            Thread.sleep(200);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        movie.setTrailerKey(searchMovieService.getTrailer(movieId));

        movie.setDescript(movieJson.getOverview());
        movie.setPopularity(movieJson.getPopularity());
        movie.setCaseImg("https://image.tmdb.org/t/p/original" + movieJson.getPoster_path());
        movie.setBackgroundImg("https://image.tmdb.org/t/p/original" + movieJson.getBackdropPath());
        movie.setVoteAverage(movieJson.getVoteAverage());
        movieRepository.save(movie);
        log.info("Updated Movie: " + getName(filename));
        try {
            Thread.sleep(300);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }


    }

}
