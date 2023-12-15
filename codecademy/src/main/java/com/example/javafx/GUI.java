package com.example.javafx;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.application.Application;
import javafx.stage.Stage;

public class GUI extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception{
        FXMLLoader loader = new FXMLLoader(getClass().getResource("xml/startPage.fxml"));
        Parent root = loader.load();

        GUIController controller = loader.getController();

        primaryStage.setTitle("Codecademy");
        primaryStage.setScene(new Scene(root, 500, 500));
        primaryStage.show();
    }
}
