package ru.practicum.explorewithme.repositories;

import ru.practicum.explorewithme.model.Compilation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CompilationRepository extends JpaRepository<Compilation, Integer> {
}
