package by.itransition.task4.dto;

import lombok.Data;

import javax.validation.constraints.Email;

@Data
public class SignUpDto {

    private String username;

    @Email
    private String email;

    private String password;
}
