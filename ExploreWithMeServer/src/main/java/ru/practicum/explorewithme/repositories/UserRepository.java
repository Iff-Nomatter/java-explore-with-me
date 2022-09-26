package ru.practicum.explorewithme.repositories;

import ru.practicum.explorewithme.model.User;
import org.springframework.data.jpa.repository.JpaRepository;


public interface UserRepository extends JpaRepository<User, Integer> {
}
