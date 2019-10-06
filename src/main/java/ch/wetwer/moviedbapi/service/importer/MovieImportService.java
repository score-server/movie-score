package ch.wetwer.moviedbapi.service.importer;

import ch.wetwer.moviedbapi.data.movie.Movie;
import ch.wetwer.moviedbapi.data.movie.MovieDao;
import ch.wetwer.moviedbapi.data.updatelog.UpdateLog;
import ch.wetwer.moviedbapi.data.updatelog.UpdateLogDao;
import ch.wetwer.moviedbapi.model.tmdb.MovieJson;
import ch.wetwer.moviedbapi.service.filehandler.SettingsService;
import org.springframework.stereotype.Service;

import java.io.File;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

/**
 * @author Wetwer
 * @project movie-score
 */

@Service
public class MovieImportService extends ImportServiceFactory {

    private MovieDao movieDto;
    private UpdateLogDao updateLogDao;
    private SettingsService settingsService;
    private SearchMovieService searchMovieService;
    private ImportLogService importLogService;
    private GenreImportService genreImportService;


    public MovieImportService(MovieDao movieDto, SettingsService settingsService, SearchMovieService searchMovieService,
                              ImportLogService importLogService, GenreImportService genreImportService,
                              UpdateLogDao updateLogDao) {
        super(settingsService);
        this.movieDto = movieDto;
        this.settingsService = settingsService;
        this.searchMovieService = searchMovieService;
        this.importLogService = importLogService;
        this.genreImportService = genreImportService;
        this.updateLogDao = updateLogDao;
    }

    @Override
    public void importAll() {
        UpdateLog updateLog = updateLogDao.addLog("MI");
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
        updateLogDao.completeLog(updateLog);
    }

    @Override
    public void updateAll() {
        UpdateLog updateLog = updateLogDao.addLog("MU");
        startImport();
        List<Movie> movies = movieDto.getOrderByTitle();
        int currentFileIndex = 0;
        int allFiles = movies.size();

        for (Movie movie : movies) {
            updateFile(new File(movie.getVideoPath()));
            setImportProgress(getPercent(currentFileIndex, allFiles));
            currentFileIndex++;
        }
        stopImport();
        updateLogDao.completeLog(updateLog);
    }

    @Override
    public void importFile(File file) {
        String filename = file.getName()
                .replace(".mp4", "")
                .replace(".mkv", "")
                .replace(".avi", "");

        Movie movie = movieDto.getByTitle(getName(filename));

        if (movie == null) {
            movie = new Movie();
            movie.setTitle(getName(filename));
            movie.setQuality(getQuality(filename));
            movie.setYear(getYear(filename));
            movie.setVideoPath(file.getPath());
            movie.setRecommended(false);
            movie.setTimestamp(new Timestamp(new Date().getTime()));
            sleep(300);

            movie.setTmdbId(searchMovieService.findMovieId(movie.getTitle(), movie.getYear()));
            MovieJson movieJson = searchMovieService.getMovieInfo(movie.getTmdbId());
            sleep(200);
            movie.setTrailerKey(searchMovieService.getTrailer(movie.getTmdbId()));

            try {
                movie.setDescript(movieJson.getOverview());
                movie.setPopularity(movieJson.getPopularity());
                movie.setRuntime(movieJson.getRuntime());
                movie.setCaseImg("https://image.tmdb.org/t/p/w500" + movieJson.getPoster_path());
                movie.setBackgroundImg("https://image.tmdb.org/t/p/original" + movieJson.getBackdropPath());
                movie.setVoteAverage(movieJson.getVoteAverage());
                movie.setFiletype(setMimeType(file.getName()));
                movieDto.save(movie);
                genreImportService.setGenre(movie, movieJson.getGenres());
                importLogService.importLog(movie, "<i class=\"fas fa-angle-double-down\" style=\"color: green;\"></i>" +
                        " Added Movie " + movie.getTitle());
            } catch (Exception e) {
                e.printStackTrace();
                importLogService.errorLog("<i class=\"fas fa-times\" style=\"color: red;\"></i>" +
                        " Can't add Movie " + filename);
            }
        }
    }

    @Override
    public void updateFile(File file) {
        String filename = file.getName()
                .replace(".mp4", "")
                .replace(".mkv", "")
                .replace(".avi", "");
        Movie movie = movieDto.getByTitle(getName(filename));

        Double popularity = movie.getPopularity();

        if (movie.getTmdbId() == null) {
            movie.setTmdbId(searchMovieService.findMovieId(movie.getTitle(), movie.getYear()));
            sleep(200);
        }
        MovieJson movieJson = searchMovieService.getMovieInfo(movie.getTmdbId());
        sleep(200);
        movie.setPopularity(movieJson.getPopularity());
        movie.setVoteAverage(movieJson.getVoteAverage());
        movie.setCaseImg("https://image.tmdb.org/t/p/w500" + movieJson.getPoster_path());
        movie.setBackgroundImg("https://image.tmdb.org/t/p/original" + movieJson.getBackdropPath());

        try {
            movie.setFiletype(setMimeType(file.getName()));
        } catch (Exception e) {
            e.printStackTrace();
        }
        movieDto.save(movie);

        String popularityIndex = getPopularityChange(movie.getPopularity(), popularity);

        importLogService.importLog(movie, "<i class=\"fas fa-angle-down\" style=\"color: blue;\"></i>" +
                " Updated Movie: " + getName(filename) + " " + popularityIndex);
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
