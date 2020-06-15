package by.itransition.task4.exceptions;

public class CredentialsAlreadyExistsException extends RuntimeException {

    public CredentialsAlreadyExistsException(String message) {
        super("User already exist with credential: " + message);
    }
}
