package ch.felix.moviedbapi.service.importer;

import ch.felix.moviedbapi.service.SettingsService;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Service;

/**
 * @author Felix
 * @date 30.05.2018
 * <p>
 * Project: movie-db-api
 * Package: ch.felix.moviedbapi.service.importer
 **/

@Service
public abstract class ImportService {


    public List<File> filesToUpdate = new ArrayList<>();

    private SettingsService settingsService;

    protected ImportService(SettingsService settingsService) {
        this.settingsService = settingsService;
    }

    public abstract void filterFile(File movieFile);

    public abstract void filterUpdateFile(File movieFile);

    public void importFile(String pathKey) {
        File file = new File(settingsService.getKey(pathKey));
        for (File movieFile : file.listFiles()) {
            if (movieFile.getName().contains(".mp4")) {
                filterFile(movieFile);
            }
        }
        updateFile();
    }

    public void updateFile() {
        for (File movieFile : filesToUpdate) {
            filterUpdateFile(movieFile);
        }
        filesToUpdate = new ArrayList<>();
    }

}
