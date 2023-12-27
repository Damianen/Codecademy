package com.example.course;

public class Speaker {
    private int id;
    private String name;
    private String oranization;

    public Speaker(int id, String name, String oranization) {
        this.id = id;
        this.name = name;
        this.oranization = oranization;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getOranization() {
        return oranization;
    }

    public void setOranization(String oranization) {
        this.oranization = oranization;
    }
}
