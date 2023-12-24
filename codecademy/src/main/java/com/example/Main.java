package com.example;

import java.time.LocalDate;

import com.example.course.Course.DifficultyLevel;
import com.example.database.DatabaseCourse;
import com.example.database.DatabaseEnrollment;
import com.example.database.DatabaseUser;
import com.example.javafx.GUI;
import com.example.user.User;
import com.example.user.User.Gender;

public class Main {
    public static void main(String[] args) {
        //GUI gui = new GUI();
        //gui.startGui(args);

        try {
            DatabaseEnrollment.deleteEnrollment(10);
            
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
