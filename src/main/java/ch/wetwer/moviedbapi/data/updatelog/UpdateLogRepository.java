package ch.wetwer.moviedbapi.data.updatelog;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author Wetwer
 * @project movie-db-api
 * @package ch.wetwer.moviedbapi.data.repository
 * @created 28.11.2018
 **/
@Repository
public interface UpdateLogRepository extends JpaRepository<UpdateLog, Long> {

    List<UpdateLog> findTop3ByOrderByTimestampDesc();

}