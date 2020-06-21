package ch.wetwer.moviedbapi.controller;

import ch.wetwer.moviedbapi.data.chat.Chat;
import ch.wetwer.moviedbapi.data.chat.ChatDao;
import ch.wetwer.moviedbapi.service.auth.UserAuthService;
import org.bytedeco.javacpp.presets.opencv_core;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import java.sql.Timestamp;
import java.util.Date;

@Controller
@RequestMapping("chat")
public class ChatController {

    private UserAuthService userAuthService;
    private ChatDao chatDao;

    public ChatController(UserAuthService userAuthService, ChatDao chatDao) {
        this.userAuthService = userAuthService;
        this.chatDao = chatDao;
    }

    @GetMapping
    public String getChat(Model model, HttpServletRequest request) {
        if (userAuthService.isUser(model, request)) {
            userAuthService.log(this.getClass(), request);
            model.addAttribute("chatLog", chatDao.getAll());
            model.addAttribute("page", "chat");
            return "template";
        } else {
            return "redirect:/login?redirect=/chat";
        }
    }

    @PostMapping("send")
    public String postMessage(@Param("message") String message, HttpServletRequest request) {
        if (userAuthService.isUser(request)) {
            userAuthService.log(this.getClass(), request);
            Chat chat = new Chat();
            chat.setUser(userAuthService.getUser(request).getUser());
            chat.setMessage(message);
            chat.setTimestamp(new Timestamp(new Date().getTime()));
            chatDao.save(chat);
            return "redirect:/chat";
        } else {
            return "redirect:/login?redirect=/chat";
        }

    }
}
