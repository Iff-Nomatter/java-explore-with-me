package ru.practicum.explorewithme.repository;

import ru.practicum.explorewithme.model.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Integer> {

    List<Comment> findAllByAuthor_Id(int userId);

    void deleteCommentById(int commentId);
}
