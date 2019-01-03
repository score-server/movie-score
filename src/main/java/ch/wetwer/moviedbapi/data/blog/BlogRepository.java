package ch.wetwer.moviedbapi.data.blog;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author Wetwer
 * @project movie-db
 */
@Repository
public interface BlogRepository extends JpaRepository<Blog, Long> {

    List<Blog> findBlogsByOrderByTimestampDesc();

    Blog findBlogById(Long id);
}
