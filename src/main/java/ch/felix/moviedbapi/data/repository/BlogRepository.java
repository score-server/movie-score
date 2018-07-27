package ch.felix.moviedbapi.data.repository;

import ch.felix.moviedbapi.data.entity.Blog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BlogRepository extends JpaRepository<Blog, Long> {

    List<Blog> findBlogsByOrderByTimestampDesc();

}
