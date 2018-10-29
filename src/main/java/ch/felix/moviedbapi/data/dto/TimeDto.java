package ch.felix.moviedbapi.data.dto;

import ch.felix.moviedbapi.data.entity.Episode;
import ch.felix.moviedbapi.data.entity.Movie;
import ch.felix.moviedbapi.data.entity.Time;
import ch.felix.moviedbapi.data.entity.User;
import ch.felix.moviedbapi.data.repository.TimeRepository;
import ch.felix.moviedbapi.model.StartedMovie;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class TimeDto implements DtoInterface<Time> {

    private TimeRepository timeRepository;

    public TimeDto(TimeRepository timeRepository) {
        this.timeRepository = timeRepository;
    }

    public List<StartedMovie> getStartedMovies(User user) {
        List<StartedMovie> startedMovieList = new ArrayList<>();
        int added = 0;
        for (Time time : timeRepository.findTimesByUserOrderByTimestampDesc(user)) {
            if (time.getMovie() != null) {
                StartedMovie startedMovie = new StartedMovie();
                startedMovie.setMovie(time.getMovie());
                startedMovie.setProgress(getProgress(time.getTime(), time.getMovie().getRuntime()));
                startedMovieList.add(startedMovie);
                added++;
                if (added == 4) {
                    return startedMovieList;
                }
            }
        }

        return startedMovieList;
    }

    private float getProgress(Float time, Integer runtime) {
        return ((time / 60) / runtime) * 100;
    }

    @Override
    public Time getById(Long id) {
        return timeRepository.getOne(id);
    }

    @Override
    public List<Time> getAll() {
        return timeRepository.findAll();
    }

    @Override
    public void save(Time time) {
        timeRepository.save(time);
    }

    public Time getByUserAndMovie(User user, Movie movie) {
        return timeRepository.findTimeByUserAndMovie(user, movie);
    }

    public Time getByUserAndEpisode(User user, Episode episode) {
        return timeRepository.findTimeByUserAndEpisode(user, episode);
    }
}
