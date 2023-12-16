package com.example.course;

import java.util.ArrayList;



public class Course { 
    
    public enum DifficultyLevel {
        BEGINNER,
        ADVANCED,
        EXPERT
    }

    private String name;
    private String subject;
    private String introText;
    private DifficultyLevel difficultyLevel;
    private ArrayList<Module> modules;

    public Course(String name, String subject, String introText, DifficultyLevel difficulty) {
        this.name = name;
        this.subject = subject;
        this.introText = introText;
        this.difficultyLevel = difficulty;
        this.modules = new ArrayList<Module>();
    }

    public String getName() {
        return name;
    }

    public String getSubject() {
        return subject;
    }

    public String getDifficultyLevelString() {
        return difficultyLevel.toString().toLowerCase();
    }

    public void addModule(Module module) {
        modules.add(module);
    }

    public Course generateRecomendedCourse() {
        return null;
    }
}
