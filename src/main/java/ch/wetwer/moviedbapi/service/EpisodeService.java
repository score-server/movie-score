package ch.wetwer.moviedbapi.service;

import ch.wetwer.moviedbapi.data.entity.Episode;
import ch.wetwer.moviedbapi.data.entity.Season;
import ch.wetwer.moviedbapi.data.entity.Serie;
import org.springframework.stereotype.Service;

/**
 * @author Wetwer
 * @project movie-db-api
 * @package ch.felix.moviedbapi.service
 * @created 28.11.2018
 **/

@Service
public class EpisodeService {
    public Episode getNextEpisode(Episode episode) {
         Season season = episode.getSeason();
        for (Episode nextEpisode : season.getEpisodes()) {
            if (nextEpisode.getEpisode() == episode.getEpisode() + 1) {
                return nextEpisode;
            }
        }

         Serie serie = season.getSerie();
        for (Season nextSeason : serie.getSeasons()) {
            if (nextSeason.getSeason() == season.getSeason() + 1)
                for (Episode nextEpisode : nextSeason.getEpisodes()) {
                    if (nextEpisode.getEpisode() == 1) {
                        return nextEpisode;
                    }
                }
        }
        return null;
    }
}
