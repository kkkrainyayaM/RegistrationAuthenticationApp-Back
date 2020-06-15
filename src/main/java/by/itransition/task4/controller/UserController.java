package by.itransition.task4.controller;

import by.itransition.task4.dto.JwtDto;
import by.itransition.task4.dto.LoginDto;
import by.itransition.task4.dto.MessageDto;
import by.itransition.task4.dto.UserDto;
import by.itransition.task4.entity.Status;
import by.itransition.task4.repository.UserRepository;
import by.itransition.task4.security.jwt.JwtUtils;
import by.itransition.task4.security.services.UserDetailsImpl;
import by.itransition.task4.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
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
@RequestMapping(value = "/",
        consumes = MediaType.APPLICATION_JSON_VALUE,
        produces = MediaType.APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
public class UserController {

    private final UserRepository userRepository;
    private final UserService userService;
    private final JwtUtils jwtUtils;
    private final AuthenticationManager authenticationManager;

    @PostMapping("/users/authorization")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginDto loginDto) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginDto.getUsername(), loginDto.getPassword())
        );
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtUtils.generateJwtToken(authentication);
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        return ResponseEntity.ok(new JwtDto(jwt, userDetails.getId(), userDetails.getUsername(),
                userDetails.getEmail(), userDetails.getDateOfRegistration(), userDetails.getDateOfLastLogin(), userDetails.getStatus()));
    }

    @PostMapping("/users")
    public ResponseEntity<?> registerUser(@Valid @RequestBody UserDto userDto) {
        if (userRepository.existsByUsername(userDto.getUsername())) {
            return ResponseEntity
                    .badRequest()
                    .body(new MessageDto("Error: Username is already taken!"));
        }
        if (userRepository.existsByEmail(userDto.getEmail())) {
            return ResponseEntity
                    .badRequest()
                    .body(new MessageDto("Error: Email is already taken!"));
        }
        userService.add(userDto);
        return ResponseEntity.ok(new MessageDto("User registered successfully!"));
    }

    @PutMapping("/users/status")
    public List<UserDto> updateStatusBlocked(@RequestParam Status status,
                                             @Valid @RequestBody List<Long> ids) {
        return userService.updateStatus(ids, Status.BLOCKED);
    }

    @DeleteMapping("/users")
    public void deleteUsers(@Valid @RequestBody List<Long> ids) {
        userService.delete(ids);
    }
}
