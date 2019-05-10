package ch.wetwer.moviedbapi.service;

import ch.wetwer.moviedbapi.data.genre.GenreDao;
import ch.wetwer.moviedbapi.data.movie.Movie;
import ch.wetwer.moviedbapi.data.movie.MovieDao;
import ch.wetwer.moviedbapi.data.movie.MovieRepository;
import ch.wetwer.moviedbapi.data.serie.Serie;
import ch.wetwer.moviedbapi.data.serie.SerieRepository;
import ch.wetwer.moviedbapi.data.time.TimeDao;
import ch.wetwer.moviedbapi.data.user.User;
import ch.wetwer.moviedbapi.data.user.UserDao;
import ch.wetwer.moviedbapi.model.StartedVideo;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

public class SearchServiceTest {

    @Mock
    private MovieRepository mockMovieRepository;
    @Mock
    private MovieDao mockMovieDao;
    @Mock
    private TimeDao mockTimeDao;
    @Mock
    private GenreDao mockGenreDao;
    @Mock
    private UserDao mockUserDao;
    @Mock
    private SerieRepository mockSerieRepository;
    @Mock
    private DuplicateService mockDuplicateService;

    private SearchService searchServiceUnderTest;

    @Before
    public void setUp() {
        initMocks(this);
        searchServiceUnderTest = new SearchService(mockMovieRepository, mockMovieDao, mockTimeDao, mockGenreDao, mockUserDao, mockSerieRepository, mockDuplicateService);
    }

    @Test
    public void testSearchMovies() {
        // Setup
        final String search = "search";
        final String orderByParam = "orderByParam";
        final String genreParam = "genreParam";
        final List<Movie> expectedResult = Arrays.asList();
        when(mockMovieRepository.findMoviesByTitleContainingOrderByPopularityDesc("title")).thenReturn(Arrays.asList());
        when(mockMovieRepository.findMoviesByTitleContainingOrderByVoteAverageDesc("title")).thenReturn(Arrays.asList());
        when(mockMovieRepository.findMoviesByTitleContainingOrderByYearDesc("title")).thenReturn(Arrays.asList());
        when(mockMovieDao.searchRecomended("search")).thenReturn(Arrays.asList());
        when(mockMovieRepository.findMoviesByTitleContainingOrderByTimestampDesc("search")).thenReturn(Arrays.asList());
        when(mockMovieRepository.findMoviesByTitleContainingOrderByTitle("title")).thenReturn(Arrays.asList());

        // Run the test
        final List<Movie> result = searchServiceUnderTest.searchMovies(search, orderByParam, genreParam);

        // Verify the results
        assertEquals(expectedResult, result);
    }

    @Test
    public void testSearchMoviesTop() {
        // Setup
        final String search = "search";
        final String orderBy = "orderBy";
        final List<Movie> expectedResult = Arrays.asList();
        when(mockMovieRepository.findTop24ByTitleContainingOrderByPopularityDesc("title")).thenReturn(Arrays.asList());
        when(mockMovieRepository.findTop24ByTitleContainingOrderByVoteAverageDesc("title")).thenReturn(Arrays.asList());
        when(mockMovieRepository.findTop24ByTitleContainingOrderByYearDesc("title")).thenReturn(Arrays.asList());
        when(mockMovieRepository.findTop24ByTitleContainingOrderByTimestampDesc("title")).thenReturn(Arrays.asList());
        when(mockMovieDao.searchRecomended("search")).thenReturn(Arrays.asList());
        when(mockMovieRepository.findTop24ByTitleContainingOrderByTitle("title")).thenReturn(Arrays.asList());

        // Run the test
        final List<Movie> result = searchServiceUnderTest.searchMoviesTop(search, orderBy);

        // Verify the results
        assertEquals(expectedResult, result);
    }

    @Test
    public void testSearchSerie() {
        // Setup
        final String search = "search";
        final String genreParam = "genreParam";
        final List<Serie> expectedResult = Arrays.asList();
        when(mockSerieRepository.findSeriesByTitleContainingOrderByPopularityDesc("search")).thenReturn(Arrays.asList());

        // Run the test
        final List<Serie> result = searchServiceUnderTest.searchSerie(search, genreParam);

        // Verify the results
        assertEquals(expectedResult, result);
    }

    @Test
    public void testFindStartedVideos() {
        // Setup
        final User user = null;
        final List<StartedVideo> expectedResult = Arrays.asList();
        when(mockTimeDao.getStartedMovies(null)).thenReturn(Arrays.asList());

        // Run the test
        final List<StartedVideo> result = searchServiceUnderTest.findStartedVideos(user);

        // Verify the results
        assertEquals(expectedResult, result);
    }

    @Test
    public void testSearchSerieTop() {
        // Setup
        final String search = "search";
        final List<Serie> expectedResult = Arrays.asList();
        when(mockSerieRepository.findTop24ByTitleContainingOrderByPopularityDesc("search")).thenReturn(Arrays.asList());

        // Run the test
        final List<Serie> result = searchServiceUnderTest.searchSerieTop(search);

        // Verify the results
        assertEquals(expectedResult, result);
    }

    @Test
    public void testSearchUser() {
        // Setup
        final String search = "search";
        final List<User> expectedResult = Arrays.asList();
        when(mockUserDao.search("search")).thenReturn(Arrays.asList());

        // Run the test
        final List<User> result = searchServiceUnderTest.searchUser(search);

        // Verify the results
        assertEquals(expectedResult, result);
    }

    @Test
    public void testGetGenres() {
        // Setup
        final GenreSearchType type = GenreSearchType.MOVIE;
        final List<String> expectedResult = Arrays.asList();
        when(mockGenreDao.getAll()).thenReturn(Arrays.asList());
        when(mockGenreDao.getForMovies()).thenReturn(Arrays.asList());
        when(mockGenreDao.getForSeries()).thenReturn(Arrays.asList());
        when(mockDuplicateService.removeStringDuplicates(Arrays.asList())).thenReturn(Arrays.asList());

        // Run the test
        final List<String> result = searchServiceUnderTest.getGenres(type);

        // Verify the results
        assertEquals(expectedResult, result);
    }
}
