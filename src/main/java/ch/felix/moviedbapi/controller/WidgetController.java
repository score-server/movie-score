package ch.felix.moviedbapi.controller;

import ch.felix.moviedbapi.service.UserAuthService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import java.net.InetSocketAddress;
import java.net.Socket;

@Controller
@RequestMapping("status")
public class WidgetController {

    private UserAuthService userAuthService;

    public WidgetController(UserAuthService userAuthService) {
        this.userAuthService = userAuthService;
    }

    private String checkOnline(String ip, Integer port) {
        String status = "Online";
        try {
            InetSocketAddress sa = new InetSocketAddress(ip, port);
            Socket socket = new Socket();
            socket.connect(sa, 200);
            socket.close();
        } catch (Exception e) {
            status = "Offline";
        }
        return status;
    }

    @GetMapping
    public String getServerStatus(Model model) {
        model.addAttribute("minecraft", checkOnline("scorewinner.ch", 25565));
        model.addAttribute("steam", checkOnline("scorewinner.ch", 7777));
        model.addAttribute("pterodactyl", checkOnline("games.scorewinner.ch", 80));
        model.addAttribute("moviedb", checkOnline("movie.scorewinner.ch", 80));
        model.addAttribute("hermann", checkOnline("scorewinner.ch", 8090));
        return "widget/status.html";
    }

    @GetMapping("user")
    public String getCurrentUser(Model model, HttpServletRequest request) {
        userAuthService.allowGuest(model, request);
        return "widget/user.html";
    }
}
