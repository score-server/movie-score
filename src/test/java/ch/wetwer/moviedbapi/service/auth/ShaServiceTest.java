package ch.wetwer.moviedbapi.service.auth;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class ShaServiceTest {

    private ShaService shaServiceUnderTest;

    @Before
    public void setUp() {
        shaServiceUnderTest = new ShaService();
    }

    @Test
    public void testEncode() {
        // Setup
        final String s = "TestPassword";
        final String expectedResult = "7bcf9d89298f1bfae16fa02ed6b61908fd2fa8de45dd8e2153a3c47300765328";

        // Run the test
        final String result = shaServiceUnderTest.encode(s);

        // Verify the results
        assertEquals(expectedResult, result);
    }

    @Test
    public void testEncodeShort() {
        // Setup
        final String s = "randomString";
        final String expectedResult = "bb4a64";

        // Run the test
        final String result = shaServiceUnderTest.encodeShort(s);

        // Verify the results
        assertEquals(expectedResult, result);
    }
}
