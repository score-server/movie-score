package ch.felix.moviedbapi.controller;

import ch.felix.moviedbapi.data.entity.Blog;
import ch.felix.moviedbapi.data.entity.User;
import ch.felix.moviedbapi.data.repository.BlogRepository;
import ch.felix.moviedbapi.data.repository.UserRepository;
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

    private BlogRepository blogRepository;
    private UserRepository userRepository;

    private UserIndicatorService userIndicatorService;
    private ActivityService activityService;

    public BlogController(BlogRepository blogRepository, UserRepository userRepository, UserIndicatorService userIndicatorService, ActivityService activityService) {
        this.blogRepository = blogRepository;
        this.userRepository = userRepository;
        this.userIndicatorService = userIndicatorService;
        this.activityService = activityService;
    }

    @GetMapping
    public String getBlogList(Model model, HttpServletRequest request) {
        userIndicatorService.allowGuest(model, request);

        model.addAttribute("blogs", blogRepository.findBlogsByOrderByTimestampDesc());
        model.addAttribute("page", "blog");
        return "template";
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
            User user = userRepository.findUserById(Long.valueOf(userId));

            Blog blog = new Blog();
            blog.setTitle(title);
            blog.setText(text.replace("\r\n", "<br>"));
            blog.setUser(user);
            blog.setTimestamp(new Timestamp(new Date().getTime()));
            blogRepository.save(blog);
            activityService.log(user.getName() + " created new Blog Post");
            return "redirect:/blog?new";
        } else {
            return "redirect:/blog";
        }
    }

    @PostMapping("{blogId}/delete")
    public String deleteBlog(@PathVariable("blogId") String blogId,
                             Model model, HttpServletRequest request) {
        if (userIndicatorService.isAdministrator(model, request)) {
            blogRepository.delete(blogRepository.findBlogById(Long.valueOf(blogId)));
            return "redirect:/blog?deleted";
        } else {
            return "redirect:/blog?notdeleted";
        }

    }
}
