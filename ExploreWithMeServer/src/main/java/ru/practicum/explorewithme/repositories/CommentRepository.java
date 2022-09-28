package ru.practicum.explorewithme.repositories;

import ru.practicum.explorewithme.model.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Integer> {

    List<Comment> findAllByAuthor_Id(int userId);

    List<Comment> findAllByEvent_Id(int eventId);

    void deleteCommentByEvent_IdAndId(int eventId, int commentId);
}
