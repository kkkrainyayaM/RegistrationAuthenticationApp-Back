package by.itransition.task4.mapper;

import by.itransition.task4.dto.UserDto;
import by.itransition.task4.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface UserMapper {

    UserDto userToUserDto(User user);

    @Mapping(target = "role", constant = "USER")
    User userDtoToUser(UserDto userDto);
}
