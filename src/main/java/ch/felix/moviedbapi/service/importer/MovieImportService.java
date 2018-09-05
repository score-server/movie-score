package ch.felix.moviedbapi.service.importer;

import ch.felix.moviedbapi.data.entity.Movie;
import ch.felix.moviedbapi.data.repository.MovieRepository;
import ch.felix.moviedbapi.jsonmodel.tmdb.MovieJson;
import ch.felix.moviedbapi.service.ImportLogService;
import ch.felix.moviedbapi.service.SettingsService;
import org.springframework.stereotype.Service;

import java.io.File;

/**
 * @author Wetwer
 * @project movie-db
 */

@Service
public class MovieImportService extends ImportService {

    private MovieRepository movieRepository;

    private GenreImportService genreImportService;
    private ImportLogService importLogService;
    private SearchMovieService searchMovieService;

    public MovieImportService(MovieRepository movieRepository, SettingsService settingsService,
                              GenreImportService genreImportService, ImportLogService importLogService,
                              SearchMovieService searchMovieService) {
        super(settingsService, movieRepository);
        this.movieRepository = movieRepository;
        this.genreImportService = genreImportService;
        this.importLogService = importLogService;
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
        String filename = movieFile.getName()
                .replace(".mp4", "")
                .replace(".mkv", "")
                .replace(".avi", "");

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
                movie.setFiletype(setMimeType(movieFile.getName()));
                movieRepository.save(movie);
                genreImportService.setGenre(movie, movieJson.getGenres());
                importLogService.importLog(movie, "Added Movie " + movie.getTitle());
            } catch (Exception e) {
                e.printStackTrace();
                importLogService.errorLog("Can't add Movie " + getName(filename) + " | " + filename);
            }
        }
    }

    @Override
    public void filterUpdateFile(File movieFile) {
        String filename = movieFile.getName()
                .replace(".mp4", "")
                .replace(".mkv", "")
                .replace(".avi", "");
        Movie movie = movieRepository.findMovieByTitle(getName(filename));

        Double popularity = movie.getPopularity();

        int movieId = searchMovieService.findMovieId(movie.getTitle(), movie.getYear());
        MovieJson movieJson = searchMovieService.getMovieInfo(movieId);
        try {
            Thread.sleep(200);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        movie.setQuality(getQuality(filename));
        movie.setPopularity(movieJson.getPopularity());
        movie.setCaseImg("https://image.tmdb.org/t/p/original" + movieJson.getPoster_path());
        movie.setBackgroundImg("https://image.tmdb.org/t/p/original" + movieJson.getBackdropPath());
        movie.setVoteAverage(movieJson.getVoteAverage());
        try {
            movie.setFiletype(setMimeType(movieFile.getName()));
        } catch (Exception e) {
            e.printStackTrace();
        }
        movieRepository.save(movie);

        Double popularityChange = Math.round((movie.getPopularity() - popularity) * 100.0) / 100.0;
        String popularityIndex;
        if (popularityChange > 0.0) {
            popularityIndex = "<a style=\"color:green\">+" + popularityChange + "</a>";
        } else if (popularityChange < 0.0) {
            popularityIndex = "<a style=\"color:red\">" + popularityChange + "</a>";
        } else {
            popularityIndex = "";
        }

        importLogService.importLog(movie, "Updated Movie: " + getName(filename) + " " + popularityIndex);
        try {
            Thread.sleep(300);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public String setMimeType(String filename) throws Exception {
        String[] parts = filename.split("\\.");
        String ending = parts[parts.length - 1];
        switch (ending.toLowerCase()) {
            case "mp4":
                return "video/mp4";
            case "avi":
                return "video/avi";
            case "mkv":
                return "video/webm";
        }
        throw new Exception("Filetype not know!!");
    }
}
