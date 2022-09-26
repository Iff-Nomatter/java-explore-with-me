package ru.practicum.explorewithme.dto.comment.mapper;

import ru.practicum.explorewithme.dto.comment.CommentDto;
import ru.practicum.explorewithme.model.Comment;
import ru.practicum.explorewithme.model.Event;
import ru.practicum.explorewithme.model.User;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class CommentMapper {

    public static CommentDto commentToDto(Comment comment) {
        return new CommentDto(
                comment.getId(),
                comment.getContent(),
                comment.getAuthor().getName(),
                comment.getEvent().getTitle(),
                comment.getCreatedOn(),
                comment.isEdited()
        );
    }

    public static List<CommentDto> commentToDtoList(List<Comment> comments) {
        List<CommentDto> commentDtos = new ArrayList<>();
        if (comments == null || comments.isEmpty()) {
            return commentDtos;
        }
        for (Comment comment : comments) {
            commentDtos.add(CommentMapper.commentToDto(comment));
        }
        return commentDtos;
    }

    public static Comment dtoToComment(CommentDto commentDto, User user, Event event) {
        Comment comment = new Comment();
        comment.setId(commentDto.getId());
        comment.setContent(commentDto.getContent());
        comment.setAuthor(user);
        comment.setEvent(event);
        comment.setCreatedOn(commentDto.getCreated() == null ? LocalDateTime.now() : commentDto.getCreated());
        comment.setEdited(commentDto.isEdited());
        return comment;
    }
}
