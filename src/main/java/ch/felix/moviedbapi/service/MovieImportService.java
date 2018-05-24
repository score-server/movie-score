package ch.felix.moviedbapi.service;


import ch.felix.moviedbapi.data.entity.Movie;
import ch.felix.moviedbapi.data.repository.MovieRepository;
import ch.felix.moviedbapi.jsonmodel.MovieJson;
import java.io.File;
import org.springframework.stereotype.Service;

@Service
public class MovieImportService {

    private MovieRepository movieRepository;

    private SearchMovieService searchMovieService;
    private SettingsService settingsService;

    public MovieImportService(MovieRepository movieRepository, SearchMovieService searchMovieService,
                              SettingsService settingsService) {
        this.movieRepository = movieRepository;
        this.searchMovieService = searchMovieService;
        this.settingsService = settingsService;
    }


    public void startImport() {
        File file = new File(settingsService.getKey("path"));
        for (File movieFile : file.listFiles()) {
            if (movieFile.getName().contains(".mp4")) {
                String filename = movieFile.getName().replace(".mp4", "");
                if (movieRepository.findMoviesByTitle(getName(filename)) == null) {
                    Movie movie = new Movie();
                    movie.setTitle(getName(filename));
                    movie.setQuality(getQuality(filename));
                    movie.setYear(getYear(filename));
                    movie.setVideoPath(movieFile.getName());
                    try {
                        Thread.sleep(300);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    MovieJson movieJson = searchMovieService.getMovieInfo(
                            searchMovieService.findMovieId(movie.getTitle(), movie.getYear()));
                    movie.setDescript(movieJson.getOverview());
                    movie.setPopularity(movieJson.getPopularity());
                    movie.setRuntime(movieJson.getRuntime());
                    movie.setCaseImg("https://image.tmdb.org/t/p/w500" + movieJson.getPoster_path());
                    System.out.println("Movieadd - Added " + movie.getTitle());
                    movieRepository.save(movie);
                }

            }
        }

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

}
