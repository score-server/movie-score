package ch.felix.moviedbapi.data.dto;

import ch.felix.moviedbapi.data.entity.User;
import ch.felix.moviedbapi.data.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserDto {

    private UserRepository userRepository;

    public UserDto(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User getBySessionId(String sessionId) {
        return userRepository.findUserBySessionId(sessionId);
    }

    public User getByAuthKey(String authkey) {
        return userRepository.findUserByAuthKey(authkey);
    }

    public User getById(Long id) {
        return userRepository.findUserById(id);
    }

    public List<User> search(String search) {
        return userRepository.findUsersByNameContainingOrderByRoleDescNameAsc(search);
    }

    public User login(String name, String password) {
        return userRepository.findUserByNameAndPasswordSha(name, password);
    }

    public User getByIdAndPasswordSha(Long id, String passwordSha) {
       return userRepository.findUserByIdAndPasswordSha(id, passwordSha);
    }


    public void save(User user) {
        userRepository.save(user);
    }

    public List<User> getAll() {
        return userRepository.findAll();
    }
}
