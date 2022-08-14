package com.publishers.exception;

/**
 * Represents an exception that is thrown in case of an error during file processing.
 *
 * @author Ana Peterlic
 */
public class FileProcessingException extends RuntimeException {

    public FileProcessingException(String message) {
        super(message);
    }
}
