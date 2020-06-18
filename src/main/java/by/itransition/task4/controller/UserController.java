package by.itransition.task4.controller;

import by.itransition.task4.dto.LoginDto;
import by.itransition.task4.dto.SignUpDto;
import by.itransition.task4.dto.StatusUpdateDto;
import by.itransition.task4.dto.UserDto;
import by.itransition.task4.entity.Status;
import by.itransition.task4.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
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
@Controller
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

    @ApiOperation(value = "Add a new user", response = SignUpDto.class)
    @PostMapping("/users")
    public UserDto registerUser(@Valid @RequestBody SignUpDto signUpDto) {
        return userService.add(signUpDto);
    }

    @ApiOperation(value = "Update status of user", response = List.class)
    @PutMapping("/users/status")
    public List<UserDto> updateStatusBlocked(@Valid @RequestBody StatusUpdateDto status) {
        return userService.updateStatus(status);
    }

    @ApiOperation(value = "View a list of all users", response = List.class)
    @GetMapping(value = "/users", consumes = MediaType.ALL_VALUE)
    public List<UserDto> getAll() {
        return userService.getAll();
    }

    @ApiOperation(value = "Delete a list or a single user", response = List.class)
    @DeleteMapping("/users")
    public void deleteUsers(@Valid @RequestBody List<Long> ids) {
        userService.delete(ids);
    }
}
