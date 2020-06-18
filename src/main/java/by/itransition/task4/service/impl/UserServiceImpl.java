package by.itransition.task4.service.impl;

import by.itransition.task4.dto.LoginDto;
import by.itransition.task4.dto.SignUpDto;
import by.itransition.task4.dto.StatusUpdateDto;
import by.itransition.task4.dto.UserDto;
import by.itransition.task4.entity.Status;
import by.itransition.task4.entity.UserDetailsImpl;
import by.itransition.task4.exceptions.CredentialsAlreadyExistsException;
import by.itransition.task4.mapper.UserMapper;
import by.itransition.task4.repository.UserRepository;
import by.itransition.task4.security.jwt.JwtHelper;
import by.itransition.task4.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final JwtHelper jwtHelper;
    private final AuthenticationManager authenticationManager;
    private final PasswordEncoder encoder;

    @Override
    public UserDto add(SignUpDto signUpDto) {
        if (userRepository.existsByUsername(signUpDto.getUsername())
                || userRepository.existsByEmail(signUpDto.getEmail())) {
            throw new CredentialsAlreadyExistsException(signUpDto.getUsername());
        }
        signUpDto.setPassword(encoder.encode(signUpDto.getPassword()));
        log.info("Save user: {}", signUpDto);
        val user = userMapper.sigUpDtoToUser(signUpDto);
        val savedUser = userRepository.save(user);
        return userMapper.userToUserDto(savedUser);
    }

    @Transactional
    @Override
    public UserDto authenticate(LoginDto loginDto) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginDto.getUsername(), loginDto.getPassword())
        );
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtHelper.generateJwtToken(authentication);
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        userRepository.updateDateOfLastLogin(LocalDate.now(), userDetails.getId());
        return userMapper.userDetailsToUserDto(jwt, userDetails);
    }

    @Transactional
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
    public List<UserDto> updateStatus(StatusUpdateDto status) {
        val usersForUpdate = userRepository.getByIdIn(status.getIds());
        usersForUpdate.forEach(user -> user.setStatus(status.getStatus()));
        userRepository.saveAll(usersForUpdate);
        log.info("Updated users: {}", usersForUpdate);
        return usersForUpdate.stream()
                .map(userMapper::userToUserDto)
                .collect(Collectors.toList());
    }
}
