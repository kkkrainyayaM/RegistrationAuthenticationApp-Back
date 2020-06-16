package by.itransition.task4.service;

import by.itransition.task4.dto.LoginDto;
import by.itransition.task4.dto.SignUpDto;
import by.itransition.task4.dto.UserDto;
import by.itransition.task4.entity.Status;

import java.util.List;

public interface UserService {

    /**
     * Add a new user
     *
     * @param user - new User
     * @return added user
     */
    UserDto add(SignUpDto user);

    /**
     * Authenticate user by surname and password
     *
     * @param loginDto - surname and password
     * @return existed user
     */
    UserDto authenticate(LoginDto loginDto);

    /**
     * Delete users by ID
     *
     * @param id - user ID
     */
    int delete(List<Long> id);

    /**
     * Get list of all users
     *
     * @return a list of all users
     */
    List<UserDto> getAll();

    /**
     * Block or unblock users by ID
     *
     * @param id - user ID
     * @return updated user
     */
    List<UserDto> updateStatus(List<Long> id, Status status);

}
