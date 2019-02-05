package ch.wetwer.moviedbapi.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.assertEquals;

/**
 * @author Wetwer
 * @project movie-score
 * @package ch.wetwer.moviedbapi.service
 * @created 05.02.2019
 **/

@SpringBootTest
@RunWith(SpringRunner.class)
public class ComponentAvalibleServiceTest {

    @Autowired
    ComponentAvalibleService componentAvalibleService;

    @Test
    public void componentOnlineTest() {
        assertEquals("Online", componentAvalibleService.checkOnline("127.0.0.1", 52058));
    }

    @Test
    public void componentOfflineTest() {
        assertEquals("Offline", componentAvalibleService.checkOnline("192.100.200.300", 21));
    }

}
