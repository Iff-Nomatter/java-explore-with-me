package ru.practicum.explorewithme.dto.category;

import lombok.AllArgsConstructor;
import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
@AllArgsConstructor
public class CategoryDto {
    private int id;
    @NotBlank
    private String name;
}
