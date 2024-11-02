package com.rfrancos.crm.exceptions;

public class FileEmptyException extends RuntimeException {
    public FileEmptyException(String message) {
        super(message);
    }
}
