package com.example.javafx;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
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
        String objString = obj.toString();
        if (objString.contains("id=updateButton")) { 
            return "/com/example/javafx/fxml/Update.fxml"; 
        } else if (objString.contains("id=createButton")) { 
            return "/com/example/javafx/fxml/Create.fxml"; 
        } else if (objString.contains("id=deleteButton")) { 
            return "/com/example/javafx/fxml/Delete.fxml"; 
        } else if (objString.contains("id=readButton")) { 
            return "/com/example/javafx/fxml/Read.fxml"; 
        } else {
            return "/com/example/javafx/fxml/Start.fxml";
        }
    }
}
