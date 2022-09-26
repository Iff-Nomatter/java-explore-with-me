package ru.practicum.explorewithme.dto.category.mapper;

import ru.practicum.explorewithme.dto.category.CategoryDto;
import ru.practicum.explorewithme.dto.category.NewCategoryDto;
import ru.practicum.explorewithme.model.Category;

import java.util.ArrayList;
import java.util.List;

public class CategoryMapper {
    public static CategoryDto categoryToDto(Category category) {
        return new CategoryDto(
                category.getId(),
                category.getName()
        );
    }

    public static Category newDtoToCategory(NewCategoryDto newCategoryDto) {
        Category category = new Category();
        category.setName(newCategoryDto.getName());
        return category;
    }

    public static Category dtoToCategory(CategoryDto categoryDto) {
        Category category = new Category();
        category.setId(categoryDto.getId());
        category.setName(categoryDto.getName());
        return category;
    }

    public static List<CategoryDto> categoryToDtoList(List<Category> categoryList) {
        List<CategoryDto> categoryDtos = new ArrayList<>();
        if (categoryList == null || categoryList.isEmpty()) {
            return categoryDtos;
        }
        for (Category category : categoryList) {
            categoryDtos.add(CategoryMapper.categoryToDto(category));
        }
        return categoryDtos;
    }
}
