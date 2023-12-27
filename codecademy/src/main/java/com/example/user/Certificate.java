package com.example.user;

public class Certificate {
    private int id;
    private int rating;
    private String employeeName;

    public Certificate(int id, int rating, String employeeName){
        this.id = id;
        this.rating = rating;
        this.employeeName = employeeName;
    }

    public void setId(int id) {
        this.id = id;
    }
    public int getId() {
        return id;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }
    public int getRating() {
        return rating;
    }
    
    public void setEmployeeName(String employeeName) {
        this.employeeName = employeeName;
    }
    public String getEmployeeName() {
        return employeeName;
    }
}
