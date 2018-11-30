package ch.wetwer.moviedbapi.controller.reactive;

import ch.wetwer.moviedbapi.service.SettingsService;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("reactive/importstatus")
public class ImportTimeController {


    private SettingsService settingsService;

    public ImportTimeController(SettingsService settingsService) {
        this.settingsService = settingsService;
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public String getImportTime() {
        return settingsService.getKey("importProgress");
    }
}
