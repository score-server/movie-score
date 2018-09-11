package ch.felix.moviedbapi.controller;

import ch.felix.moviedbapi.data.entity.User;
import ch.felix.moviedbapi.data.repository.UserRepository;
import ch.felix.moviedbapi.service.UserIndicatorService;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

@Controller
@RequestMapping("img")
public class ProfileImgController {

    private UserRepository userRepository;
    private UserIndicatorService userIndicatorService;

    public ProfileImgController(UserRepository userRepository, UserIndicatorService userIndicatorService) {
        this.userRepository = userRepository;
        this.userIndicatorService = userIndicatorService;
    }

    @ResponseBody
    @GetMapping("{userId}")
    public ResponseEntity<ByteArrayResource> getProfileFile(@PathVariable("userId") String userId) {
        ByteArrayResource file = new ByteArrayResource(
                userRepository.findUserById(Long.valueOf(userId)).getProfileImg());
        return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION,
                "attachment; filename=\"" + file.getFilename() + "\"").body(file);
    }


    @PostMapping("{userId}")
    public String uploadProfileImg(@RequestParam("file") MultipartFile file, @PathVariable("userId") String userId,
                                   HttpServletRequest request) throws IOException {
        if (userIndicatorService.isUser(request)) {
            User user = userRepository.findUserById(Long.valueOf(userId));
            user.setProfileImg(file.getBytes());
            userRepository.save(user);
            return "redirect:/user/" + userId + "?profile";
        } else {
            return "redirect:/user/" + userId + "?profileerror";
        }
    }
}