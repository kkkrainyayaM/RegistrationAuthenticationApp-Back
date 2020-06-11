package by.itransition.task4.dto;

import by.itransition.task4.entity.Status;
import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import java.time.LocalDate;

@Data
public class UserDto {

    private long id;

    @NotEmpty
    private String name;

    @Email
    @NotEmpty
    private String email;

    @NotEmpty
    private LocalDate dateOfRegistration;

    @NotEmpty
    private LocalDate dateOfLastLogin;

    @NotEmpty
    private Status status;
}
