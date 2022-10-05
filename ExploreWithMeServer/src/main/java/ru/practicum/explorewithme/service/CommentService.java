package ru.practicum.explorewithme.service;

import ru.practicum.explorewithme.dto.comment.CommentDto;

import java.util.List;

public interface CommentService {

    CommentDto createComment(CommentDto commentDto, int userId, int eventId);

    List<CommentDto> getAllByUserId(int userId, int from, int size);

    void deleteComment(int commentId);

    CommentDto editComment(CommentDto commentDto);
}
