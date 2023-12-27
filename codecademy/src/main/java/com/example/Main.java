package com.example;

import java.time.LocalDate;
import java.util.ArrayList;

import com.example.course.Course.DifficultyLevel;
import com.example.course.Webcast;
import com.example.course.ContentItem.Status;
import com.example.database.DatabaseCertificate;
import com.example.database.DatabaseCourse;
import com.example.database.DatabaseEnrollment;
import com.example.database.DatabaseUser;
import com.example.database.DatabaseWebcast;
import com.example.javafx.GUI;
import com.example.user.Enrollment;
import com.example.user.User;
import com.example.user.User.Gender;

public class Main {
    public static void main(String[] args) {
        // GUI gui = new GUI();
        // gui.startGui(args);

        try {

            
            
            System.out.println(DatabaseWebcast.readWebcast(1).getSpeaker().getOranization());

            
            

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
