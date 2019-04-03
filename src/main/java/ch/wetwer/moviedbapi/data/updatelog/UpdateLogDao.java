package ch.wetwer.moviedbapi.data.updatelog;

import ch.wetwer.moviedbapi.data.DaoInterface;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

/**
 * @author Wetwer
 * @project movie-score
 * @package ch.wetwer.moviedbapi.data.dao
 * @created 28.11.2018
 **/
@Service
public class UpdateLogDao implements DaoInterface<UpdateLog> {

    private UpdateLogRepository updateLogRepository;

    public UpdateLogDao(UpdateLogRepository updateLogRepository) {
        this.updateLogRepository = updateLogRepository;
    }

    @Override
    public UpdateLog getById(Long id) {
        return updateLogRepository.getOne(id);
    }

    @Override
    public List<UpdateLog> getAll() {
        return updateLogRepository.findTop3ByOrderByTimestampDesc();
    }

    @Override
    public void save(UpdateLog updateLog) {
        updateLogRepository.save(updateLog);
    }

    public UpdateLog addLog(String type) {
        UpdateLog updateLog = new UpdateLog();
        updateLog.setStatus("Running");
        updateLog.setType(type);
        updateLog.setTimestamp(new Timestamp(new Date().getTime()));
        save(updateLog);
        return updateLog;
    }

    public void completeLog(UpdateLog updateLog) {
        updateLog.setStatus("Complete");
        save(updateLog);
    }
}
