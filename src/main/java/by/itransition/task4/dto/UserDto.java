package by.itransition.task4.dto;

import by.itransition.task4.entity.Status;
import lombok.Data;

import javax.validation.constraints.Email;
import java.time.LocalDate;

@Data
public class UserDto {

    private String token;

    private long id;

    private String username;

    @Email
    private String email;

    private LocalDate dateOfRegistration;

    private LocalDate dateOfLastLogin;

    private Status status;
}
