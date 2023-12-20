package com.example.user;

import java.time.LocalDate;

public class Enrollment {
    private LocalDate enrollDate;
    private String certificate;
    private String course;
    private String progresses;

    public void setEnrollDate(LocalDate enrollDate) {
        this.enrollDate = enrollDate;
    } 
    public LocalDate getEnrollDate() {
        return enrollDate;
    }

    public void setCertificate(String certificate) {
        this.certificate = certificate;
    }
    public String getCertificate() {
        return certificate;
    }

    public void setCourse(String course) {
        this.course = course;
    }
    public String getCourse() {
        return course;
    }

    public void setProgresses(String progresses) {
        this.progresses = progresses;
    }
    public String getProgresses() {
        return progresses;
    }



    public void Enrollment(){

    }

    public void addCertificate(){

    }
}
