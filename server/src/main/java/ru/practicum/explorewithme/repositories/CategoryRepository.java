package ru.practicum.explorewithme.repositories;

import ru.practicum.explorewithme.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;


public interface CategoryRepository extends JpaRepository<Category, Integer> {
}
