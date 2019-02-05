package ch.wetwer.moviedbapi.service;

import ch.wetwer.moviedbapi.data.movie.Movie;
import ch.wetwer.moviedbapi.data.movie.MovieRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

/**
 * @author Wetwer
 * @project movie-score
 * @package ch.wetwer.moviedbapi.service
 * @created 04.02.2019
 **/

@RunWith(MockitoJUnitRunner.class)
public class SimilarMovieServiceTest {


    @Mock
    MovieRepository movieRepository;

    @Mock
    DuplicateService duplicateService;

    private SimilarMovieService similarMovieService;

    @Before
    public void setup() {
        similarMovieService = new SimilarMovieService(movieRepository, duplicateService);
    }


    @Test
    public void getSimilarMoviesByNameTest() {
        when(movieRepository.findMoviesByTitleContainingOrderByYearDesc(any())).thenReturn(getMovieList());
        when(movieRepository.findMoviesByTitleContainingOrderByYearDesc(any())).thenReturn(getMovieList());
        when(duplicateService.removeMovieDuplicates(any())).thenReturn(getMovieList());

        List<Movie> similarMovies = similarMovieService.getSimilarMovies(
                Movie.builder().id(1L).title("hallo").build());
        assertEquals(2, similarMovies.size());
    }

    @NotNull
    private ArrayList<Movie> getMovieList() {
        ArrayList<Movie> movies = new ArrayList<>();
        movies.add(Movie.builder().id(7L).title("hallo").build());
        movies.add(Movie.builder().id(4L).title("hallo test").build());
        movies.add(Movie.builder().id(9L).title("blub").build());

        return movies;
    }

}
