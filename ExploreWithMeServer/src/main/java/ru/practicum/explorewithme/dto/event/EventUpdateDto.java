package ru.practicum.explorewithme.dto.event;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

@Data
@AllArgsConstructor
public class EventUpdateDto {
    @JsonProperty(required = true)
    private int eventId;
    @Max(value = 2000)
    @Min(value = 20)
    private String annotation;
    private Integer category;
    @Max(value = 7000)
    @Min(value = 20)
    private String description;
    private String eventDate;
    private Boolean paid;
    @Min(3)
    @Max(120)
    private String title;
    private Integer participantLimit;
}
