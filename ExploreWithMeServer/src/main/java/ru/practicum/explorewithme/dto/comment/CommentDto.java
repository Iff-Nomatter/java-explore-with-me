package ru.practicum.explorewithme.dto.comment;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class CommentDto {
    private int id;
    private String content;
    private String authorName;
    private String eventName;
    private LocalDateTime created;
    private boolean isEdited;
}
