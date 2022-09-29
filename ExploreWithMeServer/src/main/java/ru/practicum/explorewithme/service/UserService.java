package ru.practicum.explorewithme.service;

import ru.practicum.explorewithme.dto.user.UserDto;

import java.util.List;

public interface UserService {

    List<UserDto> getUsersById(int[] ids);

    UserDto addUser(UserDto userDto);

    void deleteUser(int id);
}

