package ch.felix.moviedbapi.data.dto;

import ch.felix.moviedbapi.data.entity.Time;
import ch.felix.moviedbapi.data.entity.User;
import ch.felix.moviedbapi.data.repository.TimeRepository;
import ch.felix.moviedbapi.model.StartedMovie;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class TimeDto {

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

}
