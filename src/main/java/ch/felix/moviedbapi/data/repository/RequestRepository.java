package ch.felix.moviedbapi.data.repository;

import ch.felix.moviedbapi.data.entity.Request;
import ch.felix.moviedbapi.data.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author Wetwer
 * @project movie-db
 */
@Repository
public interface RequestRepository extends JpaRepository<Request, Long> {

    Request findRequestById(Long requestId);

    List<Request> findAllByUser(User user);

    void deleteRequestByUser(User user);

    List<Request> findAllByOrderByActiveDesc();

}