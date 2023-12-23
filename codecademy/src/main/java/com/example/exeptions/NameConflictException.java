package com.example.exeptions;

public class NameConflictException extends Exception {
    private String entityName;

    public NameConflictException(String entityName) {
        super("Update failed: Entity with name '" + entityName + "' already exists.");
        this.entityName = entityName;
    }

    public String getEntityName() {
        return entityName;
    }
}
