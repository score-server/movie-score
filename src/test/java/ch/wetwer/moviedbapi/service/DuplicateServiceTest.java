package ch.wetwer.moviedbapi.service;

import ch.wetwer.moviedbapi.data.movie.Movie;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class DuplicateServiceTest {

    private DuplicateService duplicateServiceUnderTest;

    @Before
    public void setUp() {
        duplicateServiceUnderTest = new DuplicateService();
    }

    @Test
    public void testRemoveStringDuplicates() {
        // Setup

        String[] duplicatesList = {"Ageratum", "Allium", "Poppy", "Catmint", "Allium"};
        List<String> dup = new ArrayList<>(Arrays.asList(duplicatesList));

        final List<String> expectedResult = Arrays.asList("Poppy", "Ageratum", "Catmint", "Allium");

        // Run the test
        final List<String> result = duplicateServiceUnderTest.removeStringDuplicates(dup);

        // Verify the results
        assertEquals(expectedResult, result);
    }

    @Test
    public void testRemoveMovieDuplicates() {
        // Setup

        Movie movie = new Movie();
        movie.setId(2L);

        Movie[] duplicatesList = {movie, movie};
        List<Movie> movies = new ArrayList<>(Arrays.asList(duplicatesList));
        final List<Movie> expectedResult = Arrays.asList(movie);

        // Run the test
        final List<Movie> result = duplicateServiceUnderTest.removeMovieDuplicates(movies);

        // Verify the results
        assertEquals(expectedResult, result);
    }
}
