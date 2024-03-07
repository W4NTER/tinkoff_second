package edu.java.bot.controller.exception;

import edu.java.bot.controller.dto.response.ApiErrorResponse;
import java.util.ArrayList;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class HandleControllerException {

    @ExceptionHandler(IdNotFoundException.class)
    public ResponseEntity<ApiErrorResponse> handleIdNotFound(Exception exception) {
        ApiErrorResponse apiErrorResponse = new ApiErrorResponse(
            "Id for this user not found",
                HttpStatus.NOT_FOUND.toString(),
                exception.getClass().getName(),
                exception.getMessage(),
                new ArrayList<>()
        );
        return new ResponseEntity<>(apiErrorResponse, HttpStatus.NOT_FOUND);
    }
}
