package ch.felix.moviedbapi.data.repository;

import ch.felix.moviedbapi.data.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {

    User findUserByNameAndAndPasswordSha(String name, String passwordSha);

    User findUserBySessionId(String sessionId);

    User findUserById(Long Id);

    User findUserByName(String name);


}
