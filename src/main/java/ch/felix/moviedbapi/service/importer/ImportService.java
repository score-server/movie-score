package ch.felix.moviedbapi.service.importer;

import ch.felix.moviedbapi.data.entity.Movie;
import ch.felix.moviedbapi.data.repository.MovieRepository;
import ch.felix.moviedbapi.service.SettingsService;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.io.File;
import java.math.BigDecimal;
import java.util.List;

/**
 * @author Wetwer
 * @project movie-db
 */

@Service
public abstract class ImportService {


    private SettingsService settingsService;
    private MovieRepository movieRepository;

    protected ImportService(SettingsService settingsService, MovieRepository movieRepository) {
        this.settingsService = settingsService;
        this.movieRepository = movieRepository;
    }

    public abstract void filterFile(File movieFile);

    public abstract void filterUpdateFile(File movieFile);

    @Async
    public void importFile(String pathKey) {
        File file = new File(settingsService.getKey(pathKey));

        File[] files = file.listFiles();
        int currentFileIndex = 0;
        int allFiles = files.length;


        for (File movieFile : files) {
            currentFileIndex++;
            settingsService.setValue("importProgress",
                    String.valueOf(round(getPercent(currentFileIndex, allFiles), 1)));
            if (movieFile.getName().endsWith(".mp4")
                    || movieFile.getName().endsWith(".avi")
                    || movieFile.getName().endsWith(".mkv")) {
                filterFile(movieFile);
            }
        }
//        updateFile();
        settingsService.setValue("import", "0");
    }

    public float getPercent(int current, int all) {
        return (current * 100.0f) / all;
    }


    public static float round(float d, int decimalPlace) {
        BigDecimal bd = new BigDecimal(Float.toString(d));
        bd = bd.setScale(decimalPlace, BigDecimal.ROUND_HALF_UP);
        return bd.floatValue();
    }


    public void updateFile() {
        List<Movie> movies = movieRepository.findAll();
        int currentFileIndex = 0;
        int allFiles = movies.size();

        for (Movie movie : movies) {
            filterUpdateFile(new File(movie.getVideoPath()));
            settingsService.setValue("importProgress",
                    String.valueOf(round(getPercent(currentFileIndex, allFiles), 1)));
            currentFileIndex++;
        }
        settingsService.setValue("import", "0");
        settingsService.setValue("importProgress", "0");
    }
}
