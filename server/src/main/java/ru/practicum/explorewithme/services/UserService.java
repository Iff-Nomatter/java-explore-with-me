package ru.practicum.explorewithme.services;

import ru.practicum.explorewithme.dto.user.UserDto;
import ru.practicum.explorewithme.model.User;

import java.util.List;

public interface UserService {

    List<UserDto> getUsersById(int[] ids);

    UserDto addUser(UserDto userDto);

    void deleteUser(int id);

    User getUserOrThrow(int userId);
}

