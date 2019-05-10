package ch.wetwer.moviedbapi.service.filehandler;

import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.Assert.assertEquals;
import static org.mockito.MockitoAnnotations.initMocks;

public class PropertiesHandlerTest {

    private PropertiesHandler propertiesHandlerUnderTest;

    @Before
    public void setUp() {
        initMocks(this);
        propertiesHandlerUnderTest = new PropertiesHandler(new File("testFile.properties"));
    }

    @Test
    public void testGetKeys() {
        // Setup
        final ArrayList<String> expectedResult = new ArrayList<>(Arrays.asList("test", "key"));

        // Run the test
        final ArrayList<String> result = propertiesHandlerUnderTest.getKeys();

        // Verify the results
        assertEquals(expectedResult, result);
    }

    @Test
    public void testGetProperty() {
        // Setup
        final String key = "test";
        final String expectedResult = "true";

        // Run the test
        final String result = propertiesHandlerUnderTest.getProperty(key);

        // Verify the results
        assertEquals(expectedResult, result);
    }

    @Test
    public void testSetValue() {
        // Setup
        final String key = "key";
        final String value = "value";

        // Run the test
        propertiesHandlerUnderTest.setValue(key, value);

        // Verify the results
    }
}
