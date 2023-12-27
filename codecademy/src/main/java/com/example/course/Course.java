package com.example.course;

import java.io.IOException;
import java.util.ArrayList;

import com.example.database.DatabaseCourse;
import com.example.javafx.GUIController;
import com.example.user.Enrollment;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.MenuButton;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;



public class Course { 
    
    public enum DifficultyLevel {
        BEGINNER,
        ADVANCED,
        EXPERT
    }

    private String title;
    private String subject;
    private String introText;
    private DifficultyLevel difficultyLevel;
    private ArrayList<Module> modules;

    public Course(String title, String subject, String introText, DifficultyLevel difficulty) {
        this.title = title;
        this.subject = subject;
        this.introText = introText;
        this.difficultyLevel = difficulty;
        this.modules = new ArrayList<Module>();
    }

    public String getTitle() {
        return title;
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

    static public void generateTable(TableView<Course> table, boolean editable, AnchorPane rootPane) {
        
        TableColumn<Course, String> title = new TableColumn<Course, String>("title");
        TableColumn<Course, String> subject = new TableColumn<Course, String>("Subject");
        TableColumn<Course, String> difficultyLevel = new TableColumn<Course, String>("Difficulty level");
        
        final ObservableList<TableColumn<Course, ?>> columns = FXCollections.observableArrayList();
        columns.add(title);
        columns.add(subject);
        columns.add(difficultyLevel);
        table.getColumns().addAll(columns);

        title.setCellValueFactory(new PropertyValueFactory<Course, String>("title"));
        subject.setCellValueFactory(new PropertyValueFactory<Course, String>("subject"));
        difficultyLevel.setCellValueFactory(new PropertyValueFactory<Course, String>("difficultyLevelString"));

        table.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                generatePopupWindow(event, editable, (Course)table.getSelectionModel().getSelectedItem());
            }
        });

        String nameString = ((TextField)rootPane.lookup("#name")).getText();
        String subjectString = ((TextField)rootPane.lookup("#subject")).getText();
        String introTextString = ((TextArea)rootPane.lookup("#introText")).getText();
        String difficultyLevelString = ((MenuButton)rootPane.lookup("#difficultyLevel")).getText();

        table.setItems(DatabaseCourse.getCourseList(nameString));
    }

    static private void generatePopupWindow(MouseEvent event, boolean editable, Course course) {
        if (event.getClickCount()>1) {
            AnchorPane pane;
            
            try {
                pane = FXMLLoader.load(getClass().getResource("/com/example/javafx/fxml/course.fxml"));
            } catch (IOException e) {
                e.printStackTrace();
                return;
            }
            
            Scene scene = ((Node)event.getSource()).getScene();
            GUIController.setupPopupWindow(editable, pane, (AnchorPane)scene.getRoot());
            
            TextField textField = (TextField)pane.lookup("#title");
            textField.setEditable(editable);
            textField.setText(title);

            TextField textField2 = (TextField)pane.lookup("#subject");
            textField2.setEditable(editable);
            textField2.setText(subject);

            TextArea textArea = (TextArea)pane.lookup("#introText");
            textArea.setEditable(editable);
            textArea.setText(introText);

            MenuButton menuButton = (MenuButton)pane.lookup("#difficultyLevel");
            GUIController.setMenuButtonActions(menuButton, editable);
            menuButton.setText(difficultyLevel.toString().toLowerCase());

            ObservableList<Tab> tabs = ((TabPane)pane.lookup("#tables")).getTabs();
            for (Tab tab : tabs) {
                AnchorPane rootTabPane = (AnchorPane)tab.getContent();
                TableView table = (TableView)rootTabPane.lookup("#table");
                if (!editable) {
                    table.setPrefHeight(320);
                }
                if (tab.getId().equals("module")) {
                    Module.generateTable(table, editable, pane);
                } else {
                    Enrollment.generateTable(table, editable, pane);
                }
            }
        }
    }
}
