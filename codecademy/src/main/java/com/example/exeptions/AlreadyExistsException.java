package com.example.exeptions;

public class AlreadyExistsException extends Exception {
    private String errorMessage;

    public AlreadyExistsException(String errorMessage) {
        super(errorMessage);
        this.errorMessage = errorMessage;
    }

    public String getEntityName() {
        return errorMessage;
    }
}
