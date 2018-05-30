package ch.felix.moviedbapi.service.importer;

import ch.felix.moviedbapi.data.repository.EpisodeRepository;
import ch.felix.moviedbapi.data.repository.SeasonRepository;
import ch.felix.moviedbapi.data.repository.SerieRepository;
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
public class SeriesImportService extends ImportService {

    private SerieRepository serieRepository;
    private SeasonRepository seasonRepository;
    private EpisodeRepository episodeRepository;

    protected SeriesImportService(SettingsService settingsService, SerieRepository serieRepository,
                                  SeasonRepository seasonRepository, EpisodeRepository episodeRepository) {
        super(settingsService);
        this.serieRepository = serieRepository;
        this.seasonRepository = seasonRepository;
        this.episodeRepository = episodeRepository;
    }

    @Override
    public void filterFile(File movieFile) {
        //TODO: Import Algorythm for this
    }
}
