package ch.wetwer.moviedbapi.service;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class ComponentAvalibleServiceTest {

    private ComponentAvalibleService componentAvalibleServiceUnderTest;

    @Before
    public void setUp() {
        componentAvalibleServiceUnderTest = new ComponentAvalibleService();
    }

    @Test
    public void testCheckOnline() {
        // Setup
        final String ip = "80.219.243.8";
        final Integer port = 8084;
        final String expectedResult = "Online";

        // Run the test
        final String result = componentAvalibleServiceUnderTest.checkOnline(ip, port);

        // Verify the results
        assertEquals(expectedResult, result);
    }

    @Test
    public void testCheckOffline() {
        // Setup
        final String ip = "80.219.243.8";
        final Integer port = 8047;
        final String expectedResult = "Offline";

        // Run the test
        final String result = componentAvalibleServiceUnderTest.checkOnline(ip, port);

        // Verify the results
        assertEquals(expectedResult, result);
    }
}
