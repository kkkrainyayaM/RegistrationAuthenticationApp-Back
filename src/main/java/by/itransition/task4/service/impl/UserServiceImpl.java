package by.itransition.task4.service.impl;

import by.itransition.task4.dto.UserDto;
import by.itransition.task4.entity.Status;
import by.itransition.task4.mapper.UserMapper;
import by.itransition.task4.repository.UserRepository;
import by.itransition.task4.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;

    @Override
    public UserDto add(UserDto userDto) {
        log.info("Save user: {}", userDto);
        val user = userMapper.userDtoToUser(userDto);
        val savedUser = userRepository.save(user);
        return userMapper.userToUserDto(savedUser);
    }

    @Override
    public int delete(List<Long> ids) {
        log.info("Users ids = {}", ids);
        int numberOfDeleted = userRepository.deleteByIdIn(ids);
        log.info("Deleted {} users", numberOfDeleted);
        return numberOfDeleted;
    }

    @Override
    public List<UserDto> getAll() {
        val list = userRepository.findAll().stream()
                .map(userMapper::userToUserDto)
                .collect(Collectors.toList());
        log.info("Get list of users: {}", list);
        return list;
    }

    @Override
    public List<UserDto> updateStatus(List<Long> ids, Status status) {
        val usersForUpdate = userRepository.getByIdIn(ids);
        usersForUpdate.forEach(user -> user.setStatus(status));
        userRepository.saveAll(usersForUpdate);
        log.info("Updated users: {}", usersForUpdate);
        return usersForUpdate.stream()
                .map(userMapper::userToUserDto)
                .collect(Collectors.toList());
    }
}
