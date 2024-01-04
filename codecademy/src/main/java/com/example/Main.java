package com.example;

import java.sql.DatabaseMetaData;
import java.time.LocalDate;
import java.util.HashMap;

import com.example.course.ContentItem;
import com.example.course.Course;
import com.example.course.Webcast;
import com.example.course.Module;
import com.example.course.ContentItem.Status;
import com.example.database.DatabaseCertificate;
import com.example.database.DatabaseContactPerson;
import com.example.database.DatabaseContentItem;
import com.example.database.DatabaseCourse;
import com.example.database.DatabaseEnrollment;
import com.example.database.DatabaseModule;
import com.example.database.DatabaseOverview;
import com.example.database.DatabaseProgress;
import com.example.database.DatabaseUser;
import com.example.database.DatabaseWebcast;
import com.example.javafx.GUI;
import com.example.user.User.Gender;

import javafx.scene.control.ContentDisplay;

public class Main {
    public static void main(String[] args) {
        GUI gui = new GUI();
        //gui.startGui(args);

        try {
            
            Course course = DatabaseCourse.readCourse("ruben");
            
            System.out.println(DatabaseOverview.getAveragePogressPercentagePerCourseModule(course));

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
