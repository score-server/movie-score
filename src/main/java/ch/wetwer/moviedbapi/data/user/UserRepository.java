package ch.wetwer.moviedbapi.data.user;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author Wetwer
 * @project movie-score
 */
@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    User findUserByNameAndPasswordSha(String name, String passwordSha);

    User findUserById(Long Id);

    User findUserByIdAndPasswordSha(Long Id, String password);

    User findUserByName(String name);

    List<User> findUsersByNameContainingOrderByRoleAscNameAsc(String name);

    User findUserByAuthKey(String value);
}
