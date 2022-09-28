package ru.practicum.explorewithme.services.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.practicum.explorewithme.controllers.exceptionHandling.exceptions.EntryNotFoundException;
import ru.practicum.explorewithme.dto.comment.CommentDto;
import ru.practicum.explorewithme.dto.comment.mapper.CommentMapper;
import ru.practicum.explorewithme.model.Comment;
import ru.practicum.explorewithme.model.Event;
import ru.practicum.explorewithme.model.User;
import ru.practicum.explorewithme.repositories.CommentRepository;
import ru.practicum.explorewithme.services.CommentService;
import ru.practicum.explorewithme.services.EventService;
import ru.practicum.explorewithme.services.UserService;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {

    private final CommentRepository commentRepository;
    private final UserService userService;
    private final EventService eventService;

    @Override
    public CommentDto postComment(CommentDto commentDto, int userId, int eventId) {
        User user = getUserOrThrow(userId);
        Event event = getEventOrThrow(eventId);
        Comment comment = CommentMapper.dtoToComment(commentDto, user, event);
        commentRepository.save(comment);
        return CommentMapper.commentToDto(comment);
    }

    @Override
    public List<CommentDto> getCommentsForEvent(int eventId) {
        getEventOrThrow(eventId);
        List<Comment> comments = commentRepository.findAllByEvent_Id(eventId);
        return CommentMapper.commentToDtoList(comments);
    }

    @Override
    public List<CommentDto> getCommentsForUser(int userId) {
        getUserOrThrow(userId);
        List<Comment> comments = commentRepository.findAllByAuthor_Id(userId);
        return CommentMapper.commentToDtoList(comments);
    }

    @Override
    public void deleteComment(int commentId, int eventId) {
        getEventOrThrow(eventId);
        getCommentOrThrow(commentId);
        commentRepository.deleteCommentByEvent_IdAndId(eventId, commentId);
    }

    /**
     * Проверка наличия пользователя в базе
     */
    private User getUserOrThrow(int userId) {
        return userService.getUserOrThrow(userId);
    }

    /**
     * Проверка наличия события в базе
     */
    private Event getEventOrThrow(int eventId) {
        return eventService.getEventOrThrow(eventId);
    }

    private Comment getCommentOrThrow(int commentId) {
        return commentRepository.findById(commentId).orElseThrow(() ->
                new EntryNotFoundException("Отсутствует комментарий с id: " + commentId));
    }
}
