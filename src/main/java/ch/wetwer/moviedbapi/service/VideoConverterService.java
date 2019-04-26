package ch.wetwer.moviedbapi.service;

import ch.wetwer.moviedbapi.data.episode.Episode;
import ch.wetwer.moviedbapi.data.episode.EpisodeDao;
import ch.wetwer.moviedbapi.service.importer.ImportLogService;
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

    private ImportLogService importLogService;

    public VideoConverterService(EpisodeDao episodeDao, ImportLogService importLogService) {
        this.episodeDao = episodeDao;
        this.importLogService = importLogService;
    }

    public void convertEpisodeToMp4(Episode episode) {
        importLogService.importLog("<i class=\"fas fa-hammer\"></i> " +
                "Startet Converting: " + episode.getFullTitle());

        try {
            FFmpeg ffmpeg = new FFmpeg("/usr/bin/ffmpeg");
//            FFmpeg ffmpeg = new FFmpeg("C:\\ProgramData\\chocolatey\\bin\\ffmpeg.exe");
            FFprobe ffprobe = new FFprobe("/usr/bin/ffprobe");
//            FFprobe ffprobe = new FFprobe("C:\\ProgramData\\chocolatey\\bin\\ffprobe.exe");


            FFmpegProbeResult in = ffprobe.probe(episode.getPath());

            String mp4Filename = episode.getPath()
                    .replace(".mkv", ".mp4")
                    .replace(".avi", ".mp4");

            FFmpegBuilder builder = new FFmpegBuilder()
                    .setInput(episode.getPath())
                    .addOutput(mp4Filename)
                    .setAudioChannels(1)
                    .setAudioCodec("aac")
                    .setAudioSampleRate(48_000)
                    .setAudioBitRate(32768)

                    .setVideoCodec("libx264")
                    .setVideoFrameRate(24, 1)

                    .setStrict(FFmpegBuilder.Strict.EXPERIMENTAL)
                    .done();

            FFmpegExecutor executor = new FFmpegExecutor(ffmpeg, ffprobe);

            FFmpegJob job = executor.createJob(builder, new ProgressListener() {

                final double duration_ns = in.getFormat().duration * TimeUnit.SECONDS.toNanos(1);

                @Override
                public void progress(Progress progress) {
                    double percentage = progress.out_time_ns / duration_ns;
                    episode.setConvertPercentage(Integer.valueOf(String.format("%.0f", percentage * 100)));
                    episodeDao.save(episode);
                }
            });
            job.run();
            episode.setPath(mp4Filename);
            episode.setConvertPercentage(null);
            episodeDao.save(episode);
            importLogService.importLog("<i class=\"fas fa-hammer\" style=\"color: green;\"></i> " +
                    "Converting complete: " + episode.getFullTitle());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
