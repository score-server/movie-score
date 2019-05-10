package ch.wetwer.moviedbapi.service;

import ch.wetwer.moviedbapi.data.movie.Movie;
import ch.wetwer.moviedbapi.data.movie.MovieDao;
import ch.wetwer.moviedbapi.data.previewimg.PreviewImageDao;
import ch.wetwer.moviedbapi.service.filehandler.SettingsService;
import ch.wetwer.moviedbapi.service.importer.ImportLogService;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

public class PreviewCompilerTest {

    @Mock
    private MovieDao mockMovieDao;
    @Mock
    private PreviewImageDao mockPreviewImageDao;
    @Mock
    private SettingsService mockSettingsService;
    @Mock
    private ImportLogService mockImportLogService;

    private PreviewCompiler previewCompilerUnderTest;

    @Before
    public void setUp() {
        initMocks(this);
        previewCompilerUnderTest = new PreviewCompiler(mockMovieDao, mockPreviewImageDao, mockSettingsService, mockImportLogService);
    }

    @Test(expected = NullPointerException.class)
    public void testSavePreview() {
        // Setup
        final Movie movie = null;
        when(mockSettingsService.getKey("key")).thenReturn("result");

        // Run the test
        previewCompilerUnderTest.savePreview(movie);

        // Verify the results
        verify(mockImportLogService).importLog("log");
        verify(mockMovieDao).save(null);
        verify(mockPreviewImageDao).save(null);
    }
}
