package ch.wetwer.moviedbapi.data.repository;

import ch.wetwer.moviedbapi.data.entity.GroupInvite;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GroupRepository extends JpaRepository<GroupInvite, Long> {

    GroupInvite findByNameEquals(String groupKey);

}
