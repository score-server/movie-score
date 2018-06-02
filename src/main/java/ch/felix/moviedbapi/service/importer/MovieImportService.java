package ch.felix.moviedbapi.service.importer;


import ch.felix.moviedbapi.data.entity.Movie;
import ch.felix.moviedbapi.data.repository.MovieRepository;
import ch.felix.moviedbapi.jsonmodel.MovieJson;
import ch.felix.moviedbapi.service.SettingsService;

import java.io.File;

import org.springframework.stereotype.Service;

@Service
public class MovieImportService extends ImportService {

    private MovieRepository movieRepository;

    private SearchMovieService searchMovieService;

    public MovieImportService(MovieRepository movieRepository, SearchMovieService searchMovieService,
                              SettingsService settingsService) {
        super(settingsService);
        this.movieRepository = movieRepository;
        this.searchMovieService = searchMovieService;
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
        if (movieRepository.findMoviesByTitle(getName(filename)) == null) {
            Movie movie = new Movie();
            movie.setTitle(getName(filename));
            movie.setQuality(getQuality(filename));
            movie.setYear(getYear(filename));
            movie.setVideoPath(movieFile.getPath());
            try {
                Thread.sleep(300);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            MovieJson movieJson = searchMovieService.getMovieInfo(
                    searchMovieService.findMovieId(movie.getTitle(), movie.getYear()));
            try {
                movie.setDescript(movieJson.getOverview());
                movie.setPopularity(movieJson.getPopularity());
                movie.setRuntime(movieJson.getRuntime());
                movie.setCaseImg("https://image.tmdb.org/t/p/w500" + movieJson.getPoster_path());
                movieRepository.save(movie);
                System.out.println("Movieadd - Added " + movie.getTitle());
            } catch (NullPointerException e) {
                System.err.println("Movieadd - Can't add " + getName(filename));
            }
        }
    }
}
