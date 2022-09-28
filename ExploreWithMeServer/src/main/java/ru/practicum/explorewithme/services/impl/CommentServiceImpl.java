package ru.practicum.explorewithme.services.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.explorewithme.controllers.exceptionHandling.exceptions.ConditionsNotMetException;
import ru.practicum.explorewithme.controllers.exceptionHandling.exceptions.EntryNotFoundException;
import ru.practicum.explorewithme.dto.comment.CommentDto;
import ru.practicum.explorewithme.dto.comment.mapper.CommentMapper;
import ru.practicum.explorewithme.model.Comment;
import ru.practicum.explorewithme.model.Event;
import ru.practicum.explorewithme.model.User;
import ru.practicum.explorewithme.repositories.CommentRepository;
import ru.practicum.explorewithme.repositories.EventRepository;
import ru.practicum.explorewithme.repositories.UserRepository;
import ru.practicum.explorewithme.services.CommentService;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CommentServiceImpl implements CommentService {

    private final CommentRepository commentRepository;
    private final UserRepository userRepository;
    private final EventRepository eventRepository;

    @Override
    @Transactional
    public CommentDto postComment(CommentDto commentDto, int userId, int eventId) {
        User user = getUserOrThrow(userId);
        Event event = getEventOrThrow(eventId);
        Comment comment = CommentMapper.dtoToComment(commentDto, user, event);
        commentRepository.save(comment);
        return CommentMapper.commentToDto(comment);
    }

    @Override
    public List<CommentDto> getCommentsForUser(int userId) {
        getUserOrThrow(userId);
        List<Comment> comments = commentRepository.findAllByAuthor_Id(userId);
        return CommentMapper.commentToDtoList(comments);
    }

    @Override
    @Transactional
    public void deleteComment(int commentId, int eventId) {
        Event event = getEventOrThrow(eventId);
        Comment comment = getCommentOrThrow(commentId);
        if (!event.getComments().contains(comment)) {
            throw new ConditionsNotMetException("Этот комментарий не принадлежит к этому событию");
        }
        commentRepository.deleteCommentByEvent_IdAndId(eventId, commentId);
    }

    /**
     * Проверка наличия пользователя в базе
     */
    private User getUserOrThrow(int userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new EntryNotFoundException("Отсутствует пользователь с id: " + userId));
    }

    /**
     * Проверка наличия события в базе
     */
    public Event getEventOrThrow(int eventId) {
        return eventRepository.findById(eventId).orElseThrow(() ->
                new EntryNotFoundException("Отсутствует событие с id: " + eventId));
    }

    private Comment getCommentOrThrow(int commentId) {
        return commentRepository.findById(commentId).orElseThrow(() ->
                new EntryNotFoundException("Отсутствует комментарий с id: " + commentId));
    }
}
