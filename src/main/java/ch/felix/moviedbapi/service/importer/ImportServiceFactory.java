package ch.felix.moviedbapi.service.importer;

import ch.felix.moviedbapi.service.SettingsService;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.io.File;
import java.math.BigDecimal;
import java.util.concurrent.CompletableFuture;

@Async
@Service
public abstract class ImportServiceFactory {

    private SettingsService settingsService;

    protected ImportServiceFactory(SettingsService settingsService) {
        this.settingsService = settingsService;
    }

    public abstract void importAll();

    public abstract void updateAll();

    public abstract void importFile(File file);

    public abstract void updateFile(File file);

    public float getPercent(int current, int all) {
        float percent = (current * 100.0f) / all;
        return round(percent, 1);
    }

    public static float round(float d, int decimalPlace) {
        BigDecimal bd = new BigDecimal(Float.toString(d));
        bd = bd.setScale(decimalPlace, BigDecimal.ROUND_HALF_UP);
        return bd.floatValue();
    }

    public void sleep(int millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public String setMimeType(String filename) throws Exception {
        String[] parts = filename.split("\\.");
        String ending = parts[parts.length - 1];
        switch (ending.toLowerCase()) {
            case "mp4":
                return "videoModel/mp4";
            case "avi":
                return "videoModel/avi";
            case "mkv":
                return "videoModel/webm";
        }
        throw new Exception("Filetype not know!!");
    }

    public String getPopularityChange(Double newPopularity, Double oldPopularity) {
        Double popularityChange = Math.round((oldPopularity - newPopularity) * 100.0) / 100.0;
        String popularityIndex;
        if (popularityChange > 0.0) {
            popularityIndex = "<span class=\"badge badge-primary\">" + newPopularity + "</span> " +
                    "<a style=\"color:green\">+" + popularityChange + "</a>";
        } else if (popularityChange < 0.0) {
            popularityIndex = "<span class=\"badge badge-primary\">" + newPopularity + "</span>" +
                    " <a style=\"color:red\">" + popularityChange + "</a>";
        } else {
            popularityIndex = "";
        }
        return popularityIndex;
    }

    public void setImportStatus(String status) {
        settingsService.setValue("import", status);
        settingsService.setValue("importProgress", "0");
    }

    public void setImportProgress(Float progress) {
        settingsService.setValue("importProgress", String.valueOf(progress));
    }

    public void startImport() {
        setImportProgress(0F);
        setImportStatus("1");
    }

    public void stopImport() {
        setImportProgress(0F);
        setImportStatus("0");
    }

}
