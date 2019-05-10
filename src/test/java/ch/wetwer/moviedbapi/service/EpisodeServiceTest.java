package ch.wetwer.moviedbapi.service;

import ch.wetwer.moviedbapi.data.episode.Episode;
import ch.wetwer.moviedbapi.data.season.Season;
import ch.wetwer.moviedbapi.data.serie.Serie;
import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;
import java.util.Collections;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

public class EpisodeServiceTest {

    private EpisodeService episodeServiceUnderTest;

    @Before
    public void setUp() {
        episodeServiceUnderTest = new EpisodeService();
    }

    @Test
    public void testGetNextEpisode() {
        // Setup

        Episode episode1 = new Episode();
        episode1.setEpisode(1);
        Episode episode2 = new Episode();
        episode2.setEpisode(2);

        Season season = new Season();
        season.setEpisodes(Arrays.asList(episode1, episode2));
        season.setSeason(1);
        episode1.setSeason(season);
        episode2.setSeason(season);

        Serie serie = new Serie();
        serie.setSeasons(Arrays.asList(season));
        season.setSerie(serie);

        // Run the test
        final Episode result = episodeServiceUnderTest.getNextEpisode(episode1);

        // Verify the results
        assertEquals(episode2, result);
    }

    @Test
    public void testNoNextEpisode() {
        // Setup

        Episode episode1 = new Episode();
        episode1.setEpisode(1);

        Season season = new Season();
        season.setEpisodes(Collections.singletonList(episode1));
        season.setSeason(1);
        episode1.setSeason(season);

        Serie serie = new Serie();
        serie.setSeasons(Collections.singletonList(season));
        season.setSerie(serie);

        // Run the test
        final Episode result = episodeServiceUnderTest.getNextEpisode(episode1);

        // Verify the results
        assertNull(result);
    }
}
