package ch.felix.moviedbapi.service.importer;

import ch.felix.moviedbapi.service.SettingsService;
import java.io.File;
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

    private SettingsService settingsService;

    protected ImportService(SettingsService settingsService) {
        this.settingsService = settingsService;
    }

    public abstract void filterFile(File movieFile);

    public void importFile(String pathKey) {
        File file = new File(settingsService.getKey(pathKey));
        for (File movieFile : file.listFiles()) {
            if (movieFile.getName().contains(".mp4")) {
                filterFile(movieFile);
            }
        }

    }


}
