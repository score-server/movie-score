package ch.wetwer.moviedbapi.data.dao;

import ch.wetwer.moviedbapi.data.entity.UpdateLog;
import ch.wetwer.moviedbapi.data.repository.UpdateLogRepository;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

/**
 * @author Wetwer
 * @project movie-db-api
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
        updateLog.setCompleted(false);
        updateLog.setType(type);
        updateLog.setTimestamp(new Timestamp(new Date().getTime()));
        save(updateLog);
        return updateLog;
    }

    public void completeLog(UpdateLog updateLog) {
        updateLog.setCompleted(true);
        save(updateLog);
    }
}
