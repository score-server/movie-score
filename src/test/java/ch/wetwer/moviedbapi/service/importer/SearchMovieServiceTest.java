package ch.wetwer.moviedbapi.service.importer;

import ch.wetwer.moviedbapi.model.tmdb.MovieJson;
import ch.wetwer.moviedbapi.model.tmdb.SerieJson;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class SearchMovieServiceTest {

    private SearchMovieService searchMovieServiceUnderTest;

    @Before
    public void setUp() {
        searchMovieServiceUnderTest = new SearchMovieService();
    }

    @Test
    public void testFindMovieId() {
        // Setup
        final String movieName = "Avengers Endgame";
        final String year = "2019";
        final int expectedResult = 299534;

        // Run the test
        final int result = searchMovieServiceUnderTest.findMovieId(movieName, year);

        // Verify the results
        assertEquals(expectedResult, result);
    }

    @Test
    public void testFindSeriesId() {
        // Setup
        final String seriesName = "Game of Thrones";
        final int expectedResult = 1399;

        // Run the test
        final int result = searchMovieServiceUnderTest.findSeriesId(seriesName);

        // Verify the results
        assertEquals(expectedResult, result);
    }

    @Test
    public void testFindSeriesId1() {
        // Setup
        final String seriesName = "Breaking Bad";
        final String year = "2015";
        final int expectedResult = 1396;

        // Run the test
        final int result = searchMovieServiceUnderTest.findSeriesId(seriesName, year);

        // Verify the results
        assertEquals(expectedResult, result);
    }

    @Test
    public void testGetTrailer() {
        // Setup
        final int movieId = 299534;
        final String expectedResult = "hA6hldpSTF8";

        // Run the test
        final String result = searchMovieServiceUnderTest.getTrailer(movieId);

        // Verify the results
        assertEquals(expectedResult, result);
    }

    @Test
    public void testGetMovieInfo() {
        // Setup
        final int id = 299534;

        // Run the test
        final MovieJson result = searchMovieServiceUnderTest.getMovieInfo(id);

        // Verify the results
        assertEquals("Avengers: Endgame", result.getTitle());
    }

    @Test
    public void testGetSerieInfo() {
        // Setup
        final int id = 1396;

        // Run the test
        final SerieJson result = searchMovieServiceUnderTest.getSerieInfo(id);

        // Verify the results
        assertEquals("Breaking Bad", result.getName());
    }
}
