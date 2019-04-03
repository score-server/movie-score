package ch.wetwer.moviedbapi.data.groupinvite;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author Wetwer
 * @project movie-score
 */

@Repository
public interface GroupRepository extends JpaRepository<GroupInvite, Long> {

    GroupInvite findByNameEquals(String groupKey);

}
