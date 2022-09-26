package ru.practicum.explorewithme.services;

import ru.practicum.explorewithme.dto.event.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public interface EventService {

    EventFullDto getEventById(HttpServletRequest request, int eventId);

    List<EventShortDto> getEvents(HttpServletRequest request,
                                  String text,
                                  int[] categories,
                                  Boolean isPaid,
                                  String rangeStart,
                                  String rangeEnd,
                                  boolean onlyAvailable,
                                  String sort,
                                  int from,
                                  int size);

    List<EventFullDto> getEventsAdmin(int[] users,
                                      String[] states,
                                      int[] categories,
                                      String rangeStart,
                                      String rangeEnd,
                                      int from,
                                      int size);

    EventFullDto addEvent(NewEventDto newEventDto, int userId);

    EventFullDto updateEvent(EventUpdateDto eventUpdateDto, int userId);

    EventFullDto getFullEventById(int userId, int eventId);

    List<EventShortDto> getEventListByUserId(int userId, int from, int size);

    EventFullDto cancelEvent(int userId, int eventId);

    EventFullDto updateEventAdmin(int eventId, AdminUpdateEventRequestDto adminUpdateEventRequestDto);

    EventFullDto publishEvent(int eventId);

    EventFullDto rejectEvent(int eventId);

}
