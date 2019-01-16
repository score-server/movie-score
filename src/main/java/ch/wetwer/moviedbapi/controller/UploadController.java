package ch.wetwer.moviedbapi.controller;

import ch.wetwer.moviedbapi.data.uploadFile.UploadFile;
import ch.wetwer.moviedbapi.data.uploadFile.UploadFileDao;
import ch.wetwer.moviedbapi.service.FileSizeService;
import ch.wetwer.moviedbapi.service.auth.UserAuthService;
import ch.wetwer.moviedbapi.service.filehandler.SettingsService;
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
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

/**
 * @author Wetwer
 * @project movie-db-api
 * @package ch.wetwer.moviedbapi.controller
 * @created 27.12.2018
 **/

@Controller
@RequestMapping("upload")
public class UploadController {

    private UploadFileDao uploadFileDao;

    private SettingsService settingsService;
    private UserAuthService userAuthService;

    public UploadController(UploadFileDao uploadFileDao, SettingsService settingsService,
                            UserAuthService userAuthService) {
        this.uploadFileDao = uploadFileDao;
        this.settingsService = settingsService;
        this.userAuthService = userAuthService;
    }


    @GetMapping
    public String getUploadPage(Model model, HttpServletRequest request) {
        if (userAuthService.isAdministrator(model, request)) {
            List<UploadFile> uploadFileList = uploadFileDao.getAll();
            model.addAttribute("files", uploadFileList);

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

            InputStream fileStream;
            fileStream = multipartFile.getInputStream();
            File targetFile = new File(settingsService.getKey("moviePath") + "_tmp/"
                    + multipartFile.getOriginalFilename());
            FileUtils.copyInputStreamToFile(fileStream, targetFile);

            UploadFile uploadFile = uploadFileDao.getByHash(targetFile.hashCode());
            try {
                uploadFile.getHash();
            } catch (NullPointerException e) {
                uploadFile = new UploadFile();
            }
            uploadFile.setFilename(targetFile.getName());
            uploadFile.setSize(multipartFile.getSize());
            uploadFile.setTimestamp(new Timestamp(new Date().getTime()));
            uploadFile.setHash(targetFile.hashCode());
            uploadFile.setCompleted(true);
            uploadFile.setVideoType("movie");
            uploadFileDao.save(uploadFile);

            return "redirect:/upload?uploading";
        }
        return "redirect:/";
    }

    @PostMapping("edit/{uploadId}")
    public String changeAttributes(@PathVariable("uploadId") Long uploadId,
                                   @RequestParam("name") String newName,
                                   @RequestParam("videoType") String videoType,
                                   HttpServletRequest request) {
        if (userAuthService.isAdministrator(request)) {
            File uploadDir = new File(settingsService.getKey("moviePath") + "_tmp");

            UploadFile uploadFile = uploadFileDao.getById(uploadId);

            for (File file : uploadDir.listFiles()) {
                if (file.hashCode() == uploadFile.getHash()) {
                    try {
                        File newFile = new File(file.getParent(), newName);
                        Files.move(file.toPath(), newFile.toPath());
                        uploadFile.setFilename(newFile.getName());
                        uploadFile.setVideoType(videoType);
                        uploadFile.setHash(newFile.hashCode());
                        uploadFileDao.save(uploadFile);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
            return "redirect:/upload?name";
        }
        return "redirect:/";
    }

    @PostMapping("accept/{uploadId}")
    public String acceptMovie(@PathVariable("uploadId") Long uploadId, HttpServletRequest request) {
        if (userAuthService.isAdministrator(request)) {
            File file = new File(settingsService.getKey("moviePath") + "_tmp");

            UploadFile uploadFile = uploadFileDao.getById(uploadId);

            for (File fi : file.listFiles()) {
                if (fi.hashCode() == uploadFile.getHash()) {
                    try {

                        if (uploadFile.getVideoType().equals("movie")) {
                            File movedFile = new File(
                                    settingsService.getKey("moviePath") + "/" + fi.getName());
                            Files.move(fi.toPath(), movedFile.toPath());
                            uploadFileDao.delete(uploadFile);
                        } else if (uploadFile.getVideoType().equals("episode")) {
                            File movedFile = new File(
                                    settingsService.getKey("seriePath") + "/" + fi.getName());
                            Files.move(fi.toPath(), movedFile.toPath());
                            uploadFileDao.delete(uploadFile);
                        }
                        return "redirect:/upload?added";
                    } catch (FileAlreadyExistsException e) {
                        return "redirect:/upload?exists";
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        return "redirect:/";
    }

    @PostMapping("delete/{uploadId}")
    public String deleteFile(@PathVariable("uploadId") Long uploadId, HttpServletRequest request) {
        if (userAuthService.isAdministrator(request)) {

            UploadFile uploadFile = uploadFileDao.getById(uploadId);

            File file = new File(settingsService.getKey("moviePath") + "_tmp");
            for (File fi : file.listFiles()) {
                if (fi.hashCode() == uploadFile.getHash()) {
                    if (fi.delete()) {
                        uploadFileDao.delete(uploadFile);
                        return "redirect:/upload?deleted=true";
                    } else {
                        return "redirect:/upload?deleted=false";
                    }
                }
            }
        }
        return "redirect:/";
    }

    @PostMapping("scan")
    public String scan(HttpServletRequest request) {
        if (userAuthService.isAdministrator(request)) {
            scan();
            return "redirect:/upload?scan";
        }
        return "redirect:/";
    }

    private void scan() {
        File[] files = new File(settingsService.getKey("moviePath") + "_tmp").listFiles();

        for (File fi : files) {
            if (!uploadFileDao.exists(fi.hashCode())) {
                UploadFile uploadFile = new UploadFile();
                uploadFile.setFilename(fi.getName());
                uploadFile.setHash(fi.hashCode());
                uploadFile.setCompleted(true);
                uploadFile.setSize(fi.length());
                uploadFile.setVideoType("movie");
                uploadFile.setTimestamp(new Timestamp(new Date().getTime()));
                uploadFileDao.save(uploadFile);
            }
        }

        for (UploadFile uploadFile : uploadFileDao.getAll()) {

            boolean fileExists = false;
            for (File fi : files) {
                if (fi.hashCode() == uploadFile.getHash()) {
                    fileExists = true;
                }
            }

            if (!fileExists) {
                uploadFileDao.delete(uploadFile);
            }
        }
    }
}
