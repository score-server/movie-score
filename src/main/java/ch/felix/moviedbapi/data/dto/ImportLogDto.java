package ch.felix.moviedbapi.data.dto;

import ch.felix.moviedbapi.data.entity.ImportLog;
import ch.felix.moviedbapi.data.repository.ImportLogRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ImportLogDto implements DtoInterface<ImportLog> {

    private ImportLogRepository importLogRepository;

    public ImportLogDto(ImportLogRepository importLogRepository) {
        this.importLogRepository = importLogRepository;
    }

    @Override
    public ImportLog getById(Long id) {
        return importLogRepository.getOne(id);
    }

    @Override
    public List<ImportLog> getAll() {
        return importLogRepository.findAllByOrderByTimestampDesc();
    }

    @Override
    public void save(ImportLog importLog) {
        importLogRepository.save(importLog);
    }

    public void delete() {
        importLogRepository.deleteAll();
    }
}
