package ch.felix.moviedbapi.controller;

import ch.felix.moviedbapi.data.dto.GroupDto;
import ch.felix.moviedbapi.data.dto.UserDto;
import ch.felix.moviedbapi.data.entity.GroupInvite;
import ch.felix.moviedbapi.data.entity.User;
import ch.felix.moviedbapi.service.UserAuthService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("group")
public class GroupController {

    private GroupDto groupDto;
    private UserDto userDto;

    private UserAuthService userAuthService;

    public GroupController(GroupDto groupDto, UserDto userDto, UserAuthService userAuthService) {
        this.groupDto = groupDto;
        this.userDto = userDto;
        this.userAuthService = userAuthService;
    }

    @GetMapping
    public String getGroupList(HttpServletRequest request, Model model) {
        if (userAuthService.isAdministrator(model, request)) {
            model.addAttribute("groups", groupDto.getAll());
            model.addAttribute("users", userDto.getAll());
            model.addAttribute("page", "groupList");
            return "template";
        }
        return "redirect:/";
    }

    @PostMapping("/delete/{groupId}")
    public String deleteGroup(@PathVariable Long groupId, HttpServletRequest request) {
        if (userAuthService.isAdministrator(request)) {
            GroupInvite group = groupDto.getById(groupId);
            if (group.isActive()) {
                group.setActive(false);
            } else {
                group.setActive(true);
            }
            groupDto.save(group);
            return "redirect:/group?deactivated";
        }
        return "redirect:/";
    }

    @PostMapping("/new")
    public String saveGroup(@RequestParam("name") String name, HttpServletRequest request) {
        if (userAuthService.isAdministrator(request)) {
            GroupInvite groupInvite = new GroupInvite();
            groupInvite.setName(name);
            groupDto.save(groupInvite);
            return "redirect:/group?created";
        }
        return "redirect:/";

    }

    @PostMapping("remove/{userId}")
    public String removeUser(@PathVariable("userId") Long userId, HttpServletRequest request) {
        if (userAuthService.isAdministrator(request)) {
            User user = userDto.getById(userId);
            user.setGroup(null);
            userDto.save(user);
            return "redirect:/group?removed";
        }
        return "redirect:/";

    }

    @PostMapping("{groupId}/add")
    public String addUser(@PathVariable("groupId") Long groupId, @RequestParam("name") String name,
                          HttpServletRequest request) {
        if (userAuthService.isAdministrator(request)) {
            try {
                User user = userDto.getByName(name);
                user.setGroup(groupDto.getById(groupId));
                userDto.save(user);
                return "redirect:/group?added";
            } catch (NullPointerException e) {
                return "redirect:/group?notexist=" + name;
            }
        }
        return "redirect:/";

    }
}
