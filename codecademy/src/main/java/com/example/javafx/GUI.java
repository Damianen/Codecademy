package com.example.javafx;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Parent;

public class GUI extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception{
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/javafx/fxml/Start.fxml"));
        Parent root = loader.load();
        
        primaryStage.setTitle("New Scene");
        primaryStage.setScene(new Scene(root, 800, 600));
        primaryStage.show();   
    }

    public void startGui(String args[]) {
        launch(args);
    }
}
