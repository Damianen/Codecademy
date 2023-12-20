package com.example.user;

import java.util.ArrayList;

public class User {

    private String email;
    private String name;
    private String dateOfBirth;
    private String adress;
    private String residence;
    private String country;
    private Gender gender;
    private ArrayList<Integer> enrollments = new ArrayList<Integer>();
    
    enum Gender{
        M,
        F
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

    public void setDateOfBirth(String dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getDateOfBirth() {
        return dateOfBirth;
    }

    public void setAdress(String adress) {
        this.adress = adress;
    }

    public String getAdress() {
        return adress;
    }

    public void setResidence(String residence) {
        this.residence = residence;
    }

    public String getResidence() {
        return residence;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getCountry() {
        return country;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    public Gender getGender() {
        return gender;
    }

    public void setEnrollments(ArrayList<Integer> enrollments) {
        this.enrollments = enrollments;
    }

    public ArrayList<Integer> getEnrollments() {
        return enrollments;
    }

    

    public void User(){

    }

    public void addEnrollment(){
    
    }

    public void generateUserCertificates(){
       
    }


}


