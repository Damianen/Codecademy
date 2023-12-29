package com.example.exeptions;

public class CannotBeEmptyException extends Exception {
    private String errorMessage;

    public CannotBeEmptyException(String errorMessage) {
        super(errorMessage);
        this.errorMessage = errorMessage;
    }

    public String getEntityName() {
        return errorMessage;
    }
}
