package by.itransition.task4.mapper;

import by.itransition.task4.dto.SignUpDto;
import by.itransition.task4.dto.UserDto;
import by.itransition.task4.entity.User;
import by.itransition.task4.entity.UserDetailsImpl;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.time.LocalDate;

@Mapper(componentModel = "spring")
public interface UserMapper {

    UserDto userToUserDto(User user);

    User userDtoToUser(UserDto userDto);

    UserDetailsImpl userToUserDetails(User user);

    @Mapping(source = "token", target = "token")
    UserDto userDetailsToUserDto(String token, UserDetailsImpl userDetails);

    User sigUpDtoToUser(SignUpDto signUpDto);
}
