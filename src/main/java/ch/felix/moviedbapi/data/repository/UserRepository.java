package ch.felix.moviedbapi.data.repository;

import ch.felix.moviedbapi.data.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author Wetwer
 * @project movie-db
 */
@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    User findUserByNameAndPasswordSha(String name, String passwordSha);

    User findUserBySessionId(String sessionId);

    User findUserById(Long Id);

    User findUserByIdAndPasswordSha(Long Id, String password);

    User findUserByName(String name);

    List<User> findUsersByNameContainingOrderByRoleDescNameAsc(String name);

    User findUserByAuthKey(String value);
}
