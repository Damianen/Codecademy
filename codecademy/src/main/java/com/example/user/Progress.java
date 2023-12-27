package com.example.user;

import java.util.HashMap;

public class Progress {

    private int id;
    private int progressPercentage;
    private int contentItemID;
    private String userEmail;

    public Progress(int id, int progressPercentage, int contentItemID, String userEmail){
        this.progressPercentage = progressPercentage;
        this.contentItemID = contentItemID;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setContentItemID(int contentItemID) {
        this.contentItemID = contentItemID;
    }

    public int getContentItemID() {
        return contentItemID;
    }

    public void setProgressPercentage(int progressPercentage) {
        this.progressPercentage = progressPercentage;
    }

    public int getProgressPercentage() {
        return progressPercentage;
    }

    public String getUserEmail() {
        return userEmail;
    }
    
    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }
}
