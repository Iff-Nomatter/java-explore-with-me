package ru.practicum.explorewithme.service.impl;

import ru.practicum.explorewithme.controller.exceptionHandling.exception.EntryNotFoundException;
import ru.practicum.explorewithme.dto.user.UserDto;
import ru.practicum.explorewithme.dto.user.mapper.UserMapper;
import lombok.RequiredArgsConstructor;
import ru.practicum.explorewithme.model.User;
import org.springframework.stereotype.Service;
import ru.practicum.explorewithme.repository.UserRepository;
import ru.practicum.explorewithme.service.UserService;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Override
    public List<UserDto> getUsersById(int[] ids) {
        return UserMapper.userToDtoList(userRepository.findAllById(
                Arrays.stream(ids).boxed().collect(Collectors.toList())));
    }

    @Override
    public UserDto addUser(UserDto userDto) {
        User user = userRepository.save(UserMapper.dtoToUser(userDto));
        return UserMapper.userToDto(user);
    }

    @Override
    public void deleteUser(int id) {
        userRepository.deleteById(id);
    }

    @Override
    public User getUserOrThrow(int userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new EntryNotFoundException("Отсутствует пользователь с id: " + userId));
    }
}
