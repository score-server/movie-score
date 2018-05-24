package ch.felix.moviedbapi.data.repository;

import ch.felix.moviedbapi.data.entity.Request;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RequestRepository extends JpaRepository<Request, Long> {

    List<Request> findRequestsByActiveContaining(String active);

    Request findRequestById(Long requestId);

    List<Request> findRequestsByUserFk(Long userId);
}
