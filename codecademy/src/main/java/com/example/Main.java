package com.example;

import java.sql.DatabaseMetaData;
import java.time.LocalDate;

import com.example.course.ContentItem;
import com.example.course.Course;
import com.example.course.Webcast;
import com.example.course.ContentItem.Status;
import com.example.database.DatabaseContactPerson;
import com.example.database.DatabaseCourse;
import com.example.database.DatabaseEnrollment;
import com.example.database.DatabaseModule;
import com.example.database.DatabaseUser;
import com.example.database.DatabaseWebcast;
import com.example.javafx.GUI;
import com.example.user.User.Gender;

public class Main {
    public static void main(String[] args) {
        GUI gui = new GUI();
        gui.startGui(args);
               
    }
}
