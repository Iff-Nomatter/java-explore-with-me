package ru.practicum.explorewithme.services;

import ru.practicum.explorewithme.dto.category.CategoryDto;
import ru.practicum.explorewithme.dto.category.NewCategoryDto;

import java.util.List;

public interface CategoriesService {
    CategoryDto addCategory(NewCategoryDto newCategoryDto);

    CategoryDto updateCategory(CategoryDto categoryDto);

    void deleteCategory(int categoryId);

    List<CategoryDto> getCategories(int from, int size);

    CategoryDto getCategoryById(int categoryId);
}
