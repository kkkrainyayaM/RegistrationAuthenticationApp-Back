package by.itransition.task4.exceptions;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.servlet.http.HttpServletRequest;

import static org.springframework.http.HttpStatus.CONFLICT;
import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;
import static org.springframework.http.HttpStatus.NOT_FOUND;

@ControllerAdvice
@Slf4j
@RequiredArgsConstructor
public class ExceptionControllerAdvice {

    @ExceptionHandler(Exception.class)
    @ResponseStatus(value = INTERNAL_SERVER_ERROR)
    @ResponseBody
    public ErrorDto handleInternalError(HttpServletRequest request, Exception exception) {
        log.error( "Exception at {}", request.getRequestURI(), exception );
        return handle( INTERNAL_SERVER_ERROR.getReasonPhrase(), exception );
    }

    @ExceptionHandler(UserNotFoundException.class)
    @ResponseStatus(value = NOT_FOUND)
    @ResponseBody
    public ErrorDto handleUserNotFoundException(HttpServletRequest request, UserNotFoundException exception) {
        return handle( NOT_FOUND.getReasonPhrase(), exception );
    }

    @ExceptionHandler(CredentialsAlreadyExistsException.class)
    @ResponseStatus(value = CONFLICT)
    @ResponseBody
    public ErrorDto handleCredentialsAlreadyExists(HttpServletRequest request, CredentialsAlreadyExistsException exception) {
        return handle( CONFLICT.getReasonPhrase(), exception );
    }

    private ErrorDto handle(String code, Exception exception) {
        val errorDto = new ErrorDto();
        errorDto.setCode( code );
        errorDto.setMessage( exception.getMessage() );
        return errorDto;
    }
}
