package com.example.course;

import com.example.database.DatabaseSpeaker;

import java.time.LocalDate;

public class Webcast extends ContentItem{
    private int id;
    private String url;
    private Speaker speaker;

    public Webcast(int contentItemID, String title, LocalDate publicationDate, Status status, String description, int id, String url, int speakerID){
        super(contentItemID, title, publicationDate, status, description);
        this.id = id;
        this.url = url;
        this.speaker = DatabaseSpeaker.readSpeaker(speakerID);
    }

    public int getContentItemId() {
        return super.id;
    }
    
    public void setContentItemId(int id) {
        super.id = id;
    }

    public int getId() {
        return id;
    }
    
    public void setId(int id) {
        this.id = id;
    }

    public void setUrl(String url) {
        this.url = url;
    }
    public String getUrl() {
        return url;
    }

    public void setSpeaker(Speaker speaker) {
        this.speaker = speaker;
    }
    public Speaker getSpeaker() {
        return speaker;
    }

}
