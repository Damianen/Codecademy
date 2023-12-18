package com.example.javafx;

import java.io.IOException;

import com.example.course.Course;

import static com.example.course.Course.DifficultyLevel.*;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableView;
import javafx.scene.layout.AnchorPane;
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
        AnchorPane node = (AnchorPane)((Node)((Node)event.getSource()).getParent());
        @SuppressWarnings("unchecked")
        TableView<Course> table = (TableView<Course>)node.lookup("#table");
        table.getItems().clear();
        table.getColumns().clear();
        Course c = new Course("c++", "c++", "welcome to C++", ADVANCED);
        
        boolean editable = false;
        if (node.getScene().getRoot().getId().equals("updateRoot")) {
            editable = true;
        }
        c.generateTable(table, editable);

        final ObservableList<Course> data = FXCollections.observableArrayList(
            new Course("Java", "Java", "welcome to java", BEGINNER),
            new Course("Python", "Python", "welcome to Python", BEGINNER),
            new Course("c", "c", "welcome to c", BEGINNER),
            c
        );

        table.setItems(data);
    }

    public static void closePopupWindow(AnchorPane popupPane, Rectangle rect) {
        AnchorPane root = (AnchorPane)popupPane.getScene().getRoot();
        root.getChildren().removeAll(popupPane, rect);
    }
    
    public void create(ActionEvent event) throws IOException {
        
    }

    public void delete(ActionEvent event) throws IOException {

    }

    public void Update(ActionEvent event) throws IOException {

    }
}
