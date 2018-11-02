package ch.felix.moviedbapi.data.dto;

import ch.felix.moviedbapi.data.entity.Comment;
import ch.felix.moviedbapi.data.repository.CommentRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CommentDto implements DtoInterface<Comment> {

    private CommentRepository commentRepository;

    public CommentDto(CommentRepository commentRepository) {
        this.commentRepository = commentRepository;
    }

    @Override
    public Comment getById(Long id) {
        return commentRepository.getOne(id);
    }

    @Override
    public List<Comment> getAll() {
        return commentRepository.findAll();
    }

    @Override
    public void save(Comment comment) {
        commentRepository.save(comment);
    }
}
