package com.example.user;

import java.util.HashMap;

public class Progress {
    private HashMap<String, String> enrollDate = new HashMap<String, String>();
    private String contentItem;

    public void setEnrollDate(HashMap<String, String> enrollDate) {
        this.enrollDate = enrollDate;
    }
    public HashMap<String, String> getEnrollDate() {
        return enrollDate;
    }

    public void setContentItem(String contentItem) {
        this.contentItem = contentItem;
    }
    public String getContentItem() {
        return contentItem;
    }
    



    public void progress(){

    }
}
