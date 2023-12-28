package com.example.user;

import static com.example.course.Course.DifficultyLevel.BEGINNER;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;

import com.example.course.Course;
import com.example.database.DatabaseCertificate;
import com.example.database.DatabaseCourse;
import com.example.course.*;
import com.example.javafx.GUIController;
import com.example.user.User.Gender;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.DatePicker;
import javafx.scene.control.MenuButton;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;

public class Enrollment {
    private final int id;
    private LocalDate enrollmentDate;
    private Certificate certificate;
    private Course course;
    private ArrayList<Progress> progresses;
    private User user;

    public Enrollment(int id, LocalDate enrollmentDate, String courseTitle){
        this.id = id;
        this.enrollmentDate = enrollmentDate;
        
        this.course = DatabaseCourse.readCourse(courseTitle);
        //this.progresses = DatabaseProgress.getEnrollmentProgresses();
        this.user = new User(courseTitle, courseTitle, enrollmentDate, Gender.M, courseTitle, courseTitle, courseTitle);
        this.certificate = DatabaseCertificate.getEnrollmentCertificate(this.id);
    }

    public void setCertificate(Certificate certificate) {
        this.certificate = certificate;
    }

    public int getId() {
        return id;
    }

    public void setEnrollmentDate(LocalDate enrollmentDate) {
        this.enrollmentDate = enrollmentDate;
    } 
    public LocalDate getEnrollmentDate() {
        return enrollmentDate;
    }
    public Certificate getCertificate() {
        return certificate;
    }

    public void setCourse(Course course) {
        this.course = course;
    }

    public Course getCourse() {
        return course;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public User getUser() {
        return user;
    }

    public String getEnrolmentDateString() {
        return enrollmentDate.toString();
    }

    public String getUserName() {
        return this.user.getName();
    }

    public String getCourseTitle() {
        return this.course.getTitle();
    }

    public void addCertificate(){

    }

    static public void generateTable(TableView<Enrollment> table, boolean editable) {
        TableColumn<Enrollment, String> userName = new TableColumn<Enrollment, String>("User Name");
        TableColumn<Enrollment, String> courseTitle = new TableColumn<Enrollment, String>("Course Title");
        TableColumn<Enrollment, String> EnrolmentDate = new TableColumn<Enrollment, String>("Enrolment Date");     

        final ObservableList<TableColumn<Enrollment, ?>> columns = FXCollections.observableArrayList();
        columns.add(userName);
        columns.add(EnrolmentDate);
        columns.add(courseTitle);
        table.getColumns().addAll(columns);

        userName.setCellValueFactory(new PropertyValueFactory<Enrollment, String>("userName"));
        courseTitle.setCellValueFactory(new PropertyValueFactory<Enrollment, String>("courseTitle"));
        EnrolmentDate.setCellValueFactory(new PropertyValueFactory<Enrollment, String>("enrolmentDateString"));

        table.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                Enrollment enrollment = (Enrollment)table.getSelectionModel().getSelectedItem();
                enrollment.generatePopupWindow(event, editable);
            }
        });

        final ObservableList<Enrollment> data = FXCollections.observableArrayList(
            new Enrollment(0, LocalDate.now(), "test")
        );

        table.setItems(data);
    }

    public void generatePopupWindow(MouseEvent event, boolean editable) {
        if (event.getClickCount()>1) {
            AnchorPane pane;
            
            try {
                pane = FXMLLoader.load(getClass().getResource("/com/example/javafx/fxml/enrollments.fxml"));
            } catch (IOException e) {
                e.printStackTrace();
                return;
            }
            
            Scene scene = ((Node)event.getSource()).getScene();
            GUIController.setupPopupWindow(editable, pane, (AnchorPane)scene.getRoot());

            DatePicker EnrolmentDate = (DatePicker)pane.lookup("#enrollDate");
            EnrolmentDate.setEditable(editable);
            if (!editable) {
                EnrolmentDate.setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent event) {
                        EnrolmentDate.setValue(enrollmentDate);
                    }
                });
            }
            EnrolmentDate.setValue(enrollmentDate);

            ObservableList<Tab> tabs = ((TabPane)pane.lookup("#tabPane")).getTabs();
            for (Tab tab : tabs) {
                AnchorPane rootTabPane = (AnchorPane)tab.getContent();
                TableView table = (TableView)rootTabPane.lookup("#table");
                if (tab.getId().equals("course")) {
                    Course.generateTable(table, editable);
                } else if (tab.getId().equals("user")) {
                    User.generateTable(table, editable);
                } else {
                    Certificate.generateTable(table, editable);
                }
            }
        }
    }
}
