package ch.wetwer.moviedbapi.service;

import ch.wetwer.moviedbapi.data.movie.Movie;
import ch.wetwer.moviedbapi.data.movie.MovieDao;
import ch.wetwer.moviedbapi.data.previewimg.PreviewImage;
import ch.wetwer.moviedbapi.data.previewimg.PreviewImageDao;
import ch.wetwer.moviedbapi.service.filehandler.SettingsService;
import ch.wetwer.moviedbapi.service.importer.ImportLogService;
import org.apache.commons.io.IOUtils;
import org.bytedeco.javacv.FFmpegFrameGrabber;
import org.bytedeco.javacv.Frame;
import org.bytedeco.javacv.Java2DFrameConverter;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * @author Wetwer
 * @project movie-score
 * @package ch.wetwer.moviedbapi.service
 * @created 08.04.2019
 **/

@Async
@Service
public class PreviewCompiler {

    private MovieDao movieDao;
    private PreviewImageDao previewImageDao;

    private SettingsService settingsService;
    private ImportLogService importLogService;

    public PreviewCompiler(MovieDao movieDao, PreviewImageDao previewImageDao, SettingsService settingsService,
                           ImportLogService importLogService) {
        this.movieDao = movieDao;
        this.previewImageDao = previewImageDao;
        this.settingsService = settingsService;
        this.importLogService = importLogService;
    }

    public void savePreview(Movie movie) {
        importLogService.importLog("<i class=\"fas fa-laptop-code\"></i> " +
                "Generating Preview: " + movie.getTitle());

        String previewFileName = settingsService.getKey("preview") + "preview_" + movie.getId() + ".vtt";
        movie.setPreviewPath(previewFileName);
        movieDao.save(movie);

        try {
            Java2DFrameConverter converter = new Java2DFrameConverter();
            FFmpegFrameGrabber frameGrabber = new FFmpegFrameGrabber(movie.getVideoPath());
            frameGrabber.start();
            Frame frame;
            SubtitleGen subtitleGen = new SubtitleGen((int) Math.round(frameGrabber.getFrameRate()));
            for (int currentFrame = 11; currentFrame <= frameGrabber.getLengthInFrames(); currentFrame++) {
                frameGrabber.setFrameNumber(currentFrame);
                frame = frameGrabber.grab();
                BufferedImage bi = converter.convert(frame);
                String path = settingsService.getKey("preview") + movie.getId() + "_" + currentFrame + ".jpg";


                File previewFile = new File(path);
                ImageIO.write(bi, "jpg", previewFile);

                PreviewImage previewImage = new PreviewImage();
                InputStream inputStream = new FileInputStream(previewFile);
                previewImage.setPreviewImg(IOUtils.toByteArray(inputStream));
                previewImageDao.save(previewImage);

                subtitleGen.addThumbnail(previewImage, currentFrame);

                currentFrame += 999;
            }
            frameGrabber.stop();
            subtitleGen.saveFile(previewFileName);

            importLogService.importLog("<i class=\"fas fa-laptop-code\" style=\"color: green;\"></i> " +
                    "Preview complete: " + movie.getTitle());

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
