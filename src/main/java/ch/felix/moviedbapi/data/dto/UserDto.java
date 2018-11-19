package ch.felix.moviedbapi.data.dto;

import ch.felix.moviedbapi.data.entity.User;
import ch.felix.moviedbapi.data.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserDto implements DtoInterface<User> {

    private UserRepository userRepository;
    private SessionDto sessionDto;

    public UserDto(UserRepository userRepository, SessionDto sessionDto) {
        this.userRepository = userRepository;
        this.sessionDto = sessionDto;
    }

    public User getBySessionId(String sessionId) {
        return sessionDto.getBySessionId(sessionId).getUser();
    }

    public User getByAuthKey(String authkey) {
        return userRepository.findUserByAuthKey(authkey);
    }

    public User getByName(String name) {
        return userRepository.findUserByName(name);
    }

    @Override
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

    @Override
    public void save(User user) {
        userRepository.save(user);
    }

    @Override
    public List<User> getAll() {
        return userRepository.findAll();
    }

    public boolean exists(User user) {
        return userRepository.existsById(user.getId());
    }
}
