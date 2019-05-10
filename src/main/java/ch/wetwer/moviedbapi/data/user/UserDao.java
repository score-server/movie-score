package ch.wetwer.moviedbapi.data.user;

import ch.wetwer.moviedbapi.data.DaoInterface;
import ch.wetwer.moviedbapi.data.session.SessionDao;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author Wetwer
 * @project movie-score
 */

@Service
public class UserDao implements DaoInterface<User> {

    private UserRepository userRepository;
    private SessionDao sessionDao;

    public UserDao(UserRepository userRepository, SessionDao sessionDao) {
        this.userRepository = userRepository;
        this.sessionDao = sessionDao;
    }

    @Override
    public List<User> getAll() {
        return userRepository.findAll();
    }

    @Override
    public void save(User user) {
        userRepository.save(user);
    }

    @Override
    public User getById(Long id) {
        return userRepository.findUserById(id);
    }

    public User getBySessionId(String sessionId) {
        return sessionDao.getBySessionId(sessionId).getUser();
    }

    public User getByAuthKey(String authkey) {
        return userRepository.findUserByAuthKey(authkey);
    }

    public User getByName(String name) {
        return userRepository.findUserByName(name);
    }

    public List<User> search(String search) {
        return userRepository.findUsersByNameContainingOrderByRoleAscNameAsc(search);
    }

    public List<User> searchEqual(String search) {
        return userRepository.findUsersByNameOrderByRoleAscNameAsc(search);
    }

    public User login(String name, String password) {
        return userRepository.findUserByNameAndPasswordSha(name, password);
    }

    public User getByIdAndPasswordSha(Long id, String passwordSha) {
        return userRepository.findUserByIdAndPasswordSha(id, passwordSha);
    }

    public boolean exists(User user) {
        return userRepository.existsById(user.getId());
    }
}
