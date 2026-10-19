package dk.missingbikes.controller;

import dk.missingbikes.exception.InvalidInputException;
import dk.missingbikes.exception.NotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * Turns the business exceptions thrown by the service layer into JSON error
 * responses for the API controllers.
 *
 * It applies only to @RestController classes. Page controllers catch these
 * exceptions themselves and render the form again with a message, because a
 * browser submitting a form expects HTML, not JSON.
 */
@RestControllerAdvice(annotations = RestController.class)
public class GlobalExceptionHandler {

    @ExceptionHandler(InvalidInputException.class)
    public ProblemDetail handleInvalidInput(InvalidInputException exception){
        return ProblemDetail.forStatusAndDetail(HttpStatus.BAD_REQUEST, exception.getMessage());
    }

    @ExceptionHandler(NotFoundException.class)
    public ProblemDetail handleNotFound(NotFoundException exception){
        return ProblemDetail.forStatusAndDetail(HttpStatus.NOT_FOUND, exception.getMessage());
    }
}
