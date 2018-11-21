package ch.felix.moviedbapi.controller;

import ch.felix.moviedbapi.data.dao.GroupDao;
import ch.felix.moviedbapi.data.dao.UserDao;
import ch.felix.moviedbapi.data.entity.GroupInvite;
import ch.felix.moviedbapi.data.entity.User;
import ch.felix.moviedbapi.service.auth.UserAuthService;
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

@Controller
@RequestMapping("group")
public class GroupController {

    private GroupDao groupDao;
    private UserDao userDao;

    private UserAuthService userAuthService;

    public GroupController(GroupDao groupDao, UserDao userDao, UserAuthService userAuthService) {
        this.groupDao = groupDao;
        this.userDao = userDao;
        this.userAuthService = userAuthService;
    }

    @GetMapping
    public String getGroupList(HttpServletRequest request, Model model) {
        if (userAuthService.isAdministrator(model, request)) {
            model.addAttribute("groups", groupDao.getAll());
            model.addAttribute("users", userDao.getAll());
            model.addAttribute("page", "groupList");
            return "template";
        }
        return "redirect:/";
    }

    @PostMapping("/delete/{groupId}")
    public String deleteGroup(@PathVariable Long groupId, HttpServletRequest request) {
        if (userAuthService.isAdministrator(request)) {
            GroupInvite group = groupDao.getById(groupId);
            if (group.isActive()) {
                group.setActive(false);
            } else {
                group.setActive(true);
            }
            groupDao.save(group);
            return "redirect:/group?deactivated";
        }
        return "redirect:/";
    }

    @PostMapping("/new")
    public String saveGroup(@RequestParam("name") String name, HttpServletRequest request) {
        if (userAuthService.isAdministrator(request)) {
            GroupInvite groupInvite = new GroupInvite();
            groupInvite.setName(name);
            groupDao.save(groupInvite);
            return "redirect:/group?created";
        }
        return "redirect:/";

    }

    @PostMapping("remove/{userId}")
    public String removeUser(@PathVariable("userId") Long userId, HttpServletRequest request) {
        if (userAuthService.isAdministrator(request)) {
            User user = userDao.getById(userId);
            user.setGroup(null);
            userDao.save(user);
            return "redirect:/group?removed";
        }
        return "redirect:/";

    }

    @PostMapping("{groupId}/add")
    public String addUser(@PathVariable("groupId") Long groupId, @RequestParam("name") String name,
                          HttpServletRequest request) {
        if (userAuthService.isAdministrator(request)) {
            try {
                User user = userDao.getByName(name);
                user.setGroup(groupDao.getById(groupId));
                userDao.save(user);
                return "redirect:/group?added";
            } catch (NullPointerException e) {
                return "redirect:/group?notexist=" + name;
            }
        }
        return "redirect:/";

    }
}
