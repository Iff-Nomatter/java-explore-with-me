package ru.practicum.explorewithme.dto.comment.mapper;

import ru.practicum.explorewithme.dto.comment.CommentDto;
import ru.practicum.explorewithme.model.Comment;
import ru.practicum.explorewithme.model.Event;
import ru.practicum.explorewithme.model.User;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class CommentMapper {

    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    public static CommentDto commentToDto(Comment comment) {
        return new CommentDto(
                comment.getId(),
                comment.getContent(),
                comment.getAuthor().getName(),
                comment.getEvent().getTitle(),
                comment.getCreatedOn().format(formatter)
        );
    }

    public static List<CommentDto> commentToDtoList(List<Comment> comments) {
        List<CommentDto> commentDtos = new ArrayList<>();
        if (comments == null || comments.isEmpty()) {
            return commentDtos;
        }
        comments.sort((Comparator.comparing(Comment::getCreatedOn)));
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
        comment.setCreatedOn(LocalDateTime.parse(commentDto.getCreated(), formatter));
        return comment;
    }
}
