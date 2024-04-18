package org.rail.spring2024.exception;

import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import jakarta.validation.ConstraintViolationException;
import org.rail.spring2024.model.ProductType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.NOT_FOUND;

/**
 * Class that intercepts exceptions when they occur
 */
@ControllerAdvice(annotations = RestController.class)
public class GlobalExceptionHandler {

    /**
     * field for making logs to the console about exceptions
     */
    private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    /**
     * handles {@link ConstraintViolationException} and prints it to the user
     * @param exception {@link ConstraintViolationException} object that was intercepted
     * @return {@link ResponseEntity} object with error cause and {@link org.springframework.http.HttpStatus}
     */
    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<Map<String, String>> handleInvalidArgument(ConstraintViolationException exception) {
        Map<String, String> errors = new HashMap<>();
        logger.error(exception.getMessage());
        exception.getConstraintViolations().forEach(error ->
                errors.put(error.getPropertyPath().toString(), error.getMessage()));
        return new ResponseEntity<>(errors, BAD_REQUEST);
    }

    /**
     * handles {@link InvalidFormatException} and prints it to the user
     * if exception was type of {@link ProductType} class it print specific message for it
     * @param exception {@link InvalidFormatException} object that was intercepted
     * @return {@link ResponseEntity} object with error cause and {@link org.springframework.http.HttpStatus}
     */
    @ExceptionHandler(InvalidFormatException.class)
    public ResponseEntity<String> handleInvalidFormat(InvalidFormatException exception) {
        logger.error(exception.getMessage());
        if (exception.getTargetType().isAssignableFrom(ProductType.class)) {
            String values = "Product type must be any of " + Arrays.toString(ProductType.values());
            return new ResponseEntity<>(values, BAD_REQUEST);
        } else {
            return new ResponseEntity<>("Invalid user input", BAD_REQUEST);
        }
    }

    /**
     * handles {@link ProductNotFoundException} and prints it to the user
     * @param exception {@link ProductNotFoundException} object that was intercepted
     * @return {@link ResponseEntity} object with error cause and {@link org.springframework.http.HttpStatus}
     */
    @ExceptionHandler(ProductNotFoundException.class)
    public ResponseEntity<String> handleProductNotFound(ProductNotFoundException exception) {
        logger.error(exception.getMessage());
        return new ResponseEntity<>(exception.getMessage(), NOT_FOUND);
    }

    /**
     * handles unknown exception that was thrown runtime
     * @param exception {@link RuntimeException} object that was intercepted
     * @return {@link ResponseEntity} object with error cause and {@link org.springframework.http.HttpStatus}
     */
    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<String> handleRuntimeException(RuntimeException exception) {
        logger.error(exception.getMessage());
        return new ResponseEntity<>(exception.getMessage(), BAD_REQUEST);
    }


}
