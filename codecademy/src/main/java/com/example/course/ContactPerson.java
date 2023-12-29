package com.example.course;

public class ContactPerson {

    private String email;
    private String name;

    public ContactPerson(String email, String name){
        this.email = email;
        this.name = name;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getEmail() {
        return email;
    }

    public void setName(String name) {
        this.name = name;
    }
    
    public String getName() {
        return name;
    }
}
