package ch.wetwer.moviedbapi.service;

import ch.wetwer.moviedbapi.data.previewimg.PreviewImage;
import org.junit.Before;
import org.junit.Test;

public class SubtitleGenTest {

    private SubtitleGen subtitleGenUnderTest;

    @Before
    public void setUp() {
        subtitleGenUnderTest = new SubtitleGen(0);
    }

    @Test
    public void testAddThumbnail() {
        // Setup
        final PreviewImage previewImage = null;
        final int frame = 0;

        // Run the test
        subtitleGenUnderTest.addThumbnail(previewImage, frame);

        // Verify the results
    }

    @Test
    public void testSaveFile() {
        // Setup
        final String fileName = "fileName";

        // Run the test
        subtitleGenUnderTest.saveFile(fileName);

        // Verify the results
    }
}
