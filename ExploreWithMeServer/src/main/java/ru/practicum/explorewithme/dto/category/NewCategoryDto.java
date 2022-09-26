package ru.practicum.explorewithme.dto.category;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;


@Data
public class NewCategoryDto {
    @JsonProperty(required = true)
    private String name;
}
