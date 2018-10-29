package ch.felix.moviedbapi.data.dto;

import ch.felix.moviedbapi.data.entity.GroupInvite;
import ch.felix.moviedbapi.data.repository.GroupRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GroupDto {

    private GroupRepository groupRepository;

    public GroupDto(GroupRepository groupRepository) {
        this.groupRepository = groupRepository;
    }

    public List<GroupInvite> getAll() {
        return groupRepository.findAll();
    }

}
