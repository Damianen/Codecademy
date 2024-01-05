package com.example.course;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

import javax.swing.Action;

import com.example.database.DatabaseCourse;
import com.example.database.DatabaseModule;
import com.example.database.DatabaseProgress;
import com.example.database.DatabaseUser;
import com.example.javafx.GUIController;
import com.example.user.Enrollment;
import com.example.user.Progress;
import com.example.user.User;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.MenuButton;
import javafx.scene.control.Button;
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
    private ObservableList<ContentItem> modules;

    public Course(String title, String subject, String introText, DifficultyLevel difficulty) {
        this.title = title;
        this.subject = subject;
        this.introText = introText;
        this.difficultyLevel = difficulty;
        this.modules = DatabaseModule.getCourseModules(title);
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

    public ObservableList<ContentItem> getModules() {
        return modules;
    }

    public void setModules(ObservableList<ContentItem> modules) {
        this.modules = modules;
    }

    public boolean addModule(Module module) {

        int orderNumber = DatabaseModule.getCourseModules(this.title).size() + 1;
        
        if(DatabaseModule.updateModule(module.getId(), module.getTitle(), module.getPublicationDate(), module.getStatus(), module.getDescription(), module.getVersion(), orderNumber, module.getContactPerson().getEmail(), this.title) == false){
            return false;
        }

        ObservableList<User> enrolledUsers = DatabaseUser.getEnrolledUsersForCourse(this);

        for (User user : enrolledUsers) {

            Random rand = new Random();
            int randomNumber = rand.nextInt(101);

            try {
                DatabaseProgress.createProgress(randomNumber, user.getEmail(), module.getContentItemId());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        
        modules.add(module);

        return true;
    }

    public boolean removeModule(Module module) {

        if(DatabaseModule.updateOrderNumbersRemoveFromCourse(module.getId(), module.getOrderNumber(), this.title) == false){
            return false;
        }

        ObservableList<User> enrolledUsers = DatabaseUser.getEnrolledUsersForCourse(this);

        for (User user : enrolledUsers) {

            Progress progress = DatabaseProgress.getProgressWithUserAndContentItem(user.getEmail(), module.getContentItemId());
            DatabaseProgress.deleteProgress(progress.getId());
        }
        
        modules.remove(module);

        return true;
    }

    public Course generateRecomendedCourse() {
        return null;
    }

    static public HashMap<String, String> getArgsHashMap(AnchorPane pane) throws NoSuchMethodException,
            SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        HashMap<String, String> searchArgs = new HashMap<String, String>();
        searchArgs.put("title", GUIController.searchForNodeText("title", TextField.class, pane));
        searchArgs.put("introText", GUIController.searchForNodeText("introText", TextArea.class, pane));
        searchArgs.put("subject", GUIController.searchForNodeText("subject", TextField.class, pane));
        String difficulty = GUIController.searchForNodeText("difficultyLevel", MenuButton.class, pane);
        if (!difficulty.equals("Difficulty Level")) {
            searchArgs.put("difficultyLevel", difficulty);
        }
        return searchArgs;
    }

    static public void generateTable(TableView<Course> table, boolean editable, HashMap<String, String> searchArgs) {

        TableColumn<Course, String> title = new TableColumn<Course, String>("Title");
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
                Course course = (Course) table.getSelectionModel().getSelectedItem();
                try {
                    course.generatePopupWindow(event, editable);
                } catch (NoSuchMethodException | SecurityException | IllegalAccessException | IllegalArgumentException
                        | InvocationTargetException e) {
                    e.printStackTrace();
                }
            }
        });

        if (searchArgs == null) {
            table.setItems(DatabaseCourse.getCourseList());
        } else if (searchArgs.containsKey("userEmail")) {
            table.setItems(DatabaseCourse.getNotEnrolledCourseForUser(searchArgs.get("userEmail")));
        } 
        else {
            table.setItems(DatabaseCourse.readCourseSearchAll(searchArgs));
        }

    }

    private void generatePopupWindow(MouseEvent event, boolean editable) throws NoSuchMethodException,
            SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        if (event.getClickCount() > 1) {
            AnchorPane pane;

            try {
                pane = FXMLLoader.load(getClass().getResource("/com/example/javafx/fxml/course.fxml"));
            } catch (IOException e) {
                e.printStackTrace();
                return;
            }

            Scene scene = ((Node) event.getSource()).getScene();
            GUIController.setupPopupWindow(pane, (AnchorPane) scene.getRoot());
            GUIController.setupUpdateButton(editable, pane, this);

            GUIController.setUpNode(TextField.class, editable, title, pane, "title");
            GUIController.setUpNode(TextField.class, editable, subject, pane, "subject");
            GUIController.setUpNode(TextArea.class, editable, introText, pane, "introText");
            GUIController.setUpNode(MenuButton.class, editable, difficultyLevel, pane, "difficultyLevel");

            setupTabs(pane, editable);
        }
    }

    public void setupTabs(AnchorPane pane, boolean editable) {
        ObservableList<Tab> tabs = ((TabPane) pane.lookup("#tables")).getTabs();
        for (Tab tab : tabs) {
            AnchorPane rootTabPane = (AnchorPane) tab.getContent();
            TableView table = (TableView) rootTabPane.lookup("#table");
            if (tab.getId().equals("module")) {
                if (!editable) {
                    table.setPrefHeight(320);
                }
                HashMap<String, String> map = new HashMap<String, String>();
                map.put("courseTitle", title);
                Module.generateTable(table, editable, map);
                Button btn = (Button)rootTabPane.lookup("#add");
                btn.setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent event) {
                        setAddModule(table, btn, editable);
                    }
                });
            } else {
                HashMap<String, String> map = new HashMap<String, String>();
                map.put("courseTitle", title);
                Enrollment.generateTable(table, editable, map);
            }
        }
    }

    public void setAddModule(TableView table, Button btn, boolean editable) {
        GUIController.clearTable(table);
        btn.setText("Add selected module");
        HashMap<String, String> map = new HashMap<String, String>();
        map.put("courseTitleNew", title);
        Module.generateTable(table, editable, map);
        btn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                addModule((Module)table.getSelectionModel().getSelectedItem());
                btn.setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent event) {
                        setAddModule(table, btn, editable);
                    }
                });
                GUIController.clearTable(table);
                HashMap<String, String> map = new HashMap<String, String>();
                map.put("courseTitle", title);
                Module.generateTable(table, editable, map);
                btn.setText("Add module");
            }
        });
    }
}
