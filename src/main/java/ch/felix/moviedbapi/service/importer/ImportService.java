package ch.felix.moviedbapi.service.importer;

import ch.felix.moviedbapi.service.SettingsService;

import java.io.File;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

/**
 * @author Wetwer
 * @project movie-db
 */
@Service
public abstract class ImportService {


    public List<File> filesToUpdate = new ArrayList<>();

    private SettingsService settingsService;

    protected ImportService(SettingsService settingsService) {
        this.settingsService = settingsService;
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
            if (movieFile.getName().contains(".mp4")
                    || movieFile.getName().contains(".avi")
                    || movieFile.getName().contains(".mkv")) {
                filterFile(movieFile);
            }
        }
//        updateFile();
        settingsService.setValue("import", "0");
    }

    private float getPercent(int current, int all) {
        return (current * 100.0f) / all;
    }


    public static float round(float d, int decimalPlace) {
        BigDecimal bd = new BigDecimal(Float.toString(d));
        bd = bd.setScale(decimalPlace, BigDecimal.ROUND_HALF_UP);
        return bd.floatValue();
    }


    public void updateFile() {
        for (File movieFile : filesToUpdate) {
            filterUpdateFile(movieFile);
        }
        filesToUpdate = new ArrayList<>();
    }

}
