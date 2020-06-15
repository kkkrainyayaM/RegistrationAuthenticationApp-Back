package by.itransition.task4.dto;

import by.itransition.task4.entity.Status;
import lombok.Data;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import java.time.LocalDate;

@Data
@RequiredArgsConstructor
public class JwtDto {

    @NonNull
    private String token;

    private String type = "Bearer";

    @NonNull
    private long id;

    @NonNull
    private String username;

    @NonNull
    private String email;

    @NonNull
    private LocalDate dateOfRegistration;

    @NonNull
    private LocalDate dateOfLastLogin;

    @NonNull
    private Status status;
}
