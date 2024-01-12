package com.example.overview;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import com.example.course.Course;
import com.example.course.Webcast;
import com.example.database.DatabaseCourse;
import com.example.database.DatabaseOverview;
import com.example.database.DatabaseUser;
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
    private ObservableList<User> users;
    private ObservableList<Course> courses;

    public Overview(){
        this.users = DatabaseUser.getUserList();
        this.courses = DatabaseCourse.getCourseList();
    }

    public ObservableList<Course> getCourses() {
        return courses;
    }

    public void setCourses(ObservableList<Course> courses) {
        this.courses = courses;
    }

    public ObservableList<User> getUsers() {
        return users;
    }

    public void setUsers(ObservableList<User> users) {
        this.users = users;
    }

    
    static public void setupOverviewButton(Button calculateButton, AnchorPane pane) {
        calculateButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {

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
        MenuButton btn = (MenuButton)pane.lookup("#genderBtn");

        if (btn.getText().equals("Gender")) {
            ((Label)pane.lookup("#result")).setText("Please select a gender!");
            return;
        }

        Gender gender = Gender.valueOf(String.valueOf(btn.getText().charAt(0)).toUpperCase());
        int num = DatabaseOverview.getObtaintedCertificatesPercentageForGender(gender);

        ((Label)pane.lookup("#result")).setText( btn.getText() + "s have on average " + num + "% of their courses complete!");
    }

    static public void generateAverageCourseModulesProgress(AnchorPane pane) {
        TableView<List<Object>> table = (TableView)pane.lookup("#result");
        GUIController.clearTable(table);

        TableView<Course> courseTable = (TableView)pane.lookup("#courseTable");

        Course course = courseTable.getSelectionModel().getSelectedItem();

        if (course == null) {
            return;
        }

        HashMap<String, Integer> map = DatabaseOverview.getAveragePogressPercentagePerCourseModule(course);

        if (map.isEmpty()) {
            return;
        }

        ObservableList<List<Object>> data = FXCollections.observableArrayList();
        map.forEach((key, value) -> {
            List<Object> newList = new ArrayList<>();
            newList.add((Object)key);
            newList.add((Object)value);
            data.add(newList);
        });

        TableColumn<List<Object>, String> moduleName = new TableColumn<List<Object>, String>("Module Name");
        TableColumn<List<Object>, Integer> progressPercentage = new TableColumn<List<Object>, Integer>("progress %");     

        final ObservableList<TableColumn<List<Object>, ?>> columns = FXCollections.observableArrayList();
        columns.add(moduleName);
        columns.add(progressPercentage);
        table.getColumns().addAll(columns);

        moduleName.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<List<Object>,String>,ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(CellDataFeatures<List<Object>, String> data) {
                return new ReadOnlyStringWrapper((String)data.getValue().get(0));
            }
        });

        progressPercentage.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<List<Object>,Integer>,ObservableValue<Integer>>() {
            @Override
            public ObservableValue<Integer> call(CellDataFeatures<List<Object>, Integer> data) {
                return new SimpleIntegerProperty((Integer)data.getValue().get(1)).asObject();
            }
        });

        table.setItems(data);
    }

    static public void generateCourseModulesProgressForUser(AnchorPane pane) {
        TableView<List<Object>> table = (TableView)pane.lookup("#result");
        GUIController.clearTable(table);

        TableView<Course> courseTable = (TableView)pane.lookup("#courseTable");
        TableView<User> userTable = (TableView)pane.lookup("#userTable");

        Course course = courseTable.getSelectionModel().getSelectedItem();
        User user = userTable.getSelectionModel().getSelectedItem();

        if (user == null || course == null) {
            return;
        }

        HashMap<String, Integer> map = DatabaseOverview.getCourseModulesProgressForUser(course, user);

        if (map.isEmpty()) {
            return;
        }

        ObservableList<List<Object>> data = FXCollections.observableArrayList();
        map.forEach((key, value) -> {
            List<Object> newList = new ArrayList<>();
            newList.add((Object)key);
            newList.add((Object)value);
            data.add(newList);
        });

        TableColumn<List<Object>, String> moduleName = new TableColumn<List<Object>, String>("Module Name");
        TableColumn<List<Object>, Integer> progressPercentage = new TableColumn<List<Object>, Integer>("progress %");     

        final ObservableList<TableColumn<List<Object>, ?>> columns = FXCollections.observableArrayList();
        columns.add(moduleName);
        columns.add(progressPercentage);
        table.getColumns().addAll(columns);

        moduleName.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<List<Object>,String>,ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(CellDataFeatures<List<Object>, String> data) {
                return new ReadOnlyStringWrapper((String)data.getValue().get(0));
            }
        });

        progressPercentage.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<List<Object>,Integer>,ObservableValue<Integer>>() {
            @Override
            public ObservableValue<Integer> call(CellDataFeatures<List<Object>, Integer> data) {
                return new SimpleIntegerProperty((Integer)data.getValue().get(1)).asObject();
            }
        });

        table.setItems(data);
    }
    
    static public void generateTop3Webcasts(TableView table) {
        Webcast.generateTable(table, false, "");
        table.getItems().clear();
        table.setItems(DatabaseOverview.getTop3Webcasts());
    }

    static public void generateTop3Course(TableView<Course> table) {
        Course.generateTable(table, false, null);
        table.getItems().clear();
        table.setItems(DatabaseOverview.getTop3Courses());
    }   

    static public void generateCourseCompletions(AnchorPane pane) {
        TableView<Course> courseTable = (TableView)pane.lookup("#courseTable");
        Course course = courseTable.getSelectionModel().getSelectedItem();

        if (course == null) {
            return;
        }

        int num = DatabaseOverview.getCourseCompletionsCount(course.getTitle());

        ((Label)pane.lookup("#result")).setText("The course " + course.getTitle() + " has " + num + " completions!");
    }

    static public void generateOtherTables(AnchorPane pane) {
        TableView table = (TableView)pane.lookup("#result");
        GUIController.clearTable(table);

        if (pane.getId().equals("UC")) {
            TableView<User> userTable = (TableView)pane.lookup("#userTable");
            User user = userTable.getSelectionModel().getSelectedItem();

            if (user == null) {
                return;
            }

            user.generateUserCertificates(table);
        } 

        if (pane.getId().equals("VRC")) {
            TableView courseTable = (TableView)pane.lookup("#courseTable");
            Course course = (Course)courseTable.getSelectionModel().getSelectedItem();

            if (course == null) {
                return;
            }

            course.generateRecommendedCourses(table);
        }
    }
}
