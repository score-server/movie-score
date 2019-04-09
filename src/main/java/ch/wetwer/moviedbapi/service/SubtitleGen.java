package ch.wetwer.moviedbapi.service;

import ch.wetwer.moviedbapi.data.previewimg.PreviewImage;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.concurrent.TimeUnit;

/**
 * @author Wetwer
 * @project movie-score
 * @package ch.wetwer.moviedbapi.service
 * @created 09.04.2019
 **/
public class SubtitleGen {

    private StringBuilder fullSubtitle;

    private int frameRate;

    public SubtitleGen(int frameRate) {
        this.frameRate = frameRate;
        fullSubtitle = new StringBuilder("WEBVTT\n\n");
    }

    public void addThumbnail(PreviewImage previewImage, int frame) {
        int currentTime = frame / frameRate;
        fullSubtitle.append(getTimeForFrame(currentTime));
        fullSubtitle.append(" --> ");
        fullSubtitle.append(getTimeForFrame(currentTime + 20));
        fullSubtitle.append("\n");
        fullSubtitle.append("/preview/img/").append(previewImage.getId());
        fullSubtitle.append("\n\n");
    }

    private String getTimeForFrame(int seconds) {
        return smallerThanTen(TimeUnit.SECONDS.toHours(seconds)) + ":"
                + smallerThanTen(TimeUnit.SECONDS.toMinutes(seconds)) + ":"
                + smallerThanTen((TimeUnit.SECONDS.toSeconds(seconds) % 60)) + ".000";
    }

    private String smallerThanTen(long time) {
        if (time < 10) {
            return "0" + time;
        } else {
            return String.valueOf(time);
        }
    }

    public StringBuilder getFullSubtitle() {
        return fullSubtitle;
    }

    public void saveFile(String fileName) {
        try {
            Files.write(Paths.get(fileName), fullSubtitle.toString().getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}
