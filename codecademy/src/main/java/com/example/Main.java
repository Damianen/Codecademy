package com.example;

import com.example.javafx.GUI;

public class Main {
    public static void main(String[] args) {
        GUI gui = new GUI();
        gui.startGui(args);

        try {
            //System.out.println(DatabaseWebcast.readWebcast(1).getSpeaker().getOranization());
        } catch (Exception e) {
           e.printStackTrace();
        }
    }
}
