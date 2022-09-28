package ru.practicum.explorewithme.services.impl;

import ru.practicum.explorewithme.dto.user.UserDto;
import ru.practicum.explorewithme.dto.user.mapper.UserMapper;
import lombok.RequiredArgsConstructor;
import ru.practicum.explorewithme.model.User;
import org.springframework.stereotype.Service;
import ru.practicum.explorewithme.repositories.UserRepository;
import ru.practicum.explorewithme.services.UserService;

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
}
