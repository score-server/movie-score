package ch.wetwer.moviedbapi.service;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class FileSizeServiceTest {

    private FileSizeService fileSizeServiceUnderTest;

    @Before
    public void setUp() {
        fileSizeServiceUnderTest = new FileSizeService();
    }

    @Test
    public void testGetFileSize() {
        // Setup
        final Long size = 18486349L;
        final long expectedResult = 17L;

        // Run the test
        final long result = fileSizeServiceUnderTest.getFileSize(size);

        System.out.println(expectedResult);
        System.out.println(size);

        // Verify the results
        assertEquals(expectedResult, result);
    }
}
