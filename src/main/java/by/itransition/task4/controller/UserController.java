package by.itransition.task4.controller;

import by.itransition.task4.dto.LoginDto;
import by.itransition.task4.dto.UserDto;
import by.itransition.task4.entity.Status;
import by.itransition.task4.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

@CrossOrigin(origins = "http://localhost:4200", maxAge = 3600)
@RestController
@RequestMapping(value = "/api/v1",
        consumes = MediaType.APPLICATION_JSON_VALUE,
        produces = MediaType.APPLICATION_JSON_VALUE)
@Api(value = "Registration/Authorization system")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @ApiOperation(value = "Authenticate user", response = UserDto.class)
    @PostMapping("/authentication")
    public UserDto authenticateUser(@Valid @RequestBody LoginDto loginDto) {
        return userService.authenticate(loginDto);
    }

    @ApiOperation(value = "Add a new user", response = UserDto.class)
    @PostMapping("/users")
    public UserDto registerUser(@Valid @RequestBody UserDto userDto) {
        return userService.add(userDto);
    }

    @ApiOperation(value = "Update status of user", response = List.class)
    @PutMapping("/users/status")
    public List<UserDto> updateStatusBlocked(@RequestParam Status status,
                                             @Valid @RequestBody List<Long> ids) {
        return userService.updateStatus(ids, status);
    }

    @ApiOperation(value = "View a list of all users", response = List.class)
    @GetMapping("/users")
    public List<UserDto> getAll() {
        return userService.getAll();
    }
    @ApiOperation(value = "Delete a list or a single user", response = List.class)
    @DeleteMapping("/users")
    public void deleteUsers(@Valid @RequestBody List<Long> ids) {
        userService.delete(ids);
    }
}
