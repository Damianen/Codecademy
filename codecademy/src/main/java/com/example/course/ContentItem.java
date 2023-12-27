package com.example.course;

import java.time.LocalDate;

public abstract class ContentItem {
    protected int id;
    protected String title;
    protected LocalDate publicationDate;
    protected String description;
    protected Status status;

    public enum Status{
        CONCEPT,
        ACTIVE,
        ARCHIVED
    }

    public ContentItem(int id, String title, LocalDate publicationDate, Status status, String description){
        this.id = id;
        this.title = title;
        this.publicationDate = publicationDate;
        this.status = status;
        this.description = description;
    }

    public void seContentItemtId(int id) {
        this.id = id;
    }
    public int getContentItemId() {
        return id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    public void setPublicationDate(LocalDate publicationDate) {
        this.publicationDate = publicationDate;
    }
    public LocalDate getPublicationDate() {
        return publicationDate;
    }

    public void setStatus(Status status) {
        this.status = status;
    }
    public Status getStatus() {
        return status;
    }

    public void setDescription(String description) {
        this.description = description;
    }
    public String getDescription() {
        return description;
    }
    
}
