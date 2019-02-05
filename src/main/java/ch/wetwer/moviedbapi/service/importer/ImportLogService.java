package ch.wetwer.moviedbapi.service.importer;

import ch.wetwer.moviedbapi.data.importlog.ImportLog;
import ch.wetwer.moviedbapi.data.importlog.ImportLogRepository;
import ch.wetwer.moviedbapi.data.movie.Movie;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.Date;

/**
 * @author Wetwer
 * @project movie-db
 */

@Service
public class ImportLogService {

    private ImportLogRepository importLogRepository;

    public ImportLogService(ImportLogRepository importLogRepository) {
        this.importLogRepository = importLogRepository;
    }

    public void importLog(Movie movie, String log) {
        ImportLog importLog = new ImportLog();
        importLog.setMovie(movie);
        importLog.setLog(log);
        importLog.setType("ok");
        importLog.setTimestamp(new Timestamp(new Date().getTime()));
        importLogRepository.save(importLog);
    }

    public void importLog(String log) {
        ImportLog importLog = new ImportLog();
        importLog.setLog(log);
        importLog.setType("serie");
        importLog.setTimestamp(new Timestamp(new Date().getTime()));
        importLogRepository.save(importLog);
    }

    public void errorLog(String log) {
        ImportLog importLog = new ImportLog();
        importLog.setLog(log);
        importLog.setType("nok");
        importLog.setTimestamp(new Timestamp(new Date().getTime()));
        importLogRepository.save(importLog);
    }

}
