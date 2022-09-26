package ru.practicum.explorewithme.dto.compilation;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class NewCompilationDto {
    @JsonProperty(required = true)
    private List<Integer> events;
    @JsonProperty(required = true)
    private boolean pinned;
    @JsonProperty(required = true)
    private String title;
}
