package ch.wetwer.moviedbapi.service;

import ch.wetwer.moviedbapi.data.timeline.Timeline;
import org.junit.Before;
import org.junit.Test;
import org.springframework.ui.Model;

public class ListServiceTest {

    private ListService listServiceUnderTest;

    @Before
    public void setUp() {
        listServiceUnderTest = new ListService();
    }

    @Test
    public void testGetNextListPlace() {
        // Setup
        final Model model = null;
        final Timeline timeLine = null;

        // Run the test
        listServiceUnderTest.getNextListPlace(model, timeLine);

        // Verify the results
    }
}
