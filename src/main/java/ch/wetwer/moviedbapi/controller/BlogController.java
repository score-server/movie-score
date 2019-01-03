package ch.wetwer.moviedbapi.controller;

import ch.wetwer.moviedbapi.data.blog.BlogDao;
import ch.wetwer.moviedbapi.data.user.UserDao;
import ch.wetwer.moviedbapi.data.user.User;
import ch.wetwer.moviedbapi.service.ActivityService;
import ch.wetwer.moviedbapi.service.auth.UserAuthService;
import lombok.extern.slf4j.Slf4j;
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
 * @project movie-db
 */

@Slf4j
@Controller
@RequestMapping("blog")
public class BlogController {

    private BlogDao blogDao;
    private UserDao userDto;

    private UserAuthService userAuthService;
    private ActivityService activityService;

    public BlogController(BlogDao blogDao, UserDao userDto, UserAuthService userAuthService,
                          ActivityService activityService) {
        this.blogDao = blogDao;
        this.userDto = userDto;
        this.userAuthService = userAuthService;
        this.activityService = activityService;
    }

    @GetMapping
    public String getBlogList(Model model, HttpServletRequest request) {
        if (userAuthService.isUser(model, request)) {
            userAuthService.log(this.getClass(), request);
            model.addAttribute("blogs", blogDao.getAll());
            model.addAttribute("page", "blog");
            return "template";
        } else {
            return "redirect:/login?redirect=/blog";
        }
    }

    @GetMapping("new")
    public String getBlogForm(Model model, HttpServletRequest request) {
        if (userAuthService.isAdministrator(model, request)) {
            userAuthService.log(this.getClass(), request);
            model.addAttribute("page", "createBlog");
            return "template";
        } else {
            return "redirect:/blog";
        }
    }


    @PostMapping("new/{userId}")
    public String saveNewPost(@PathVariable("userId") Long userId,
                              @RequestParam("title") String title,
                              @RequestParam("text") String text, Model model, HttpServletRequest request) {
        if (userAuthService.isAdministrator(model, request)) {
            userAuthService.log(this.getClass(), request);
            User user = userDto.getById(userId);
            blogDao.createBlog(title, text, user);
            activityService.log(user.getName() + " created new Blog Post", user);
            return "redirect:/blog?new";
        } else {
            return "redirect:/blog";
        }
    }

    @PostMapping("{blogId}/delete")
    public String deleteBlog(@PathVariable("blogId") Long blogId,
                             Model model, HttpServletRequest request) {
        if (userAuthService.isAdministrator(model, request)) {
            blogDao.delete(blogDao.getById(blogId));
            return "redirect:/blog?deleted";
        } else {
            return "redirect:/blog?notdeleted";
        }

    }
}
