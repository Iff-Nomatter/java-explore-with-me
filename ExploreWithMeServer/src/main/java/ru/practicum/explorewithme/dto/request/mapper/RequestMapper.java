package ru.practicum.explorewithme.dto.request.mapper;

import ru.practicum.explorewithme.dto.request.ParticipationRequestDto;
import ru.practicum.explorewithme.model.Request;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class RequestMapper {

    public static ParticipationRequestDto requestDToDto(Request request) {
        return new ParticipationRequestDto(
                request.getCreatedOn().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")),//TODO вынести форматтер
                request.getEvent().getId(),
                request.getId(),
                request.getRequester().getId(),
                request.getState().toString()
        );
    }

    public static List<ParticipationRequestDto> requestToDtoList(List<Request> requestList) {
        List<ParticipationRequestDto> participationRequestDtos = new ArrayList<>();
        if (requestList == null || requestList.isEmpty()) {
            return participationRequestDtos;
        }
        for (Request request : requestList) {
            participationRequestDtos.add(RequestMapper.requestDToDto(request));
        }
        return participationRequestDtos;
    }
}
