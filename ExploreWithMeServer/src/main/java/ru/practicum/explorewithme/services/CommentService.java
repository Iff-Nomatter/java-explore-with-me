package ru.practicum.explorewithme.services;

import ru.practicum.explorewithme.dto.comment.CommentDto;

import java.util.List;

public interface CommentService {

    CommentDto postComment(CommentDto commentDto, int userId, int eventId);

    List<CommentDto> getCommentsForEvent(int eventId);

    List<CommentDto> getCommentsForUser(int userId);

    void deleteComment(int commentId, int eventId);
}
