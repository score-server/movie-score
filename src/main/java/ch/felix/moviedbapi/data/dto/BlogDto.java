package ch.felix.moviedbapi.data.dto;

import ch.felix.moviedbapi.data.entity.Blog;
import ch.felix.moviedbapi.data.repository.BlogRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BlogDto implements DtoInterface<Blog> {

    private BlogRepository blogRepository;

    public BlogDto(BlogRepository blogRepository) {
        this.blogRepository = blogRepository;
    }

    @Override
    public Blog getById(Long id) {
        return blogRepository.findBlogById(id);
    }

    @Override
    public List<Blog> getAll() {
        return blogRepository.findBlogsByOrderByTimestampDesc();
    }

    @Override
    public void save(Blog blog) {
        blogRepository.save(blog);
    }

    public void delete(Blog blog) {
        blogRepository.delete(blog);
    }
}
