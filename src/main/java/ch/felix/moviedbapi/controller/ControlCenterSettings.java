package ch.felix.moviedbapi.controller;


import ch.felix.moviedbapi.data.dto.ActivityLogDto;
import ch.felix.moviedbapi.data.dto.ImportLogDto;
import ch.felix.moviedbapi.data.dto.RequestDto;
import ch.felix.moviedbapi.service.SettingsService;
import ch.felix.moviedbapi.service.UserIndicatorService;
import ch.felix.moviedbapi.service.filehandler.FileHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Wetwer
 * @project movie-db
 */
@Controller
@RequestMapping("settings")
public class ControlCenterSettings {

    private ImportLogDto importLogDto;
    private ActivityLogDto activityLogDto;
    private RequestDto requestDto;

    private SettingsService settingsService;
    private UserIndicatorService userIndicatorService;

    public ControlCenterSettings(SettingsService settingsService, UserIndicatorService userIndicatorService,
                                 ImportLogDto importLogDto, ActivityLogDto activityLogDto, RequestDto requestDto) {
        this.settingsService = settingsService;
        this.userIndicatorService = userIndicatorService;
        this.importLogDto = importLogDto;
        this.activityLogDto = activityLogDto;
        this.requestDto = requestDto;
    }

    @GetMapping
    private String getControlCenter(Model model, HttpServletRequest request) {
        if (userIndicatorService.isAdministrator(model, request)) {

            model.addAttribute("moviePath", settingsService.getKey("moviePath"));
            model.addAttribute("seriePath", settingsService.getKey("seriePath"));
            model.addAttribute("importProgress", settingsService.getKey("importProgress"));
            model.addAttribute("importLogs", importLogDto.getAll());
            model.addAttribute("activityLogs", activityLogDto.getAll());
            model.addAttribute("requests", requestDto.getAll());
            try {
                model.addAttribute("running", settingsService.getKey("import").equals("1"));
            } catch (NullPointerException e) {
                settingsService.setValue("import", "0");
                model.addAttribute("running", settingsService.getKey("import").equals("1"));
            }

            model.addAttribute("page", "controlCenter");
            return "template";
        } else {
            return "redirect:/login?redirect=/settings";
        }
    }

    @GetMapping("error")
    public String getErrorLogs(Model model, HttpServletRequest request) {
        if (userIndicatorService.isAdministrator(model, request)) {
            model.addAttribute("error", new FileHandler("log.log").read());
            model.addAttribute("page", "errorLog");
            return "template";
        } else {
            return "redirect:/login?redirect=/settings/error";
        }
    }

    @PostMapping("clear")
    private String clearImportLogs(HttpServletRequest request) {
        if (userIndicatorService.isAdministrator(request)) {
            importLogDto.delete();
            return "redirect:/settings";
        } else {
            return "redirect:/login?redirect=/settings/error";
        }

    }

    @PostMapping("clearactivity")
    private String clearActivityLogs(HttpServletRequest request) {
        if (userIndicatorService.isAdministrator(request)) {
            activityLogDto.delete();
            return "redirect:/settings";
        } else {
            return "redirect:/login?redirect=/settings/error";
        }
    }
}
