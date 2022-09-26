package ru.practicum.explorewithme.dto.event;

import ru.practicum.explorewithme.dto.category.CategoryDto;
import ru.practicum.explorewithme.dto.location.LocationDto;
import ru.practicum.explorewithme.dto.user.UserShortDto;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class EventFullDto {
    private String annotation;
    private CategoryDto category;
    private long confirmedRequests;
    private String createdOn;
    private String description;
    private String eventDate;
    private int id;
    private UserShortDto initiator;
    private LocationDto location;
    private boolean paid;
    private int participantLimit;
    private String publishedOn;
    private boolean requestModeration;
    private String state;
    private String title;
    private int views;
}
