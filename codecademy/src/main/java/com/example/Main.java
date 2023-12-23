package com.example;

import com.example.course.Course;
import com.example.course.Course.DifficultyLevel;
import com.example.database.DatabaseCourse;
import com.example.javafx.GUI;

public class Main {
    public static void main(String[] args) {
        //GUI gui = new GUI();
        //gui.startGui(args);

        try{
            DatabaseCourse.updateCourse("test", "test", "test", "test", DifficultyLevel.ADVANCED);
        }catch(Exception e){
            e.printStackTrace();
        }
        

        
    }
}
