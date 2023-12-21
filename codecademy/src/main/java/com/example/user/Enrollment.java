package com.example.user;

import java.time.LocalDate;
import java.util.ArrayList;

import com.example.course.Course;

public class Enrollment {
    private LocalDate enrollDate;
    private Certificate certificate;
    private Course course;
    private ArrayList<Progress> progresses;

    public void setEnrollDate(LocalDate enrollDate) {
        this.enrollDate = enrollDate;
    } 
    public LocalDate getEnrollDate() {
        return enrollDate;
    }

    public void setCertificate(Certificate certificate) {
        this.certificate = certificate;
    }
    public Certificate getCertificate() {
        return certificate;
    }

    public void setCourse(Course course) {
        this.course = course;
    }
    public Course getCourse() {
        return course;
    }

    public Enrollment(){
        
    }

    public void addCertificate(){

    }
}
