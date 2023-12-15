package com.example.course;

import java.util.ArrayList;

public class Course {
    enum DifficultyLevel {
        BEGINNER,
        ADVANCED,
        EXPERT
    }
    
    private String name;
    private String subject;
    private String introText;
    private DifficultyLevel difficulty;
    private ArrayList<Module> modules;

    public Course(String name,String subject,String introText, DifficultyLevel difficulty) {
        this.name = name;
        this.subject = subject;
        this.introText = introText;
        this.difficulty = difficulty;
        this.modules = new ArrayList<Module>();
    }

    public void addModule(Module module) {
        modules.add(module);
    }

    public Course generateRecomendedCourse() {
        return null;
    }
}
