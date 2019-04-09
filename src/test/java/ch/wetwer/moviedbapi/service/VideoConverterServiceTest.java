package ch.wetwer.moviedbapi.service;

import ch.wetwer.moviedbapi.data.episode.Episode;
import ch.wetwer.moviedbapi.data.episode.EpisodeDao;
import ch.wetwer.moviedbapi.service.importer.ImportLogService;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

import static org.mockito.MockitoAnnotations.initMocks;

public class VideoConverterServiceTest {

    @Mock
    private EpisodeDao mockEpisodeDao;

    @Mock
    private ImportLogService mockImportLogService;

    private VideoConverterService videoConverterServiceUnderTest;


    @Before
    public void setUp() {
        initMocks(this);
        videoConverterServiceUnderTest = new VideoConverterService(mockEpisodeDao, mockImportLogService);
    }

    @Test
    public void testConvertEpisodeToMp4() {
        // Setup
        final Episode episode = null;

        // Run the test
        videoConverterServiceUnderTest.convertEpisodeToMp4(episode);

        // Verify the results
    }
}
