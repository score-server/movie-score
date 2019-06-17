package ch.wetwer.moviedbapi.data.request;

import ch.wetwer.moviedbapi.data.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author Wetwer
 * @project movie-score
 */
@Repository
public interface RequestRepository extends JpaRepository<Request, Long> {

    Request findRequestById(Long requestId);

    List<Request> findAllByUser(User user);

    void deleteRequestByUser(User user);

    List<Request> findAllByOrderByActiveDescRequest();

}
