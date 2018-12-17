package ch.wetwer.moviedbapi.controller.api;

import ch.wetwer.moviedbapi.data.dao.GroupDao;
import ch.wetwer.moviedbapi.data.dao.SessionDao;
import ch.wetwer.moviedbapi.data.entity.GroupInvite;
import ch.wetwer.moviedbapi.model.GroupModel;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * @author Wetwer
 * @project movie-db-api
 * @package ch.wetwer.moviedbapi.controller.api
 * @created 17.12.2018
 **/

@Controller
@RequestMapping("api/group")
public class GroupApiController {

    private GroupDao groupDao;
    private SessionDao userDto;

    public GroupApiController(GroupDao groupDao, SessionDao userDto) {
        this.groupDao = groupDao;
        this.userDto = userDto;
    }

    @GetMapping(produces = "application/json")
    public @ResponseBody
    List<GroupInvite> getGroups(@RequestParam("sessionId") String sessionId) {
        if (userDto.getBySessionId(sessionId) != null) {
            return groupDao.getAll();
        } else {
            return null;
        }
    }

    @GetMapping(value = "{groupId}", produces = "application/json")
    public @ResponseBody
    GroupModel getGroup(@PathVariable("groupId") Long groupId, @RequestParam("sessionId") String sessionId) {
        if (userDto.getBySessionId(sessionId) != null) {
            GroupInvite group = groupDao.getById(groupId);
            GroupModel groupModel = new GroupModel();
            groupModel.setId(group.getId());
            groupModel.setName(group.getName());
            groupModel.setUsers(group.getUsers());
            return groupModel;
        } else {
            return null;
        }
    }
}
