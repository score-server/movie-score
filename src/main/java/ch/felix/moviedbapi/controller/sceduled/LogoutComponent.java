package ch.felix.moviedbapi.controller.sceduled;

import ch.felix.moviedbapi.data.entity.User;
import ch.felix.moviedbapi.data.repository.UserRepository;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class LogoutComponent {

    private UserRepository userRepository;

    public LogoutComponent(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Scheduled(fixedRate = 600000)
    public void logoutGuest() {
        User user = userRepository.findUserByName("Guest");
        user.setSessionId("-");
        userRepository.save(user);
    }

}
