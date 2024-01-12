package com.example.overview;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import com.example.course.Course;
import com.example.course.Webcast;
import com.example.database.DatabaseOverview;
import com.example.javafx.GUIController;
import com.example.user.User;
import com.example.user.User.Gender;

import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.MenuButton;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.layout.AnchorPane;
import javafx.util.Callback;

public class Overview {

    // Setup for the calculate button on the overview pages
    static public void setupOverviewButton(Button calculateButton, AnchorPane pane) {
        // Add event handler the calculate button
        calculateButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                // Based on the current tab root pane id we call an overview function
                switch (pane.getId()) {
                    case "gender":
                        generateGenderOverview(pane);
                        break;
                
                    case "AMP":
                        generateAverageCourseModulesProgress(pane);;
                        break;
                       
                    case "UMP":
                        generateCourseModulesProgressForUser(pane);
                        break;

                    case "CC":
                        generateCourseCompletions(pane);
                        break;

                    default:
                        generateOtherTables(pane);
                        break;
                }
            }
        });
    }

    static public void generateGenderOverview(AnchorPane pane) {
        // Get the gender button
        MenuButton btn = (MenuButton)pane.lookup("#genderBtn");

        // Check if a gender is selected
        if (btn.getText().equals("Gender")) {
            ((Label)pane.lookup("#result")).setText("Please select a gender!");
            return;
        }

        // Get the gender from the selected menu button and get the num then display it in the label
        Gender gender = Gender.valueOf(String.valueOf(btn.getText().charAt(0)).toUpperCase());
        int num = DatabaseOverview.getObtaintedCertificatesPercentageForGender(gender);

        ((Label)pane.lookup("#result")).setText( btn.getText() + "s have on average " + num + "% of their courses complete!");
    }

    static public void generateAverageCourseModulesProgress(AnchorPane pane) {
        // Get the result table and clear it
        TableView<List<Object>> table = (TableView)pane.lookup("#result");
        GUIController.clearTable(table);

        // Get the course table and the selected course table and check if a course is selected
        TableView<Course> courseTable = (TableView)pane.lookup("#courseTable");
        Course course = courseTable.getSelectionModel().getSelectedItem();

        if (course == null) {
            return;
        }

        // Get the hashmap with the data for the selected course and check if its empty
        HashMap<String, Integer> map = DatabaseOverview.getAveragePogressPercentagePerCourseModule(course);

        if (map.isEmpty()) {
            return;
        }

        // Make a observable list with data and add ech hashmap key and value to it in the form of a list
        // so the observable list has another list inside of it
        ObservableList<List<Object>> data = FXCollections.observableArrayList();
        map.forEach((key, value) -> {
            List<Object> newList = new ArrayList<>();
            newList.add((Object)key);
            newList.add((Object)value);
            data.add(newList);
        });

        // Make table columns and add them to the table
        TableColumn<List<Object>, String> moduleName = new TableColumn<List<Object>, String>("Module Name");
        TableColumn<List<Object>, Integer> progressPercentage = new TableColumn<List<Object>, Integer>("progress %");     

        final ObservableList<TableColumn<List<Object>, ?>> columns = FXCollections.observableArrayList();
        columns.add(moduleName);
        columns.add(progressPercentage);
        table.getColumns().addAll(columns);

        // Set the value factory so that it will look into the list of the entry in the observable list
        // and get the first value aka the key
        moduleName.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<List<Object>,String>,ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(CellDataFeatures<List<Object>, String> data) {
                return new ReadOnlyStringWrapper((String)data.getValue().get(0));
            }
        });

        // Set the value factory so that it will look into the list of the entry in the observable list
        // and get the second value aka the value
        progressPercentage.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<List<Object>,Integer>,ObservableValue<Integer>>() {
            @Override
            public ObservableValue<Integer> call(CellDataFeatures<List<Object>, Integer> data) {
                return new SimpleIntegerProperty((Integer)data.getValue().get(1)).asObject();
            }
        });

        // Add the data to the table
        table.setItems(data);
    }

    static public void generateCourseModulesProgressForUser(AnchorPane pane) {
        // Get the result table and clear it
        TableView<List<Object>> table = (TableView)pane.lookup("#result");
        GUIController.clearTable(table);

        // Get the course and user table and the selected course and user and check if a course and user are selected
        TableView<Course> courseTable = (TableView)pane.lookup("#courseTable");
        TableView<User> userTable = (TableView)pane.lookup("#userTable");

        Course course = courseTable.getSelectionModel().getSelectedItem();
        User user = userTable.getSelectionModel().getSelectedItem();

        if (user == null || course == null) {
            return;
        }

        // Get the hashmap with the data for the selected course and check if its empty
        HashMap<String, Integer> map = DatabaseOverview.getCourseModulesProgressForUser(course, user);

        if (map.isEmpty()) {
            return;
        }

        // Make a observable list with data and add ech hashmap key and value to it in the form of a list
        // so the observable list has another list inside of it
        ObservableList<List<Object>> data = FXCollections.observableArrayList();
        map.forEach((key, value) -> {
            List<Object> newList = new ArrayList<>();
            newList.add((Object)key);
            newList.add((Object)value);
            data.add(newList);
        });

        // Make table columns and add them to the table
        TableColumn<List<Object>, String> moduleName = new TableColumn<List<Object>, String>("Module Name");
        TableColumn<List<Object>, Integer> progressPercentage = new TableColumn<List<Object>, Integer>("progress %");     

        final ObservableList<TableColumn<List<Object>, ?>> columns = FXCollections.observableArrayList();
        columns.add(moduleName);
        columns.add(progressPercentage);
        table.getColumns().addAll(columns);

        // Set the value factory so that it will look into the list of the entry in the observable list
        // and get the first value aka the key
        moduleName.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<List<Object>,String>,ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(CellDataFeatures<List<Object>, String> data) {
                return new ReadOnlyStringWrapper((String)data.getValue().get(0));
            }
        });

        // Set the value factory so that it will look into the list of the entry in the observable list
        // and get the second value aka the value
        progressPercentage.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<List<Object>,Integer>,ObservableValue<Integer>>() {
            @Override
            public ObservableValue<Integer> call(CellDataFeatures<List<Object>, Integer> data) {
                return new SimpleIntegerProperty((Integer)data.getValue().get(1)).asObject();
            }
        });

        // Add the data to the table
        table.setItems(data);
    }
    
    static public void generateTop3Webcasts(TableView table) {
        // Generate the table so it has the columns
        Webcast.generateTable(table, false, "");

        // Clear the table so it still has the columns but not the data
        table.getItems().clear();

        // Add the correct data to the table
        table.setItems(DatabaseOverview.getTop3Webcasts());
    }

    static public void generateTop3Course(TableView<Course> table) {
        // Generate the table so it has the columns
        Course.generateTable(table, false, null);

        // Clear the table so it still has the columns but not the data
        table.getItems().clear();

        // Add the correct data to the table
        table.setItems(DatabaseOverview.getTop3Courses());
    }   

    static public void generateCourseCompletions(AnchorPane pane) {
        // Get the course table and the selected course table and check if a course is selected
        TableView<Course> courseTable = (TableView)pane.lookup("#courseTable");
        Course course = courseTable.getSelectionModel().getSelectedItem();

        if (course == null) {
            return;
        }

        // Get the number of completions and display it in the label
        int num = DatabaseOverview.getCourseCompletionsCount(course.getTitle());

        ((Label)pane.lookup("#result")).setText("The course " + course.getTitle() + " has " + num + " completions!");
    }

    static public void generateOtherTables(AnchorPane pane) {
        // Get the result table and clear it
        TableView table = (TableView)pane.lookup("#result");
        GUIController.clearTable(table);

        if (pane.getId().equals("UC")) {
            // Get the user table and the selected user table and check if a user is selected
            TableView<User> userTable = (TableView)pane.lookup("#userTable");
            User user = userTable.getSelectionModel().getSelectedItem();

            if (user == null) {
                return;
            }

            user.generateUserCertificates(table);
        } 

        if (pane.getId().equals("VRC")) {
            // Get the course table and the selected course table and check if a course is selected
            TableView<Course> courseTable = (TableView)pane.lookup("#courseTable");
            Course course = courseTable.getSelectionModel().getSelectedItem();

            if (course == null) {
                return;
            }

            course.generateRecommendedCourses(table);
        }
    }
}
