package com.example.course;

public class Webcast extends ContentItem {
    private String url;
    private String speaker;
    private String orginization;

    public void setUrl(String url) {
        this.url = url;
    }
    public String getUrl() {
        return url;
    }

    public void setSpeaker(String speaker) {
        this.speaker = speaker;
    }
    public String getSpeaker() {
        return speaker;
    }

    public void setOrginization(String orginization) {
        this.orginization = orginization;
    }
    public String getOrginization() {
        return orginization;
    }

    public Webcast(){
        
    }
}
