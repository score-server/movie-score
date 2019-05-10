package ch.wetwer.moviedbapi.service.filehandler;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class WebHandlerTest {

    private WebHandler webHandlerUnderTest;

    @Before
    public void setUp() {
        webHandlerUnderTest = new WebHandler("https://jsonplaceholder.typicode.com/todos/1");
    }

    @Test
    public void testGetContent() {
        // Setup
        final String expectedResult = "{\n" +
                "  \"userId\": 1,\n" +
                "  \"id\": 1,\n" +
                "  \"title\": \"delectus aut autem\",\n" +
                "  \"completed\": false\n" +
                "}";

        // Run the test
        final String result = webHandlerUnderTest.getContent();

        // Verify the results
        assertEquals(expectedResult, result);
    }
}
