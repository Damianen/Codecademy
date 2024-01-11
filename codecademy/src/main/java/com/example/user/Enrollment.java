package com.example.user;

import static com.example.course.Course.DifficultyLevel.BEGINNER;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;

import com.example.course.Course;
import com.example.database.DatabaseCertificate;
import com.example.database.DatabaseCourse;
import com.example.database.DatabaseEnrollment;
import com.example.database.DatabaseUser;
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
import javafx.scene.control.Button;
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

    public Enrollment(int id, LocalDate enrollmentDate, String courseTitle) {
        this.id = id;
        this.enrollmentDate = enrollmentDate;

        this.course = DatabaseCourse.readCourse(courseTitle);
        // this.progresses = DatabaseProgress.getEnrollmentProgresses();
        
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

    public String getEnrolmentDateString() {
        return enrollmentDate.toString();
    }

    public String getCourseTitle() {
        return this.course.getTitle();
    }

    public String getUserEmail() {
        return DatabaseEnrollment.getEnrollmentUser(id);
    }

    public boolean addCertificate() {
        try {
            DatabaseCertificate.createCertificate(this);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    static public void generateTable(TableView<Enrollment> table, boolean editable,
            HashMap<String, String> searchArgs) {
        TableColumn<Enrollment, String> userEmail = new TableColumn<Enrollment, String>("User E-mail");
        TableColumn<Enrollment, String> courseTitle = new TableColumn<Enrollment, String>("Course Title");
        TableColumn<Enrollment, String> EnrolmentDate = new TableColumn<Enrollment, String>("Enrolment Date");

        final ObservableList<TableColumn<Enrollment, ?>> columns = FXCollections.observableArrayList();
        columns.add(userEmail);
        columns.add(EnrolmentDate);
        columns.add(courseTitle);
        table.getColumns().addAll(columns);

        userEmail.setCellValueFactory(new PropertyValueFactory<Enrollment, String>("userEmail"));
        courseTitle.setCellValueFactory(new PropertyValueFactory<Enrollment, String>("courseTitle"));
        EnrolmentDate.setCellValueFactory(new PropertyValueFactory<Enrollment, String>("enrolmentDateString"));

        table.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                Enrollment enrollment = (Enrollment) table.getSelectionModel().getSelectedItem();
                try {
                    enrollment.generatePopupWindow(event, editable);
                } catch (NoSuchMethodException | SecurityException | IllegalAccessException | IllegalArgumentException
                        | InvocationTargetException e) {
                    e.printStackTrace();
                }
            }
        });

        table.setItems(DatabaseEnrollment.getEnrollmentsSearch(searchArgs));
        
    }

    public void generatePopupWindow(MouseEvent event, boolean editable) throws NoSuchMethodException, SecurityException,
            IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        if (event.getClickCount() > 1) {
            AnchorPane pane;

            try {
                pane = FXMLLoader.load(getClass().getResource("/com/example/javafx/fxml/enrollments.fxml"));
            } catch (IOException e) {
                e.printStackTrace();
                return;
            }

            Scene scene = ((Node) event.getSource()).getScene();
            GUIController.setupPopupWindow(pane, (AnchorPane) scene.getRoot());
            GUIController.setUpNode(DatePicker.class, editable, enrollmentDate, pane, "enrollDate");
            setupTabs(pane, editable);
        }
    }

    public void setupTabs(AnchorPane pane, boolean editable) {
        ObservableList<Tab> tabs = ((TabPane) pane.lookup("#tabPane")).getTabs();
        for (Tab tab : tabs) {
            AnchorPane rootTabPane = (AnchorPane) tab.getContent();
            TableView table = (TableView) rootTabPane.lookup("#table");
            if (tab.getId().equals("course")) {
                HashMap<String, String> map = new HashMap<String, String>();
                map.put("enrolmentTitle", course.getTitle());
                Course.generateTable(table, editable, map);
            } else if (tab.getId().equals("user")) {
                HashMap<String, String> map = new HashMap<String, String>();
                map.put("email", DatabaseEnrollment.getEnrollmentUser(id));
                User.generateTable(table, editable, map);
            } else {
                Certificate.generateTable(table, editable, id);
                ObservableList items = table.getItems();
                if (items.contains(null)) {
                    ((Button)pane.lookup("#create")).setOnAction(new EventHandler<ActionEvent>() {
                        @Override
                        public void handle(ActionEvent event) {
                            addCertificate();
                            GUIController.clearTable(table);
                            Certificate.generateTable(table, editable, id);
                            ((Button)pane.lookup("#create")).setVisible(false);
                        }
                    });
                } else {
                    ((Button)pane.lookup("#create")).setVisible(false);
                }
                
            }
        }
    }
}
