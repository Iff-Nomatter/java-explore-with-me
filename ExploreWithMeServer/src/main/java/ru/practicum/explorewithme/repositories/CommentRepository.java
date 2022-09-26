package ru.practicum.explorewithme.repositories;

import ru.practicum.explorewithme.model.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comment, Integer> {
}
