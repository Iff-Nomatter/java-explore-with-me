package ru.practicum.explorewithme.controllers;

import ru.practicum.explorewithme.dto.category.CategoryDto;
import ru.practicum.explorewithme.dto.category.NewCategoryDto;
import ru.practicum.explorewithme.dto.compilation.CompilationDto;
import ru.practicum.explorewithme.dto.compilation.NewCompilationDto;
import ru.practicum.explorewithme.dto.event.AdminUpdateEventRequestDto;
import ru.practicum.explorewithme.dto.event.EventFullDto;
import ru.practicum.explorewithme.dto.user.UserDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.explorewithme.services.*;

import java.util.List;

@RestController
@Slf4j
@Validated
@RequiredArgsConstructor
@RequestMapping(path = "/admin")
public class AdminController {

    private final UserService userService;
    private final EventService eventService;
    private final CategoriesService categoriesService;
    private final CompilationService compilationService;
    private final CommentService commentService;

    @GetMapping("/events")
    public List<EventFullDto> getEventsAdmin(@RequestParam int[] users,
                                             @RequestParam String[] states,
                                             @RequestParam int[] categories,
                                             @RequestParam String rangeStart,
                                             @RequestParam String rangeEnd,
                                             @RequestParam int from,
                                             @RequestParam int size) {
        log.info("Администратор запросил список событий с параметрами users={}, states={}, categories={}, rangeStart={}, rangeEnd={}, from={}, size={}",
                users, states, categories, rangeStart, rangeEnd, from, size);
        return eventService.getEventsAdmin(users, states, categories, rangeStart, rangeEnd, from, size);
    }

    @PutMapping("/events/{eventId}")
    public EventFullDto updateEventAdmin(@PathVariable int eventId,
                                         @RequestBody AdminUpdateEventRequestDto adminUpdateEventRequestDto) {
        log.info("Администратор обновил событие id={}, adminUpdateEventRequestDto={}",
                eventId, adminUpdateEventRequestDto);
        return eventService.updateEventAdmin(eventId, adminUpdateEventRequestDto);
    }

    @DeleteMapping("/events/{eventId}/comments/{commentId}")
    public void deleteComment(@PathVariable int eventId,
                              @PathVariable int commentId) {
        log.info("Администратор удалил комментарий id={} к событию id={}", commentId, eventId);
        commentService.deleteComment(commentId, eventId);
    }

    @PatchMapping("/events/{eventId}/publish")
    public EventFullDto publishEvent(@PathVariable int eventId) {
        log.info("Администратор опубликовал событие id={}", eventId);
        return eventService.publishEvent(eventId);
    }

    @PatchMapping("/events/{eventId}/reject")
    public EventFullDto rejectEvent(@PathVariable int eventId) {
        log.info("Администратор отклонил событие id={}", eventId);
        return eventService.rejectEvent(eventId);
    }

    @PatchMapping("/categories")
    public CategoryDto updateCategory(@RequestBody CategoryDto categoryDto) {
        log.info("Администратор обновил категорию={}", categoryDto);
        return categoriesService.updateCategory(categoryDto);
    }

    @PostMapping("/categories")
    public CategoryDto postCategory(@RequestBody NewCategoryDto newCategoryDto) {
        log.info("Администратор добавил категорию={}", newCategoryDto);
        return categoriesService.addCategory(newCategoryDto);
    }

    @DeleteMapping("/categories/{catId}")
    public void deleteCategory(@PathVariable int catId) {
        log.info("Администратор удалил категорию id={}", catId);
        categoriesService.deleteCategory(catId);
    }

    @GetMapping("/users")
    public List<UserDto> getUsersById(@RequestParam int[] ids) {
        log.info("Администратор запросил пользователей = {}", ids);
        return userService.getUsersById(ids);
    }

    @PostMapping("/users")
    public UserDto addUser(@RequestBody UserDto userDto) {
        log.info("Администратор добавил пользователя = {}", userDto);
        return userService.addUser(userDto);
    }

    @DeleteMapping("/users/{id}")
    public void deleteUser(@PathVariable int id) {
        log.info("Администратор удалил пользователя = {}", id);
        userService.deleteUser(id);
    }

    @PostMapping("/compilations")
    public CompilationDto postCompilation(@RequestBody NewCompilationDto newCompilationDto) {
        log.info("Администратор добавил подборку={}", newCompilationDto);
        return compilationService.addCompilation(newCompilationDto);
    }

    @DeleteMapping("/compilations/{compId}")
    public void deleteCompilation(@PathVariable int compId) {
        log.info("Администратор удалил подборку id={}", compId);
        compilationService.deleteCompilation(compId);
    }

    @DeleteMapping("/compilations/{compId}/events/{eventId}")
    public void deleteEventFromCompilation(@PathVariable int compId,
                                  @PathVariable int eventId) {
        log.info("Администратор удалил событие id={} из подборки id={}", eventId, compId);
        compilationService.deleteEventFromCompilation(compId, eventId);
    }

    @PatchMapping("/compilations/{compId}/events/{eventId}")
    public void addEventToCompilation(@PathVariable int compId,
                                  @PathVariable int eventId) {
        log.info("Администратор добавил событие id={} в подборку id={}", eventId, compId);
        compilationService.addEventToCompilation(compId, eventId);
    }

    @DeleteMapping("/compilations/{compId}/pin")
    public void unpinCompilation(@PathVariable int compId) {
        log.info("Администратор открепил подборку id={}", compId);
        compilationService.unpinCompilation(compId);
    }

    @PatchMapping("/compilations/{compId}/pin")
    public void pinCompilation(@PathVariable int compId) {
        log.info("Администратор закрепил подборку id={}", compId);
        compilationService.pinCompilation(compId);
    }

}
