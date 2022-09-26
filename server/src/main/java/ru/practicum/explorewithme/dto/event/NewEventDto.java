package ru.practicum.explorewithme.dto.event;

import com.fasterxml.jackson.annotation.JsonProperty;
import ru.practicum.explorewithme.dto.location.LocationDto;
import lombok.AllArgsConstructor;
import lombok.Data;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;


@Data
@AllArgsConstructor
public class NewEventDto {
    @JsonProperty(required = true)
    @Max(value = 2000)
    @Min(value = 20)
    private String annotation;
    @JsonProperty(required = true)
    private int category;
    @JsonProperty(required = true)
    @Max(value = 7000)
    @Min(value = 20)
    private String description;
    @JsonProperty(required = true)
    private String eventDate;
    @JsonProperty(required = true)
    private LocationDto location;
    @JsonProperty(defaultValue = "false")
    private boolean paid;
    @JsonProperty(defaultValue = "0")
    private int participantLimit;
    @JsonProperty(defaultValue = "false")
    private boolean requestModeration;
    @JsonProperty(required = true)
    @Min(3)
    @Max(120)
    private String title;
}
