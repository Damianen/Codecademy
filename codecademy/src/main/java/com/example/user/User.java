package com.example.user;

import java.time.LocalDate;
import java.util.ArrayList;

import com.example.database.DatabaseEnrollment;

public class User {

    private String email;
    private String name;
    private LocalDate dateOfBirth;
    private String address;
    private String residence;
    private String country;
    private Gender gender;
    private ArrayList<Enrollment> enrollments;
    
    public enum Gender{
        M,
        F
    }

     public User(String email, String name, LocalDate dateOfBirth, Gender gender, String address, String residence, String country){
        this.email = email;
        this.name = name;
        this.dateOfBirth = dateOfBirth;
        this.gender = gender;
        this.address = address;
        this.residence = residence;
        this.country = country;

        this.enrollments = DatabaseEnrollment.getUserEnrollments(email);
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

    public void setDateOfBirth(LocalDate dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }

    public void setAdress(String address) {
        this.address = address;
    }

    public String getAdress() {
        return address;
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

    public void setEnrollments(ArrayList<Enrollment> enrollments) {
        this.enrollments = enrollments;
    }

    public ArrayList<Enrollment> getEnrollments() {
        return enrollments;
    }

    public void addEnrollment(){
    
    }

    public void generateUserCertificates(){
       
    }


}


