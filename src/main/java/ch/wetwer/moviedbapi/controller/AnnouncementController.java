package ch.wetwer.moviedbapi.controller;

import ch.wetwer.moviedbapi.data.announcement.Announcement;
import ch.wetwer.moviedbapi.data.announcement.AnnouncementDao;
import ch.wetwer.moviedbapi.service.auth.UserAuthService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Wetwer
 * @project movie-score
 * @package ch.wetwer.moviedbapi.controller
 * @created 29.04.2019
 **/

@Controller
@RequestMapping("announcement")
public class AnnouncementController {

    private AnnouncementDao announcementDao;

    private UserAuthService userAuthService;

    public AnnouncementController(AnnouncementDao announcementDao, UserAuthService userAuthService) {
        this.announcementDao = announcementDao;
        this.userAuthService = userAuthService;
    }

    @GetMapping
    public String getTable(Model model, HttpServletRequest request) {
        if (userAuthService.isAdministrator(model, request)) {
            userAuthService.log(this.getClass(), request);
            model.addAttribute("announcements", announcementDao.getAll());
            model.addAttribute("page", "announcement");
            return "template";
        }
        return "redirect:/settings";
    }

    @PostMapping("add")
    public String addAnnouncement(@RequestParam("title") String title,
                                  @RequestParam("imgUrl") String imgUrl,
                                  @RequestParam("url") String url,
                                  HttpServletRequest request) {
        if (userAuthService.isAdministrator(request)) {
            userAuthService.log(this.getClass(), request);
            Announcement announcement = new Announcement();
            announcement.setTitle(title);
            announcement.setImgUrl(imgUrl);
            announcement.setUrl(url);
            announcementDao.save(announcement);
            return "redirect:/announcement";
        }
        return "redirect:/settings";
    }

    @PostMapping("{announcementId}/delete")
    public String remove(@PathVariable("announcementId") Long announcementId,
                         HttpServletRequest request) {
        if (userAuthService.isAdministrator(request)) {
            userAuthService.log(this.getClass(), request);
            announcementDao.delete(announcementDao.getById(announcementId));
            return "redirect:/announcement";
        }
        return "redirect:/settings";
    }

    @PostMapping("{announcementId}/edit")
    public String edit(@PathVariable("announcementId") Long announcementId,
                       @RequestParam("title") String title,
                       @RequestParam("imgUrl") String imgUrl,
                       @RequestParam("url") String url,
                       HttpServletRequest request) {
        if (userAuthService.isAdministrator(request)) {
            userAuthService.log(this.getClass(), request);
            Announcement announcement = announcementDao.getById(announcementId);
            announcement.setTitle(title);
            announcement.setImgUrl(imgUrl);
            announcement.setUrl(url);
            announcementDao.save(announcement);
            return "redirect:/announcement";
        }
        return "redirect:/settings";
    }

    @PostMapping("{announcementId}/color")
    public String setColor(@PathVariable("announcementId") Long announcementId,
                           @RequestParam("colorClass") String colorClass,
                           HttpServletRequest request) {
        if (userAuthService.isAdministrator(request)) {
            userAuthService.log(this.getClass(), request);
            Announcement announcement = announcementDao.getById(announcementId);
            announcement.setColorClass(colorClass);
            announcementDao.save(announcement);
            return "redirect:/announcement";
        }
        return "redirect:/settings";
    }

}
