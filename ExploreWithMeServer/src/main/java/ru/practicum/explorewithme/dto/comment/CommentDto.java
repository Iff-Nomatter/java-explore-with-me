package ru.practicum.explorewithme.dto.comment;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;

import javax.validation.constraints.Max;

@Data
@AllArgsConstructor
public class CommentDto {
    private int id;
    @JsonProperty(required = true)
    @Max(255)
    private String content;
    private String authorName;
    private String eventName;
    private String created;
}
