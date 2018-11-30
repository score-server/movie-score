package ch.wetwer.moviedbapi.controller;

import ch.wetwer.moviedbapi.data.dao.UserDao;
import ch.wetwer.moviedbapi.data.entity.User;
import ch.wetwer.moviedbapi.service.auth.UserAuthService;
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

    private UserDao userDto;

    private UserAuthService userAuthService;

    public ProfileImgController(UserDao userDto, UserAuthService userAuthService) {
        this.userDto = userDto;
        this.userAuthService = userAuthService;
    }

    @ResponseBody
    @GetMapping("{userId}")
    public ResponseEntity<ByteArrayResource> getProfileFile(@PathVariable("userId") Long userId) {
        ByteArrayResource file = new ByteArrayResource(
                userDto.getById(userId).getProfileImg());
        return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION,
                "attachment; filename=\"" + file.getFilename() + "\"").body(file);
    }


    @PostMapping("{userId}")
    public String uploadProfileImg(@RequestParam("file") MultipartFile file, @PathVariable("userId") Long userId,
                                   HttpServletRequest request) throws IOException {
        if (userAuthService.isUser(request)) {
            User user = userDto.getById(userId);
            user.setProfileImg(file.getBytes());
            userDto.save(user);
            return "redirect:/user/" + userId + "?profile";
        } else {
            return "redirect:/user/" + userId + "?profileerror";
        }
    }
}
