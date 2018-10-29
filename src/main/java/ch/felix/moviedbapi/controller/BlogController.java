package ch.felix.moviedbapi.controller;

import ch.felix.moviedbapi.data.dto.BlogDto;
import ch.felix.moviedbapi.data.dto.UserDto;
import ch.felix.moviedbapi.data.entity.Blog;
import ch.felix.moviedbapi.data.entity.User;
import ch.felix.moviedbapi.service.ActivityService;
import ch.felix.moviedbapi.service.UserIndicatorService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import java.sql.Timestamp;
import java.util.Date;

/**
 * @author Wetwer
 * @project movie-db
 */

@Slf4j
@Controller
@RequestMapping("blog")
public class BlogController {

    private BlogDto blogDto;
    private UserDto userDto;

    private UserIndicatorService userIndicatorService;
    private ActivityService activityService;

    public BlogController(BlogDto blogDto, UserDto userDto, UserIndicatorService userIndicatorService,
                          ActivityService activityService) {
        this.blogDto = blogDto;
        this.userDto = userDto;
        this.userIndicatorService = userIndicatorService;
        this.activityService = activityService;
    }

    @GetMapping
    public String getBlogList(Model model, HttpServletRequest request) {
        if (userIndicatorService.isUser(model, request)) {
            model.addAttribute("blogs", blogDto.getAll());
            model.addAttribute("page", "blog");
            return "template";
        } else {
            return "redirect:/login?redirect=/blog";
        }
    }

    @GetMapping("new")
    public String getBlogForm(Model model, HttpServletRequest request) {
        if (userIndicatorService.isAdministrator(model, request)) {
            model.addAttribute("page", "createBlog");
            return "template";
        } else {
            return "redirect:/blog";
        }
    }


    @PostMapping("new/{userId}")
    public String saveNewPost(@PathVariable("userId") String userId,
                              @RequestParam("title") String title,
                              @RequestParam("text") String text, Model model, HttpServletRequest request) {
        if (userIndicatorService.isAdministrator(model, request)) {
            User user = userDto.getById(Long.valueOf(userId));

            Blog blog = new Blog();
            blog.setTitle(title);
            blog.setText(text.replace("\r\n", "<br>"));
            blog.setUser(user);
            blog.setTimestamp(new Timestamp(new Date().getTime()));
            blogDto.save(blog);
            activityService.log(user.getName() + " created new Blog Post", user);
            return "redirect:/blog?new";
        } else {
            return "redirect:/blog";
        }
    }

    @PostMapping("{blogId}/delete")
    public String deleteBlog(@PathVariable("blogId") String blogId,
                             Model model, HttpServletRequest request) {
        if (userIndicatorService.isAdministrator(model, request)) {
            blogDto.delete(blogDto.getById(Long.valueOf(blogId)));
            return "redirect:/blog?deleted";
        } else {
            return "redirect:/blog?notdeleted";
        }

    }
}
