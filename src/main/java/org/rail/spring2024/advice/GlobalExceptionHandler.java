package org.rail.spring2024.advice;

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

@ControllerAdvice(annotations = RestController.class)
public class GlobalExceptionHandler {

    private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<Map<String, String>> handleInvalidArgument(ConstraintViolationException exception) {
        Map<String, String> errors = new HashMap<>();
        logger.error(exception.getMessage());
        exception.getConstraintViolations().forEach(error ->
                errors.put(error.getPropertyPath().toString(), error.getMessage()));
        return new ResponseEntity<>(errors, BAD_REQUEST);
    }

    @ExceptionHandler(InvalidFormatException.class)
    public ResponseEntity<String> handleOfflineBankApi(InvalidFormatException exception) {
        logger.error(exception.getMessage());
        if (exception.getTargetType().isAssignableFrom(ProductType.class)) {
            String values = "Product type must be any of " + Arrays.toString(ProductType.values());
            return new ResponseEntity<>(values, BAD_REQUEST);
        } else {
            return new ResponseEntity<>("Invalid user input", BAD_REQUEST);
        }
    }
}
