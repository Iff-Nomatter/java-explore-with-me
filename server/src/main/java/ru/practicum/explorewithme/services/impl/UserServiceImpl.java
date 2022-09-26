package ru.practicum.explorewithme.services.impl;

import ru.practicum.explorewithme.controllers.exceptionHandling.exceptions.EntryNotFoundException;
import ru.practicum.explorewithme.controllers.exceptionHandling.exceptions.ValidationException;
import ru.practicum.explorewithme.dto.user.UserDto;
import ru.practicum.explorewithme.dto.user.mapper.UserMapper;
import lombok.RequiredArgsConstructor;
import ru.practicum.explorewithme.model.User;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import ru.practicum.explorewithme.repositories.UserRepository;
import ru.practicum.explorewithme.services.UserService;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Override
    public List<UserDto> getUsersById(int[] ids) {
        List<UserDto> userDtos = new ArrayList<>();
        for (int id : ids) {
            userDtos.add(UserMapper.userToDto(getUserOrThrow(id)));
        }
        return userDtos;
    }

    @Override
    public UserDto addUser(UserDto userDto) {
        try {
            User user = userRepository.save(UserMapper.dtoToUser(userDto));
            return UserMapper.userToDto(user);
        } catch (DataIntegrityViolationException | ConstraintViolationException e) {
            throw new ValidationException("Пользователь с таким email уже существует");
        }
    }

    @Override
    public void deleteUser(int id) {
        try {
            userRepository.deleteById(id);
        } catch (EmptyResultDataAccessException e) {
            throw new EntryNotFoundException("Отсутствует пользователь с id: " + id);
        }
    }

    public User getUserOrThrow(int userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new EntryNotFoundException("Отсутствует пользователь с id: " + userId));
    }
}
