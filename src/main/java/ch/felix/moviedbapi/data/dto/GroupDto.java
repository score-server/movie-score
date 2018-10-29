package ch.felix.moviedbapi.data.dto;

import ch.felix.moviedbapi.data.entity.GroupInvite;
import ch.felix.moviedbapi.data.repository.GroupRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GroupDto implements DtoInterface<GroupInvite> {

    private GroupRepository groupRepository;

    public GroupDto(GroupRepository groupRepository) {
        this.groupRepository = groupRepository;
    }

    @Override
    public GroupInvite getById(Long id) {
        return groupRepository.getOne(Math.toIntExact(id));
    }

    public List<GroupInvite> getAll() {
        return groupRepository.findAll();
    }

    @Override
    public void save(GroupInvite groupInvite) {
        groupRepository.save(groupInvite);
    }

}
