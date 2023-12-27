package com.example.user;

import com.example.course.Course;

import java.time.LocalDate;
import java.util.ArrayList;

import com.example.database.DatabaseCourse;
import com.example.database.DatabaseEnrollment;
import com.example.database.DatabaseCertificate;

public class Enrollment {
    private int id;
    private LocalDate enrollmentDate;

    private Course course;
    private ArrayList<Progress> progresses;
    private Certificate certificate = null;

    public Enrollment(int id, LocalDate enrollmentDate, String courseTitle){
        this.id = id;
        this.enrollmentDate = enrollmentDate;
        
        this.course = DatabaseCourse.readCourse(courseTitle);
        //this.progresses = DatabaseProgress.getEnrollmentProgresses();

        this.certificate = DatabaseCertificate.getEnrollmentCertificate(this.id);
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setEnrollmentDate(LocalDate enrollmentDate) {
        this.enrollmentDate = enrollmentDate;
    } 
    public LocalDate getEnrollmentDate() {
        return enrollmentDate;
    }

    public Certificate getCertificate() {
        return certificate;
    }

    public void setCertificate(Certificate certificate) {
        this.certificate = certificate;
    }

    public Course getCourse() {
        return course;
    }

    public void setCourse(Course course) {
        this.course = course;
    }

    public void setProgresses(ArrayList<Progress> progresses) {
        this.progresses = progresses;
    }
    public ArrayList<Progress> getProgresses() {
        return progresses;
    }

    public boolean addCertificate(){
        
        try {
            DatabaseCertificate.createCertificate(this);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
