package com.example.javafx;

import java.io.IOException;
import java.util.Observable;

import com.example.course.Course;
import com.example.course.Course.DifficultyLevel;

import static com.example.course.Course.DifficultyLevel.*;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
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
        AnchorPane node = (AnchorPane)((Node)((Node)event.getSource()).getParent());
        System.out.println(node);
        TableView table = (TableView)node.lookup("#table");
        TableColumn name = new TableColumn("Name");
        TableColumn subject = new TableColumn("Subject");
        TableColumn difficultyLevel = new TableColumn("Difficulty level");
        table.getColumns().addAll(name, subject, difficultyLevel);

        final ObservableList<Course> data = FXCollections.observableArrayList(
            new Course("Java", "Java", "welcome to java", BEGINNER),
            new Course("Python", "Python", "welcome to Python", BEGINNER),
            new Course("c", "c", "welcome to c", BEGINNER)
        );

        name.setCellValueFactory(new PropertyValueFactory<Course, String>("name"));
        subject.setCellValueFactory(new PropertyValueFactory<Course, String>("subject"));
        difficultyLevel.setCellValueFactory(new PropertyValueFactory<Course, String>("difficultyLevelString"));

        table.setItems(data);

        table.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if (event.getClickCount()>1) {
                    System.out.println(((Course)table.getSelectionModel().getSelectedItem()).getName());
                }
            }
        });

    }

    public void create(ActionEvent event) throws IOException {
        AnchorPane pane = FXMLLoader.load(getClass().getResource("/com/example/javafx/fxml/coursePopup.fxml"));
        scene = ((Node)event.getSource()).getScene();
        AnchorPane rootAnchorPane = (AnchorPane)scene.getRoot();
        Rectangle rect = new Rectangle(960, 540, Paint.valueOf("#0000008f"));
        rootAnchorPane.getChildren().addAll(rect, pane);
        pane.setLayoutX(180);
        pane.setLayoutY(70);
    }

    public void delete(ActionEvent event) throws IOException {

    }

    public void Update(ActionEvent event) throws IOException {

    }
}
