package ch.felix.moviedbapi.data.repository;

import ch.felix.moviedbapi.data.entity.GroupInvite;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GroupRepository extends JpaRepository<GroupInvite, Long> {

    GroupInvite findByNameEquals(String groupKey);

}
