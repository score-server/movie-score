package ch.wetwer.moviedbapi.controller;


import ch.wetwer.moviedbapi.data.activitylog.ActivityLogDao;
import ch.wetwer.moviedbapi.data.episode.Episode;
import ch.wetwer.moviedbapi.data.episode.EpisodeDao;
import ch.wetwer.moviedbapi.data.importlog.ImportLogDao;
import ch.wetwer.moviedbapi.data.request.RequestDao;
import ch.wetwer.moviedbapi.service.auth.UserAuthService;
import ch.wetwer.moviedbapi.service.filehandler.FileHandler;
import ch.wetwer.moviedbapi.service.filehandler.SettingsService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Wetwer
 * @project movie-score
 */

@Controller
@RequestMapping("settings")
public class ControlCenterSettings {

    private ImportLogDao importLogDao;
    private ActivityLogDao activityLogDao;
    private RequestDao requestDao;
    private EpisodeDao episodeDao;

    private SettingsService settingsService;
    private UserAuthService userAuthService;

    public ControlCenterSettings(SettingsService settingsService, UserAuthService userAuthService,
                                 ImportLogDao importLogDao, ActivityLogDao activityLogDao, RequestDao requestDao,
                                 EpisodeDao episodeDao) {
        this.settingsService = settingsService;
        this.userAuthService = userAuthService;
        this.importLogDao = importLogDao;
        this.activityLogDao = activityLogDao;
        this.requestDao = requestDao;
        this.episodeDao = episodeDao;
    }

    @GetMapping
    private String getControlCenter(Model model, HttpServletRequest request) {
        if (userAuthService.isAdministrator(model, request)) {

            model.addAttribute("moviePath", settingsService.getKey("moviePath"));
            model.addAttribute("seriePath", settingsService.getKey("seriePath"));
            model.addAttribute("previewPath", settingsService.getKey("preview"));
            model.addAttribute("importProgress", settingsService.getKey("importProgress"));
            model.addAttribute("importLogs", importLogDao.getAll());
            model.addAttribute("activityLogs", activityLogDao.getAll());
            model.addAttribute("requests", requestDao.getAll());
            try {
                model.addAttribute("running", settingsService.getKey("import").equals("1"));
            } catch (NullPointerException e) {
                settingsService.setValue("import", "0");
                model.addAttribute("running", settingsService.getKey("import").equals("1"));
            }

            try {
                model.addAttribute("restart", settingsService.getKey("restart"));
            } catch (NullPointerException e) {

            }

            model.addAttribute("page", "controlCenter");
            return "template";
        } else {
            return "redirect:/?access";
        }
    }

    @GetMapping("error")
    public String getErrorLogs(Model model, HttpServletRequest request) {
        if (userAuthService.isAdministrator(model, request)) {
            model.addAttribute("error", new FileHandler("log.log").read());
            model.addAttribute("page", "errorLog");
            return "template";
        } else {
            return "redirect:/?access";
        }
    }

    @PostMapping("clear")
    private String clearImportLogs(HttpServletRequest request) {
        if (userAuthService.isAdministrator(request)) {
            importLogDao.delete();
            return "null";
        } else {
            return "null";
        }

    }

    @PostMapping("clearactivity")
    private String clearActivityLogs(HttpServletRequest request) {
        if (userAuthService.isAdministrator(request)) {
            activityLogDao.delete();
            return "redirect:/settings";
        } else {
            return "redirect:/?access";
        }
    }

    @PostMapping("scedule")
    private String scedule(@RequestParam("time") String time, HttpServletRequest request) {
        if (userAuthService.isAdministrator(request)) {
            settingsService.setValue("restart", time);
            return "redirect:/settings?sceduled";
        } else {
            return "redirect:/?access";
        }
    }

    @PostMapping("scedule/cancel")
    private String scedule(HttpServletRequest request) {
        if (userAuthService.isAdministrator(request)) {
            settingsService.setValue("restart", "0");
            return "redirect:/settings?canceled";
        } else {
            return "redirect:/?access";
        }
    }

    @PostMapping("path/preview")
    public String setPreviewPath(@RequestParam("path") String pathParam, Model model, HttpServletRequest request) {
        if (userAuthService.isAdministrator(model, request)) {
            settingsService.setValue("preview", pathParam);
            return "redirect:/settings";
        } else {
            return "redirect:/";
        }
    }

    @GetMapping("convert")
    public String getConvertProgress(Model model, HttpServletRequest request) {
        if (userAuthService.isAdministrator(model, request)) {
            List<Episode> episodeList = new ArrayList<>();
            for (Episode episode : episodeDao.getOrderByPercentage()) {
                if (episode.getConvertPercentage() != null) {
                    episodeList.add(episode);
                }
            }

            List<Episode> episodesToConvert = new ArrayList<>();
            for (Episode episode : episodeDao.getAll()) {
                if (episode.getConvertPercentage() == null) {
                    if (episode.getMime().equals("video/x-matroska") || episode.getMime().equals("video/x-msvideo")) {
                        episodesToConvert.add(episode);
                    }
                }
            }

            model.addAttribute("episodes", episodeList);
            model.addAttribute("episodesToConvert", episodesToConvert);
            model.addAttribute("page", "convert");
            return "template";
        } else {
            return "redirect:/";
        }
    }
}
