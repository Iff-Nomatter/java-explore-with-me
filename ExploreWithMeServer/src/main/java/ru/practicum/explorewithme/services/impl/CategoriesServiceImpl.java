package ru.practicum.explorewithme.services.impl;

import ru.practicum.explorewithme.controllers.exceptionHandling.exceptions.EntryNotFoundException;
import ru.practicum.explorewithme.controllers.exceptionHandling.exceptions.ValidationException;
import ru.practicum.explorewithme.dto.category.CategoryDto;
import ru.practicum.explorewithme.dto.category.NewCategoryDto;
import ru.practicum.explorewithme.dto.category.mapper.CategoryMapper;
import lombok.RequiredArgsConstructor;
import ru.practicum.explorewithme.model.Category;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import ru.practicum.explorewithme.repositories.CategoryRepository;
import ru.practicum.explorewithme.services.CategoriesService;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoriesServiceImpl implements CategoriesService {

    private final CategoryRepository categoryRepository;

    @Override
    public CategoryDto addCategory(NewCategoryDto newCategoryDto) {
        try {
            Category category = categoryRepository.save(CategoryMapper.newDtoToCategory(newCategoryDto));
            return CategoryMapper.categoryToDto(category);
        } catch (DataIntegrityViolationException | ConstraintViolationException e) {
            throw new ValidationException("Такая категория уже существует");
        }

    }

    @Override
    public CategoryDto updateCategory(CategoryDto categoryDto) {
        getCategoryOrThrow(categoryDto.getId());
        categoryRepository.save(CategoryMapper.dtoToCategory(categoryDto));
        return categoryDto;
    }

    @Override
    public void deleteCategory(int categoryId) {
        try {
            categoryRepository.deleteById(categoryId);
        } catch (EmptyResultDataAccessException e) {
            throw new EntryNotFoundException("Отсутствует категория с id: " + categoryId);
        }
    }

    @Override
    public List<CategoryDto> getCategories(int from, int size) {
        Page<Category> categoryPage = categoryRepository.findAll(PageRequest.of(from / size, size));
        return CategoryMapper.categoryToDtoList(categoryPage.getContent());
    }

    @Override
    public CategoryDto getCategoryById(int categoryId) {
        Category category = getCategoryOrThrow(categoryId);
        return CategoryMapper.categoryToDto(category);
    }

    private Category getCategoryOrThrow(int categoryId) {
        return categoryRepository.findById(categoryId).orElseThrow(() ->
                new EntryNotFoundException("Отсутствует категория с id: " + categoryId));
    }
}
