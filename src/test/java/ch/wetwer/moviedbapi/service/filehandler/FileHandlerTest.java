package ch.wetwer.moviedbapi.service.filehandler;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class FileHandlerTest {

    private FileHandler fileHandlerUnderTest;

    @Before
    public void setUp() {
        fileHandlerUnderTest = new FileHandler("testFile.txt");
        fileHandlerUnderTest.clear();
        fileHandlerUnderTest.write("hello world");
    }

    @Test
    public void testSetFilePath() {
        // Setup
        final String path = "testFile.txt";

        // Run the test
        fileHandlerUnderTest.setFilePath(path);

        // Verify the results
    }

    @Test
    public void testGetPath() {
        // Setup
        final String expectedResult = "testFile.txt";

        // Run the test
        final String result = fileHandlerUnderTest.getPath();

        // Verify the results
        assertEquals("D:\\Projects\\privat\\movie-db-api\\testFile.txt", result);
    }

    @Test
    public void testGetRowSize() {
        // Setup
        final int expectedResult = 1;

        // Run the test
        final int result = fileHandlerUnderTest.getRowSize();

        // Verify the results
        assertEquals(expectedResult, result);
    }

    @Test
    public void testGetName() {
        // Setup
        final String expectedResult = "testFile.txt";

        // Run the test
        final String result = fileHandlerUnderTest.getName();

        // Verify the results
        assertEquals(expectedResult, result);
    }

    @Test
    public void testRead() {
        // Setup
        final String expectedResult = "hello world";

        // Run the test
        final String result = fileHandlerUnderTest.read();

        // Verify the results
        assertEquals(expectedResult, result);
    }

    @Test
    public void testRead1() {
        // Setup
        final int row = 1;
        final String expectedResult = "hello world";

        // Run the test
        final String result = fileHandlerUnderTest.read(row);

        // Verify the results
        assertEquals(expectedResult, result);
    }

    @Test
    public void testReadRows() {
        // Setup
        final String[] expectedResult = new String[]{"hello world"};

        // Run the test
        final String[] result = fileHandlerUnderTest.readRows();

        // Verify the results
        assertArrayEquals(expectedResult, result);
    }

    @Test
    public void testWrite() {
        // Setup
        final String text = "text";

        // Run the test
        fileHandlerUnderTest.clear();
        fileHandlerUnderTest.write(text);
        // Verify the results
        assertEquals("text", fileHandlerUnderTest.read());
    }

    @Test
    public void testWrite1() {
        // Setup
        final String text = "text";
        final int row = 0;

        // Run the test
        fileHandlerUnderTest.write(text, row);

        // Verify the results
    }

    @Test
    public void testWrite2() {
        // Setup
        final String[] rows = new String[]{};

        // Run the test
        fileHandlerUnderTest.write(rows);

        // Verify the results
    }

    @Test
    public void testIsFile() {
        // Setup

        // Run the test
        final boolean result = fileHandlerUnderTest.isFile();

        // Verify the results
        assertTrue(result);
    }

    @Test
    public void testGetSize() {
        // Setup
        final long expectedResult = 11L;

        // Run the test
        final long result = fileHandlerUnderTest.getSize();

        // Verify the results
        assertEquals(expectedResult, result);
    }
}
