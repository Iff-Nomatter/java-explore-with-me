package ru.practicum.explorewithme.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.explorewithme.controller.exceptionHandling.exception.EntryNotFoundException;
import ru.practicum.explorewithme.dto.comment.CommentDto;
import ru.practicum.explorewithme.dto.comment.mapper.CommentMapper;
import ru.practicum.explorewithme.model.Comment;
import ru.practicum.explorewithme.model.Event;
import ru.practicum.explorewithme.model.User;
import ru.practicum.explorewithme.repository.CommentRepository;
import ru.practicum.explorewithme.repository.EventRepository;
import ru.practicum.explorewithme.repository.UserRepository;
import ru.practicum.explorewithme.service.CommentService;

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
        List<Comment> comments = commentRepository.findAllByAuthor_Id(userId);
        return CommentMapper.commentToDtoList(comments);
    }

    @Override
    @Transactional
    public void deleteComment(int commentId) {
        commentRepository.deleteCommentById(commentId);
    }

    @Override
    public CommentDto editComment(int commentId, CommentDto commentDto) {
        Comment commentToUpdate = getCommentOrThrow(commentId);
        commentToUpdate.setContent(commentDto.getContent());
        return CommentMapper.commentToDto(commentToUpdate);
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

    public Comment getCommentOrThrow(int commentId) {
        return commentRepository.findById(commentId).orElseThrow(() ->
                new EntryNotFoundException("Отсутствует комментарий с id: " + commentId));
    }
}
