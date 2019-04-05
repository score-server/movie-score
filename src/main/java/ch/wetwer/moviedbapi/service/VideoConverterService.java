package ch.wetwer.moviedbapi.service;

import ch.wetwer.moviedbapi.data.episode.Episode;
import ch.wetwer.moviedbapi.data.episode.EpisodeDao;
import net.bramp.ffmpeg.FFmpeg;
import net.bramp.ffmpeg.FFmpegExecutor;
import net.bramp.ffmpeg.FFprobe;
import net.bramp.ffmpeg.builder.FFmpegBuilder;
import net.bramp.ffmpeg.job.FFmpegJob;
import net.bramp.ffmpeg.probe.FFmpegProbeResult;
import net.bramp.ffmpeg.progress.Progress;
import net.bramp.ffmpeg.progress.ProgressListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

/**
 * @author Wetwer
 * @project movie-score
 * @package ch.wetwer.moviedbapi.service
 * @created 05.04.2019
 **/

@Async
@Service
public class VideoConverterService {

    private EpisodeDao episodeDao;

    public VideoConverterService(EpisodeDao episodeDao) {
        this.episodeDao = episodeDao;
    }

    public void convertEpisodeToMp4(Episode episode) {
        try {
            FFmpeg ffmpeg = new FFmpeg("/usr/bin/ffmpeg");
            FFprobe ffprobe = new FFprobe("/usr/bin/ffprobe");


            FFmpegProbeResult in = ffprobe.probe(episode.getPath());

            String mp4Filename = episode.getPath().replace(".mkv", ".mp4");

            FFmpegBuilder builder = new FFmpegBuilder()
                    .setInput(episode.getPath())
                    .addOutput(mp4Filename)
                    .setAudioChannels(1)         // Mono audio
                    .setAudioCodec("aac")        // using the aac codec
                    .setAudioSampleRate(48_000)  // at 48KHz
                    .setAudioBitRate(32768)      // at 32 kbit/s

                    .setVideoCodec("libx264")     // Video using x264
                    .setVideoFrameRate(24, 1)     // at 24 frames per second

                    .setStrict(FFmpegBuilder.Strict.EXPERIMENTAL) // Allow FFmpeg to use experimental specs
                    .done();

            FFmpegExecutor executor = new FFmpegExecutor(ffmpeg, ffprobe);

            FFmpegJob job = executor.createJob(builder, new ProgressListener() {

                // Using the FFmpegProbeResult determine the duration of the input
                final double duration_ns = in.getFormat().duration * TimeUnit.SECONDS.toNanos(1);

                @Override
                public void progress(Progress progress) {
                    double percentage = progress.out_time_ns / duration_ns;

                    System.out.println(String.format(
                            "[%.0f%%]",
                            percentage * 100));

                    episode.setConvertPercentage(Integer.valueOf(String.format("%.0f", percentage * 100)));
                    episodeDao.save(episode);
                }
            });
            job.run();
            episode.setPath(mp4Filename);
            episode.setConvertPercentage(null);
            episodeDao.save(episode);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
