package ch.felix.moviedbapi.service.importer;

import ch.felix.moviedbapi.data.entity.Movie;
import ch.felix.moviedbapi.data.repository.MovieRepository;
import ch.felix.moviedbapi.jsonmodel.tmdb.MovieJson;
import ch.felix.moviedbapi.service.ImportLogService;
import ch.felix.moviedbapi.service.SettingsService;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.List;

@Service
public class MovieImportService extends ImportServiceFactory {

    private MovieRepository movieRepository;

    private SettingsService settingsService;
    private SearchMovieService searchMovieService;
    private ImportLogService importLogService;
    private GenreImportService genreImportService;

    public MovieImportService(MovieRepository movieRepository, SettingsService settingsService,
                              SearchMovieService searchMovieService, ImportLogService importLogService, GenreImportService genreImportService) {
        super(settingsService);
        this.movieRepository = movieRepository;
        this.settingsService = settingsService;
        this.searchMovieService = searchMovieService;
        this.importLogService = importLogService;
        this.genreImportService = genreImportService;
    }

    @Override
    public void importAll() {
        startImport();
        File file = new File(settingsService.getKey("moviePath"));
        File[] files = file.listFiles();
        int currentFileIndex = 0;
        int allFiles = files.length;


        for (File movieFile : files) {
            currentFileIndex++;
            setImportProgress(getPercent(currentFileIndex, allFiles));
            if (movieFile.getName().endsWith(".mp4")
                    || movieFile.getName().endsWith(".avi")
                    || movieFile.getName().endsWith(".mkv")) {
                importFile(movieFile);
            }
        }
        stopImport();
    }

    @Override
    public void updateAll() {
        startImport();
        List<Movie> movies = movieRepository.findMoviesByOrderByTitle();
        int currentFileIndex = 0;
        int allFiles = movies.size();

        for (Movie movie : movies) {
            updateFile(new File(movie.getVideoPath()));
            setImportProgress(getPercent(currentFileIndex, allFiles));
            currentFileIndex++;
        }
        stopImport();
    }

    @Override
    public void importFile(File file) {
        String filename = file.getName()
                .replace(".mp4", "")
                .replace(".mkv", "")
                .replace(".avi", "");

        Movie movie = movieRepository.findMovieByTitle(getName(filename));

        if (movie == null) {
            movie = new Movie();
            movie.setTitle(getName(filename));
            movie.setQuality(getQuality(filename));
            movie.setYear(getYear(filename));
            movie.setVideoPath(file.getPath());
            sleep(300);

            int movieId = searchMovieService.findMovieId(movie.getTitle(), movie.getYear());
            MovieJson movieJson = searchMovieService.getMovieInfo(movieId);
            sleep(200);
            movie.setTrailerKey(searchMovieService.getTrailer(movieId));

            try {
                movie.setDescript(movieJson.getOverview());
                movie.setPopularity(movieJson.getPopularity());
                movie.setRuntime(movieJson.getRuntime());
                movie.setCaseImg("https://image.tmdb.org/t/p/original" + movieJson.getPoster_path());
                movie.setBackgroundImg("https://image.tmdb.org/t/p/original" + movieJson.getBackdropPath());
                movie.setVoteAverage(movieJson.getVoteAverage());
                movie.setFiletype(setMimeType(file.getName()));
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
    public void updateFile(File file) {
        String filename = file.getName()
                .replace(".mp4", "")
                .replace(".mkv", "")
                .replace(".avi", "");
        Movie movie = movieRepository.findMovieByTitle(getName(filename));

        Double popularity = movie.getPopularity();

        int movieId = searchMovieService.findMovieId(movie.getTitle(), movie.getYear());
        MovieJson movieJson = searchMovieService.getMovieInfo(movieId);
        sleep(200);
        movie.setPopularity(movieJson.getPopularity());
        movie.setVoteAverage(movieJson.getVoteAverage());
        try {
            movie.setFiletype(setMimeType(file.getName()));
        } catch (Exception e) {
            e.printStackTrace();
        }
        movieRepository.save(movie);

        String popularityIndex = getPopularityChange(movie.getPopularity(), popularity);

        importLogService.importLog(movie, "Updated Movie: " + getName(filename) + " " + popularityIndex);
        sleep(300);
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
