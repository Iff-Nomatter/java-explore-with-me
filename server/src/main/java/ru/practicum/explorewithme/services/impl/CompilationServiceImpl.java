package ru.practicum.explorewithme.services.impl;

import ru.practicum.explorewithme.clients.StatisticsClient;
import ru.practicum.explorewithme.controllers.exceptionHandling.exceptions.EntryNotFoundException;
import ru.practicum.explorewithme.dto.compilation.CompilationDto;
import ru.practicum.explorewithme.dto.compilation.NewCompilationDto;
import ru.practicum.explorewithme.dto.compilation.mapper.CompilationMapper;
import lombok.RequiredArgsConstructor;
import ru.practicum.explorewithme.model.Compilation;
import ru.practicum.explorewithme.model.Event;
import ru.practicum.explorewithme.model.StatEntry;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import ru.practicum.explorewithme.repositories.CompilationRepository;
import ru.practicum.explorewithme.repositories.EventRepository;
import ru.practicum.explorewithme.services.CompilationService;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


@Service
@RequiredArgsConstructor
public class CompilationServiceImpl implements CompilationService {

    private final CompilationRepository compilationRepository;
    private final EventRepository eventRepository;
    private final StatisticsClient statisticsClient;

    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    @Override
    public CompilationDto addCompilation(NewCompilationDto newCompilationDto) {
        Compilation compilation = CompilationMapper.dtoToCompilation(newCompilationDto);
        compilation.setEvents(eventRepository.findAllById(newCompilationDto.getEvents()));
        compilationRepository.save(compilation);
        HashMap<Integer, StatEntry> statEntryHashMap = groupStatEntryListById(getStatsForEventList(compilation.getEvents()));
        return CompilationMapper.compilationToDto(compilation, statEntryHashMap);
    }

    @Override
    public void deleteCompilation(int compilationId) {
        getCompilationOrThrow(compilationId);
        compilationRepository.deleteById(compilationId);
    }

    @Override
    public void deleteEventFromCompilation(int compilationId, int eventId) {
        Compilation compilation = getCompilationOrThrow(compilationId);
        Event event = getEventOrThrow(eventId);
        List<Event> eventList = compilation.getEvents();
        eventList.remove(event);
        compilation.setEvents(eventList);
        compilationRepository.save(compilation);
    }

    @Override
    public void addEventToCompilation(int compilationId, int eventId) {
        Compilation compilation = getCompilationOrThrow(compilationId);
        Event event = getEventOrThrow(eventId);
        List<Event> eventList = compilation.getEvents();
        eventList.add(event);
        compilation.setEvents(eventList);
        compilationRepository.save(compilation);
    }

    @Override
    public void unpinCompilation(int compilationId) {
        Compilation compilation = getCompilationOrThrow(compilationId);
        compilation.setPinned(false);
        compilationRepository.save(compilation);
    }

    @Override
    public void pinCompilation(int compilationId) {
        Compilation compilation = getCompilationOrThrow(compilationId);
        compilation.setPinned(true);
        compilationRepository.save(compilation);
    }

    @Override
    public List<CompilationDto> getCompilationList(boolean pinned, int from, int size) {
        Page<Compilation> compilationPage = compilationRepository.findAll(PageRequest.of(from / size, size));
        List<Compilation> compilationList = compilationPage.getContent();
        List<CompilationDto> compilationDtos = new ArrayList<>();
        for (Compilation compilation : compilationList) {
            compilationDtos.add(CompilationMapper.compilationToDto(compilation, groupStatEntryListById(getStatsForEventList(compilation.getEvents()))));
        }
        return compilationDtos;
    }

    @Override
    public CompilationDto getCompilationById(int compilationId) {
        Compilation compilation = getCompilationOrThrow(compilationId);
        HashMap<Integer, StatEntry> statEntryHashMap = groupStatEntryListById(getStatsForEventList(compilation.getEvents()));
        return CompilationMapper.compilationToDto(getCompilationOrThrow(compilationId), statEntryHashMap);
    }

    private Compilation getCompilationOrThrow(int compilationId) {
        return compilationRepository.findById(compilationId).orElseThrow(() ->
                new EntryNotFoundException("Не найдена подборка с id: " + compilationId));
    }

    private Event getEventOrThrow(int eventId) {
        return eventRepository.findById(eventId).orElseThrow(() ->
                new EntryNotFoundException("Не найдено событие с id: " + eventId));
    }

    private HashMap<Integer, StatEntry> groupStatEntryListById(List<StatEntry> statEntryList) {
        HashMap<Integer, StatEntry> statEntryHashMap = new HashMap<>();
        if (statEntryList.isEmpty() || statEntryList.get(0).getApp() == null) {
            return statEntryHashMap;
        } else {
            for (StatEntry statEntry : statEntryList) {
                String entryUri = statEntry.getUri();
                entryUri = entryUri.substring(entryUri.lastIndexOf("/"));
                statEntryHashMap.put(Integer.parseInt(entryUri), statEntry);
            }
        }
        return statEntryHashMap;
    }

    private List<StatEntry> getStatsForEventList(List<Event> eventList) {
        List<StatEntry> statEntryList = new ArrayList<>();
        for (Event event : eventList) {
            statEntryList.add(statisticsClient.getEventStat(event.getId(),
                    LocalDateTime.now().format(formatter),
                    LocalDateTime.now().format(formatter)));
        }
        return statEntryList;
    }
}
