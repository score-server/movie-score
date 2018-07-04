package ch.felix.moviedbapi.service.importer;

import ch.felix.moviedbapi.data.entity.Movie;
import ch.felix.moviedbapi.data.repository.MovieRepository;
import ch.felix.moviedbapi.jsonmodel.tmdb.MovieJson;
import ch.felix.moviedbapi.service.SettingsService;
import java.io.File;
import org.springframework.stereotype.Service;

@Service
public class MovieImportService extends ImportService {

    private MovieRepository movieRepository;

    private GenreImportService genreImportService;

    public MovieImportService(MovieRepository movieRepository, SearchMovieService searchMovieService,
                              SettingsService settingsService, GenreImportService genreImportService) {
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

        Movie movie = movieRepository.findMoviesByTitle(getName(filename));

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
                movie.setDescript(movieJson.getOverview());
                movie.setPopularity(movieJson.getPopularity());
                movie.setRuntime(movieJson.getRuntime());
                movie.setCaseImg("https://image.tmdb.org/t/p/original" + movieJson.getPoster_path());
                movie.setBackgroundImg("https://image.tmdb.org/t/p/original" + movieJson.getBackdropPath());
                movie.setVoteAverage(movieJson.getVoteAverage());
                movieRepository.save(movie);
                genreImportService.setGenre(movie, movieJson.getGenres());
                System.out.println("Movieadd - Added " + movie.getTitle());
            } catch (NullPointerException e) {
                System.err.println("Movieadd - Can't add " + getName(filename));
            }
        } else {
            super.filesToUpdate.add(movieFile);
        }
    }

    @Override
    public void filterUpdateFile(File movieFile) {
        String filename = movieFile.getName().replace(".mp4", "");
        Movie movie = movieRepository.findMoviesByTitle(getName(filename));

        SearchMovieService searchMovieService = new SearchMovieService();
        int movieId = searchMovieService.findMovieId(movie.getTitle(), movie.getYear());
        MovieJson movieJson = searchMovieService.getMovieInfo(movieId);

        movie.setDescript(movieJson.getOverview());
        movie.setPopularity(movieJson.getPopularity());
        movie.setCaseImg("https://image.tmdb.org/t/p/original" + movieJson.getPoster_path());
        movie.setBackgroundImg("https://image.tmdb.org/t/p/original" + movieJson.getBackdropPath());
        movie.setVoteAverage(movieJson.getVoteAverage());
        movieRepository.save(movie);
        System.out.println("Updated Movie: " + getName(filename));
        try {
            Thread.sleep(300);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }


    }

}
