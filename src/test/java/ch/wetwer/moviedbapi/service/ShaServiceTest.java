package ch.wetwer.moviedbapi.service;

import ch.wetwer.moviedbapi.service.auth.ShaService;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Random;

/**
 * @author Wetwer
 * @project movie-score
 * @package ch.wetwer.moviedbapi.service
 * @created 08.01.2019
 **/

@SpringBootTest
@RunWith(SpringRunner.class)
public class ShaServiceTest {

    @Autowired
    private ShaService shaService;

    @Test
    public void specificPasswordTest() {
        String encoded = shaService.encode("password");
        Assert.assertEquals("5e884898da28047151d0e56f8dc6292773603d0d6aabbdd62a11ef721d1542d8", encoded);
    }

    @Test
    public void randomPasswordLengthTest() {
        String encoded = shaService.encode(String.valueOf(new Random().nextInt()));
        Assert.assertEquals(64, encoded.length());
    }

    @Test
    public void encodeShortSpecificTest() {
        String encoded = shaService.encodeShort("password");
        Assert.assertEquals("e88489", encoded);
    }

    @Test
    public void encodeShortLengthTest() {
        String encoded = shaService.encodeShort(String.valueOf(new Random().nextInt()));
        Assert.assertEquals(6, encoded.length());
    }

}
