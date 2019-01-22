package ch.wetwer.moviedbapi.data;

import ch.wetwer.moviedbapi.data.user.User;
import ch.wetwer.moviedbapi.data.user.UserRepository;
import ch.wetwer.moviedbapi.service.auth.ShaService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static junit.framework.TestCase.assertEquals;

@SpringBootTest
@RunWith(SpringRunner.class)
public class UserDataTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ShaService shaService;

    @Test
    public void findUser() {
        User user = new User();
        user.setName("Testmann");
        user.setPasswordSha(shaService.encode("password"));
        entityManager.persist(user);
        entityManager.flush();

        User found = userRepository.findUserByName(user.getName());

        assertEquals(user.getName(), found.getName());
        assertEquals(shaService.encode("password"), found.getPasswordSha());
    }
}
