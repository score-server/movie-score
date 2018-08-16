package ch.felix.moviedbapi.data.repository;

import ch.felix.moviedbapi.data.entity.ImportLog;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ImportLogRepository extends JpaRepository<ImportLog, Long> {

    List<ImportLog> findAllByOrderByTimestampDesc();

}
