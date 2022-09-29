package ru.practicum.explorewithme.service.impl;

import org.springframework.transaction.annotation.Transactional;
import ru.practicum.explorewithme.controller.exceptionHandling.exception.ConditionsNotMetException;
import ru.practicum.explorewithme.controller.exceptionHandling.exception.EntryNotFoundException;
import ru.practicum.explorewithme.dto.category.CategoryDto;
import ru.practicum.explorewithme.dto.category.NewCategoryDto;
import ru.practicum.explorewithme.dto.category.mapper.CategoryMapper;
import lombok.RequiredArgsConstructor;
import ru.practicum.explorewithme.model.Category;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import ru.practicum.explorewithme.model.Event;
import ru.practicum.explorewithme.repository.CategoryRepository;
import ru.practicum.explorewithme.repository.EventRepository;
import ru.practicum.explorewithme.service.CategoriesService;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CategoriesServiceImpl implements CategoriesService {

    private final CategoryRepository categoryRepository;

    private final EventRepository eventRepository;

    @Override
    @Transactional
    public CategoryDto addCategory(NewCategoryDto newCategoryDto) {
        Category category = categoryRepository.save(CategoryMapper.newDtoToCategory(newCategoryDto));
        return CategoryMapper.categoryToDto(category);
    }

    @Override
    @Transactional
    public CategoryDto updateCategory(CategoryDto categoryDto) {
        Category category = getCategoryOrThrow(categoryDto.getId());
        category.setName(categoryDto.getName());
        return CategoryMapper.categoryToDto(category);
    }

    @Override
    @Transactional
    public void deleteCategory(int categoryId) {
        List<Event> events = eventRepository.findEventsByCategory(getCategoryOrThrow(categoryId));
        if (!events.isEmpty()) {
            throw new ConditionsNotMetException("Нельзя удалять категорию, к которой приписаны события");
        }
        categoryRepository.deleteById(categoryId);
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