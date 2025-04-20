package edu.java.controller.exception;


import edu.java.controller.dto.response.ApiErrorResponse;
import java.util.ArrayList;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;


@RestControllerAdvice
public class HandleControllerException {

    @ExceptionHandler(ChatAlreadyExistsException.class)
    public ResponseEntity<ApiErrorResponse> handleChatAlreadyExistsException(Exception exception) {
        ApiErrorResponse apiErrorResponse = new ApiErrorResponse(
            "User exists",
                HttpStatus.BAD_REQUEST.toString(),
                exception.getClass().getName(),
                exception.getMessage(),
                new ArrayList<>()
        );
        return new ResponseEntity<>(apiErrorResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ChatIdNotFoundException.class)
    public ResponseEntity<ApiErrorResponse> handleChatIdNotFoundException(Exception exception) {
        var apiErrorResponse = new ApiErrorResponse(
                "Chat id not found",
                HttpStatus.BAD_REQUEST.toString(),
                exception.getClass().getName(),
                exception.getMessage(),
                new ArrayList<>()
        );
        return new ResponseEntity<>(apiErrorResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(IncorrectParametersException.class)
    public ResponseEntity<ApiErrorResponse> handleIncorrectParametersException(Exception exception) {
        var apiErrorResponse = new ApiErrorResponse(
                "Incorrect parameter(s)",
                HttpStatus.BAD_REQUEST.toString(),
                exception.getClass().getName(),
                exception.getMessage(),
                new ArrayList<>()
        );
          return new ResponseEntity<>(apiErrorResponse, HttpStatus.BAD_REQUEST);
    }
}
