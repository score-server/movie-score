package ch.felix.moviedbapi.controller;

import ch.felix.moviedbapi.data.entity.Blog;
import ch.felix.moviedbapi.data.repository.BlogRepository;
import ch.felix.moviedbapi.data.repository.UserRepository;
import ch.felix.moviedbapi.service.CookieService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.sql.Timestamp;
import java.util.Date;

@Controller
@RequestMapping("blog")
public class BlogController {

    private BlogRepository blogRepository;
    private UserRepository userRepository;

    private CookieService cookieService;

    public BlogController(CookieService cookieService, BlogRepository blogRepository, UserRepository userRepository) {
        this.cookieService = cookieService;
        this.blogRepository = blogRepository;
        this.userRepository = userRepository;
    }

    @GetMapping
    public String getBlogList(Model model, HttpServletRequest request) {
        try {
            model.addAttribute("currentUser", cookieService.getCurrentUser(request));
        } catch (NullPointerException e) {
        }

        model.addAttribute("blogs", blogRepository.findBlogsByOrderByTimestampDesc());
        model.addAttribute("page", "blog");
        return "template";
    }

    @GetMapping("new")
    public String getBlogForm(Model model, HttpServletRequest request) {
        try {
            model.addAttribute("currentUser", cookieService.getCurrentUser(request));
        } catch (NullPointerException e) {
        }

        model.addAttribute("page", "createBlog");
        return "template";
    }


    @PostMapping("new/{userId}")
    public String saveNewPost(@PathVariable("userId") String userId,
                              @RequestParam("title") String title,
                              @RequestParam("text") String text, Model model, HttpServletRequest request) {
        try {
            model.addAttribute("currentUser", cookieService.getCurrentUser(request));
        } catch (NullPointerException e) {
        }

        Blog blog = new Blog();
        blog.setTitle(title);
        blog.setText(text);
        blog.setUser(userRepository.findUserById(Long.valueOf(userId)));
        blog.setTimestamp(new Timestamp(new Date().getTime()));
        blogRepository.save(blog);

        return "redirect:/blog";
    }
}
