package ch.wetwer.moviedbapi.service;

import ch.wetwer.moviedbapi.data.movie.Movie;
import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class PageServiceTest {

    private PageService pageServiceUnderTest;

    @Before
    public void setUp() {
        pageServiceUnderTest = new PageService();
    }

    @Test
    public void testGetPage() {
        // Setup
        final List<Movie> movies = Arrays.asList();
        final Integer page = 0;
        final List<Movie> expectedResult = Arrays.asList();

        // Run the test
        final List<Movie> result = pageServiceUnderTest.getPage(movies, page);

        // Verify the results
        assertEquals(expectedResult, result);
    }
}
