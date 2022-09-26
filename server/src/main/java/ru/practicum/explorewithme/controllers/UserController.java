package ru.practicum.explorewithme.controllers;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.explorewithme.dto.event.EventFullDto;
import ru.practicum.explorewithme.dto.event.EventShortDto;
import ru.practicum.explorewithme.dto.event.EventUpdateDto;
import ru.practicum.explorewithme.dto.event.NewEventDto;
import ru.practicum.explorewithme.dto.request.ParticipationRequestDto;
import ru.practicum.explorewithme.services.EventService;
import ru.practicum.explorewithme.services.RequestService;

import java.util.List;

@RestController
@Slf4j
@Validated
@RequiredArgsConstructor
@RequestMapping("/users")
public class UserController {
    private final EventService eventService;
    private final RequestService requestService;

    @GetMapping("/{userId}/events")
    public List<EventShortDto> getEventsOfUserById(@PathVariable int userId,
                                                   @RequestParam(defaultValue = "0") int from,
                                                   @RequestParam(defaultValue = "10") int size) {
        log.info("Запрошены события пользователя id={}, страница={}, размер={}", userId, from, size);
        return eventService.getEventListByUserId(userId, from, size);
    }

    @PatchMapping("/{userId}/events")
    public EventFullDto updateEvent(@PathVariable int userId,
                                     @RequestBody EventUpdateDto eventUpdateDto) {
        log.info("Пользователь id={} обновил событие={}", userId, eventUpdateDto);
        return eventService.updateEvent(eventUpdateDto, userId);
    }

    @PostMapping("/{userId}/events")
    public EventFullDto postEvent(@PathVariable int userId,
                                  @RequestBody NewEventDto newEventDto) {
        log.info("Пользователь id={} создал событие={}", userId, newEventDto);
        return eventService.addEvent(newEventDto, userId);
    }

    @GetMapping("/{userId}/events/{eventId}")
    public EventFullDto getFullEventById(@PathVariable int userId,
                                         @PathVariable int eventId) {
        log.info("Пользователь id={} запросил событие id={}", userId, eventId);
        return eventService.getFullEventById(userId, eventId);
    }

    @PatchMapping("/{userId}/events/{eventId}")
    public EventFullDto cancelEvent(@PathVariable int userId,
                                    @PathVariable int eventId) {
        log.info("Пользователь id={} отменил событие id={}", userId, eventId);
        return eventService.cancelEvent(userId, eventId);
    }

    @GetMapping("/{userId}/events/{eventId}/requests")
    public List<ParticipationRequestDto> getRequestForEventByUserId(@PathVariable int userId,
                                                                    @PathVariable int eventId) {
        log.info("Пользователь id={} запросил список запросов к событию id={}", userId, eventId);
        return requestService.getRequestForEventByUserId(userId, eventId);
    }

    @PatchMapping("/{userId}/events/{eventId}/requests/{reqId}/confirm")
    public ParticipationRequestDto confirmRequest(@PathVariable int userId,
                                                  @PathVariable int eventId,
                                                  @PathVariable int reqId) {
        log.info("Пользователь id={}, подтвердил запрос на участие id={}, в событии id={}", userId, reqId, eventId);
        return requestService.confirmRequest(userId, eventId, reqId);
    }

    @PatchMapping("/{userId}/events/{eventId}/requests/{reqId}/reject")
    public ParticipationRequestDto rejectRequest(@PathVariable int userId,
                                                  @PathVariable int eventId,
                                                  @PathVariable int reqId) {
        log.info("Пользователь id={}, отклонил запрос на участие id={}, в событии id={}", userId, reqId, eventId);
        return requestService.rejectRequest(userId, eventId, reqId);
    }

    @GetMapping("/{userId}/requests")
    public List<ParticipationRequestDto> getRequestListByUserId(@PathVariable int userId) {
        log.info("Пользователь id={} запросил список своих запросов на участие в событиях", userId);
        return requestService.getRequestsByUserId(userId);
    }

    @PostMapping("/{userId}/requests")
    public ParticipationRequestDto postRequest(@PathVariable int userId,
                                               @RequestParam int eventId) {
        log.info("Пользователь id={} добавил запрос на участие в событии={}", userId, eventId);
        return requestService.addRequest(userId, eventId);
    }

    @PatchMapping("/{userId}/requests/{requestId}/cancel")
    public ParticipationRequestDto cancelRequest(@PathVariable int userId,
                                                 @PathVariable int requestId) {
        log.info("Пользователь id={} отменил свой запрос на участие id={}", userId, requestId);
        return requestService.removeRequest(userId, requestId);
    }
}
