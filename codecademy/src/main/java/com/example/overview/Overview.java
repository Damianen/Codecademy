package com.example.overview;

import com.example.course.Course;
import com.example.database.DatabaseCourse;
import com.example.database.DatabaseUser;
import com.example.user.User;

import javafx.collections.ObservableList;

public class Overview {
    private ObservableList<User> users;
    private ObservableList<Course> courses;

    public Overview(){
        this.users = DatabaseUser.getUserList();
        this.courses = DatabaseCourse.getCourseList();
    }

    public ObservableList<Course> getCourses() {
        return courses;
    }

    public void setCourses(ObservableList<Course> courses) {
        this.courses = courses;
    }

    public ObservableList<User> getUsers() {
        return users;
    }

    public void setUsers(ObservableList<User> users) {
        this.users = users;
    }

    
    public void generateGenderOverview(){
        
    }

    public void generateAverageCourseModulesProgress(){

    }

    public void generateCourseModulesProgressForUser(){

    }
    
    public void generateTopWebcasts(){

    }

    public void generateTopCourse(){

    }

    public void generateCourseComletions(){

    }
}
