package com.example.course;

import java.io.IOException;
import java.util.ArrayList;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.MenuButton;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.ToolBar;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;



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

    public String getIntroText() {
        return introText;
    }

    public void addModule(Module module) {
        modules.add(module);
    }

    public Course generateRecomendedCourse() {
        return null;
    }

    public void generateTable(TableView table, boolean editable) {
        
        TableColumn name = new TableColumn("Name");
        TableColumn subject = new TableColumn("Subject");
        TableColumn difficultyLevel = new TableColumn("Difficulty level");
        table.getColumns().addAll(name, subject, difficultyLevel);

        name.setCellValueFactory(new PropertyValueFactory<Course, String>("name"));
        subject.setCellValueFactory(new PropertyValueFactory<Course, String>("subject"));
        difficultyLevel.setCellValueFactory(new PropertyValueFactory<Course, String>("difficultyLevelString"));

        table.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                generatePopupWindow(event, editable, (Course)table.getSelectionModel().getSelectedItem());
            }
        });
    }

    private void generatePopupWindow(MouseEvent event, boolean editable, Course course) {
        if (event.getClickCount()>1) {
            AnchorPane pane;
            try {
                pane = FXMLLoader.load(getClass().getResource("/com/example/javafx/fxml/coursePopup.fxml"));
            } catch (IOException e) {
                e.printStackTrace();
                return;
            }
            Scene scene = ((Node)event.getSource()).getScene();
            AnchorPane rootAnchorPane = (AnchorPane)scene.getRoot();
            Rectangle rect = new Rectangle(960, 540, Paint.valueOf("#0000008f"));
            rootAnchorPane.getChildren().addAll(rect, pane);
            pane.setLayoutX(180);
            pane.setLayoutY(70);
            
            TextField textField = (TextField)pane.lookup("#name");
            textField.setEditable(editable);
            textField.setText(course.name);

            TextField textField2 = (TextField)pane.lookup("#subject");
            textField2.setEditable(editable);
            textField2.setText(course.subject);

            TextArea textArea = (TextArea)pane.lookup("#introText");
            textArea.setEditable(editable);
            textArea.setText(course.introText);

            MenuButton menuButton = (MenuButton)pane.lookup("#difficultyLevel");
            menuButton.setOnAction(null);
            menuButton.setText(course.difficultyLevel.toString().toLowerCase());

            ToolBar toolbar = (ToolBar)pane.lookup("#toolBar");
            ((Button)toolbar.getItems().get(0)).setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                    AnchorPane root = (AnchorPane)pane.getScene().getRoot();
                    root.getChildren().removeAll(pane, rect);
                }   
            });
        }
    }
}
