package by.itransition.task4.entity;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import java.time.LocalDate;

@Data
@Entity
@Table(schema = "users", name = "user")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(nullable = false, unique = true)
    private String username;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private final LocalDate dateOfRegistration = LocalDate.now();

    @Column(nullable = false)
    private LocalDate dateOfLastLogin = LocalDate.now();

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Status status = Status.UNBLOCKED;
}
