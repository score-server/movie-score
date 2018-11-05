package ch.felix.moviedbapi.data.dto;

import ch.felix.moviedbapi.data.entity.Episode;
import ch.felix.moviedbapi.data.entity.Movie;
import ch.felix.moviedbapi.data.entity.Time;
import ch.felix.moviedbapi.data.entity.User;
import ch.felix.moviedbapi.data.repository.TimeRepository;
import ch.felix.moviedbapi.model.StartedVideo;
import ch.felix.moviedbapi.model.VideoModel;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class TimeDto implements DtoInterface<Time> {

    private TimeRepository timeRepository;
    private EpisodeDto episodeDto;

    public TimeDto(TimeRepository timeRepository, EpisodeDto episodeDto) {
        this.timeRepository = timeRepository;
        this.episodeDto = episodeDto;
    }

    public List<StartedVideo> getStartedMovies(User user) {
        List<StartedVideo> startedVideoList = new ArrayList<>();
        int added = 0;
        for (Time time : timeRepository.findTimesByUserOrderByTimestampDesc(user)) {
            if (time.getMovie() != null) {
                StartedVideo startedVideo = new StartedVideo();
                Movie movie = time.getMovie();
                startedVideo.setVideoModel(getMovieVideoModel(movie));
                startedVideo.setProgress(getProgress(time.getTime(), time.getMovie().getRuntime()));
                startedVideoList.add(startedVideo);
                added++;
                if (added == 4) {
                    return startedVideoList;
                }
            } else if (time.getEpisode() != null) {
                StartedVideo startedVideo = new StartedVideo();
                Episode episode = time.getEpisode();
                boolean serieExists = false;
                for (StartedVideo startedVideoInList : startedVideoList) {
                    if (startedVideoInList.getVideoModel().getType().equals("episode")) {
                        if (episodeDto.getById(startedVideoInList.getVideoModel().getId()).getSeason().getSerie().getId()
                                == episode.getSeason().getSerie().getId()) {
                            serieExists = true;
                        }
                    }
                }
                if (!serieExists) {
                    startedVideo.setVideoModel(getEpisodeVideoModel(episode));
                    startedVideo.setProgress(getProgress(time.getTime(), 45));
                    startedVideoList.add(startedVideo);
                    added++;
                    if (added == 4) {
                        return startedVideoList;
                    }
                }

            }
        }

        return startedVideoList;
    }

    private VideoModel getMovieVideoModel(Movie movie) {
        VideoModel videoModel = new VideoModel();
        videoModel.setId(movie.getId());
        videoModel.setTitle(movie.getTitle());
        videoModel.setCaseImg(movie.getCaseImg());
        videoModel.setVoteAverage(movie.getVoteAverage());
        videoModel.setQuality(movie.getQuality());
        videoModel.setYear(movie.getYear());
        videoModel.setType("movie");
        return videoModel;
    }

    private VideoModel getEpisodeVideoModel(Episode episode) {
        VideoModel videoModel = new VideoModel();
        videoModel.setId(episode.getId());
        videoModel.setTitle(episode.getSeason().getSerie().getTitle() + " S" + episode.getSeason().getSeason()
                + "E" + episode.getEpisode());
        videoModel.setCaseImg(episode.getSeason().getSerie().getCaseImg());
        videoModel.setVoteAverage(episode.getSeason().getSerie().getVoteAverage());
        videoModel.setQuality(episode.getQuality());
        videoModel.setYear(episode.getSeason().getYear());
        videoModel.setType("episode");
        return videoModel;
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
