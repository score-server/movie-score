package ch.wetwer.moviedbapi.controller;

import ch.wetwer.moviedbapi.service.FileSizeService;
import ch.wetwer.moviedbapi.service.SettingsService;
import ch.wetwer.moviedbapi.service.auth.UserAuthService;
import org.apache.commons.io.FileUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.FileAlreadyExistsException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * @author Wetwer
 * @project movie-db-api
 * @package ch.wetwer.moviedbapi.controller
 * @created 27.12.2018
 **/

@Controller
@RequestMapping("upload")
public class UploadController {

    private SettingsService settingsService;
    private UserAuthService userAuthService;

    public UploadController(SettingsService settingsService, UserAuthService userAuthService) {
        this.settingsService = settingsService;
        this.userAuthService = userAuthService;
    }


    @GetMapping
    public String getUploadPage(Model model, HttpServletRequest request) {
        if (userAuthService.isAdministrator(model, request)) {
            File file = new File(settingsService.getKey("moviePath") + "_tmp");
            ArrayList<File> files = new ArrayList<>(Arrays.asList(file.listFiles()));
            model.addAttribute("files", files);
            model.addAttribute("filesize", new FileSizeService());
            model.addAttribute("page", "upload");
            return "template";
        }
        return "redirect:/";
    }

    @PostMapping("movie")
    public String upload(@RequestParam("movie") MultipartFile multipartFile,
                         HttpServletRequest request) throws IOException {
        if (userAuthService.isAdministrator(request)) {
            InputStream fileStream = multipartFile.getInputStream();
            File targetFile = new File(
                    settingsService.getKey("moviePath") + "_tmp/" + multipartFile.getOriginalFilename());
            FileUtils.copyInputStreamToFile(fileStream, targetFile);
            return "redirect:/upload?uploaded";
        }
        return "redirect:/";
    }

    @PostMapping("edit/{hash}")
    public String changeFileName(@PathVariable("hash") int hash, @RequestParam("name") String newName,
                                 HttpServletRequest request) {
        if (userAuthService.isAdministrator(request)) {
            File file = new File(settingsService.getKey("moviePath") + "_tmp");
            for (File fi : file.listFiles()) {
                if (fi.hashCode() == hash) {
                    try {
                        File newFile = new File(fi.getParent(), newName);
                        Files.move(fi.toPath(), newFile.toPath());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        return "redirect:/upload";
    }

    @PostMapping("accept/{hash}")
    public String acceptMovie(@PathVariable("hash") int hash, HttpServletRequest request) {
        if (userAuthService.isAdministrator(request)) {
            File file = new File(settingsService.getKey("moviePath") + "_tmp");
            for (File fi : file.listFiles()) {
                if (fi.hashCode() == hash) {
                    try {
                        File movedFile = new File(settingsService.getKey("moviePath") + "/" + fi.getName());
                        Files.move(fi.toPath(), movedFile.toPath());
                        return "redirect:/upload?added";
                    } catch (FileAlreadyExistsException e) {
                        return "redirect:/upload?exists";
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        return "redirect:/upload";
    }

    @PostMapping("delete/{hash}")
    public String deleteFile(@PathVariable("hash") int hash, HttpServletRequest request) {
        if (userAuthService.isAdministrator(request)) {
            File file = new File(settingsService.getKey("moviePath") + "_tmp");
            for (File fi : file.listFiles()) {
                if (fi.hashCode() == hash) {
                    return "redirect:/upload?deleted=" + fi.delete();
                }
            }
        }
        return "redirect:/upload";
    }
}
