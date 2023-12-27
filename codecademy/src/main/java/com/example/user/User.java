package com.example.user;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;

import com.example.database.DatabaseEnrollment;
import com.example.javafx.GUIController;

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

public class User {

    private String email;
    private String name;
    private LocalDate dateOfBirth;
    private String address;
    private String residence;
    private String country;
    private Gender gender;
    private ArrayList<Enrollment> enrollments;
    
    public enum Gender{
        M,
        F
    }

    public User(String email, String name, LocalDate dateOfBirth, Gender gender, String address, String residence, String country){
        this.email = email;
        this.name = name;
        this.dateOfBirth = dateOfBirth;
        this.gender = gender;
        this.address = address;
        this.residence = residence;
        this.country = country;

        this.enrollments = DatabaseEnrollment.getUserEnrollments(email);
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getEmail() {
        return email;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setDateOfBirth(LocalDate dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }

    public void setAdress(String address) {
        this.address = address;
    }

    public String getAdress() {
        return address;
    }

    public void setResidence(String residence) {
        this.residence = residence;
    }

    public String getResidence() {
        return residence;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getCountry() {
        return country;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    public Gender getGender() {
        return gender;
    }

    public void setEnrollments(ArrayList<Enrollment> enrollments) {
        this.enrollments = enrollments;
    }

    public ArrayList<Enrollment> getEnrollments() {
        return enrollments;
    }

    public void addEnrollment(){
    
    }

    public void generateUserCertificates(){
       
    }

    static public void generateTable(TableView<User> table, boolean editable, AnchorPane rootPane) {
        
        TableColumn<User, String> name = new TableColumn<User, String>("Name");
        TableColumn<User, String> email = new TableColumn<User, String>("E-mail");
        TableColumn<User, String> gender = new TableColumn<User, String>("Gender");
        TableColumn<User, String> dateOfBirth = new TableColumn<User, String>("Date of Birth");
        
        final ObservableList<TableColumn<User, ?>> columns = FXCollections.observableArrayList();
        columns.add(name);
        columns.add(email);
        columns.add(gender);
        columns.add(dateOfBirth);
        table.getColumns().addAll(columns);

        name.setCellValueFactory(new PropertyValueFactory<User, String>("name"));
        email.setCellValueFactory(new PropertyValueFactory<User, String>("email"));
        gender.setCellValueFactory(new PropertyValueFactory<User, String>("genderString"));
        dateOfBirth.setCellValueFactory(new PropertyValueFactory<User, String>("dateOfBirth"));

        table.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                generatePopupWindow(event, editable, (User)table.getSelectionModel().getSelectedItem());
            }
        });

        final ObservableList<User> data = FXCollections.observableArrayList(
            
        );

        table.setItems(data);
        
    }

    static private void generatePopupWindow(MouseEvent event, boolean editable, User User) {
        if (event.getClickCount()>1) {
            AnchorPane pane;
            
            try {
                pane = FXMLLoader.load(User.getClass().getResource("/com/example/javafx/fxml/User.fxml"));
            } catch (IOException e) {
                e.printStackTrace();
                return;
            }
            
            Scene scene = ((Node)event.getSource()).getScene();
            GUIController.setupPopupWindow(editable, pane, (AnchorPane)scene.getRoot());
            
            TextField textField = (TextField)pane.lookup("#name");
            textField.setEditable(editable);
            textField.setText(User.name);

            TextField textField2 = (TextField)pane.lookup("#email");
            textField2.setEditable(editable);
            textField2.setText(User.email);

            TextField textArea = (TextField)pane.lookup("#dateOfBirth");
            textArea.setEditable(editable);
            textArea.setText(User.dateOfBirth.toString());

            MenuButton menuButton = (MenuButton)pane.lookup("#gender");
            GUIController.setMenuButtonActions(menuButton, editable);
            menuButton.setText(User.gender.toString().toLowerCase());

            ObservableList<Tab> tabs = ((TabPane)pane.lookup("#tables")).getTabs();
            for (Tab tab : tabs) {
                AnchorPane rootTabPane = (AnchorPane)tab.getContent();
                TableView table = (TableView)rootTabPane.lookup("#table");
            }
        }
    }
}


