package uz.pdp.olchauz.exception.handler;

import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import uz.pdp.olchauz.exception.UserNotFoundException;

@RestControllerAdvice
public class MyExceptionHandler {

    @ExceptionHandler(UserNotFoundException.class)
    public String userNotFoundException(UserNotFoundException u) {
        return u.getMessage();
    }
}
