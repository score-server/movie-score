package ch.felix.moviedbapi.controller;

import ch.felix.moviedbapi.data.entity.Blog;
import ch.felix.moviedbapi.data.repository.BlogRepository;
import ch.felix.moviedbapi.data.repository.UserRepository;
import ch.felix.moviedbapi.service.UserIndicatorService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.sql.Timestamp;
import java.util.Date;

/**
 * @author Wetwer
 * @project movie-db
 */
@Controller
@RequestMapping("blog")
public class BlogController {

    private BlogRepository blogRepository;
    private UserRepository userRepository;

    private UserIndicatorService userIndicatorService;

    public BlogController(BlogRepository blogRepository, UserRepository userRepository, UserIndicatorService userIndicatorService) {
        this.blogRepository = blogRepository;
        this.userRepository = userRepository;
        this.userIndicatorService = userIndicatorService;
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
            Blog blog = new Blog();
            blog.setTitle(title);
            blog.setText(text.replace("\r\n", "<br>"));
            blog.setUser(userRepository.findUserById(Long.valueOf(userId)));
            blog.setTimestamp(new Timestamp(new Date().getTime()));
            blogRepository.save(blog);
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
