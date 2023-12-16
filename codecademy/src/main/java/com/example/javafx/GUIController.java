package com.example.javafx;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

public class GUIController {
    private Stage stage;
    private Scene scene;
    private Parent root;

    public void switchPage(ActionEvent event) throws IOException {
        root = FXMLLoader.load(getClass().getResource(getFXMLPath(event.getSource())));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    private String getFXMLPath(Object obj) {
        Button clickedButton = (Button) obj;
        switch (clickedButton.getId()) {
            case ("updateButton"): return "/com/example/javafx/fxml/Update.fxml";
            case ("createButton"): return "/com/example/javafx/fxml/Create.fxml";
            case ("deleteButton"): return "/com/example/javafx/fxml/Delete.fxml";
            case ("readButton"):   return "/com/example/javafx/fxml/Read.fxml";
            case ("homeButton"):   return "/com/example/javafx/fxml/Start.fxml";
        }

        return "";
    }

    public void search(ActionEvent event) throws IOException {
        AnchorPane pane = FXMLLoader.load(getClass().getResource("/com/example/javafx/fxml/coursePopup.fxml"));
        scene = ((Node)event.getSource()).getScene();
        AnchorPane rootAnchorPane = (AnchorPane)scene.getRoot();
        Rectangle rect = new Rectangle(960, 540, Paint.valueOf("#0000008f"));
        rootAnchorPane.getChildren().addAll(rect, pane);
        pane.setLayoutX(180);
        pane.setLayoutY(70);
    }

    public void create(ActionEvent event) throws IOException {
        
    }

    public void delete(ActionEvent event) throws IOException {

    }

    public void Update(ActionEvent event) throws IOException {

    }
}
