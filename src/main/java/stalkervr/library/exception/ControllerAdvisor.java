package stalkervr.library.exception;

import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import static java.time.LocalDateTime.now;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.NOT_FOUND;

@RestControllerAdvice
public class ControllerAdvisor {

    @ExceptionHandler(BadRequestException.class)
    @ResponseStatus(BAD_REQUEST)
    public ErrorResponse handleBadRequestException(BadRequestException exception){
        return ErrorResponse.builder()
                .message(exception.getLocalizedMessage())
                .status(BAD_REQUEST)
                .timestamp(now())
                .build();
    }

    @ExceptionHandler(NotFoundException.class)
    @ResponseStatus(NOT_FOUND)
    public ErrorResponse handleNotFoundException(NotFoundException exception){
        return ErrorResponse.builder()
                .message(exception.getLocalizedMessage())
                .status(NOT_FOUND)
                .timestamp(now())
                .build();
    }
}
