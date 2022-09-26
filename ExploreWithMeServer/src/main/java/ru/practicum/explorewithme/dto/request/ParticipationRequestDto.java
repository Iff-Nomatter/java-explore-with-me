package ru.practicum.explorewithme.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ParticipationRequestDto {
    @JsonProperty(required = true)
    private String created;
    @JsonProperty(required = true)
    private int event;
    @JsonProperty(required = true)
    private int id;
    @JsonProperty(required = true)
    private int requester;
    @JsonProperty(required = true)
    private String status;
}
