package org.rail.spring2024.exception;


/**
 *  Exception that thrown when product is not found in database
 */
public class ProductNotFoundException extends Exception {
    /**
     * overrides default constructor and prints message
     * @param message message to be printed to user
     */
    public ProductNotFoundException(String message) {
        super(message);
    }
}
