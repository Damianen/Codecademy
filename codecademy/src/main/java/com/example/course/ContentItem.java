package com.example.course;

import java.time.LocalDate;

public class ContentItem {
    protected int id;
    protected LocalDate publicationDate;
    protected String status;
    protected String description;
    protected int trackingNumber;

    public void setId(int id) {
        this.id = id;
    }
    public int getId() {
        return id;
    }

    public void setPublicationDate(LocalDate publicationDate) {
        this.publicationDate = publicationDate;
    }
    public LocalDate getPublicationDate() {
        return publicationDate;
    }

    public void setStatus(String status) {
        this.status = status;
    }
    public String getStatus() {
        return status;
    }

    public void setDescription(String description) {
        this.description = description;
    }
    public String getDescription() {
        return description;
    }

    public void setTrackingNumber(int trackingNumber) {
        this.trackingNumber = trackingNumber;
    }
    public int getTrackingNumber() {
        return trackingNumber;
    }

    


    public void ContentItem(){
        
    }
}
