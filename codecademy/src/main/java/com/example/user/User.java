package com.example.user;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;

import com.example.course.Course;
import com.example.course.Webcast;
import com.example.database.DatabaseEnrollment;
import com.example.database.DatabaseProgress;
import com.example.database.DatabaseUser;
import com.example.exeptions.AlreadyExistsException;
import com.example.javafx.GUIController;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
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
    private String postalCode;
    private String residence;
    private String country;
    private Gender gender;
    private ObservableList<Enrollment> enrollments;

    public enum Gender {
        M,
        F
    }

    public User(String email, String name, LocalDate dateOfBirth, Gender gender, String address, String postalCode, String residence,
            String country) {
        this.email = email;
        this.name = name;
        this.dateOfBirth = dateOfBirth;
        this.gender = gender;
        this.address = address;
        this.postalCode = postalCode;
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

    public String getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
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

    public String getGenderString() {
        return gender.toString();
    }

    public ObservableList<Enrollment> getEnrollments() {
        return enrollments;
    }

    public void generateUserCertificates() {
        
    }

    // Get all of the all attributes from elements in a specific pane and return a hashmap with the values.
    static public HashMap<String, String> getArgsHashMap(AnchorPane pane) throws NoSuchMethodException,
            SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        
        HashMap<String, String> searchArgs = new HashMap<String, String>();
        
        searchArgs.put("name", GUIController.searchForNodeText("name", TextField.class, pane));
        searchArgs.put("email", GUIController.searchForNodeText("email", TextField.class, pane));
        searchArgs.put("address", GUIController.searchForNodeText("address", TextField.class, pane));
        searchArgs.put("country", GUIController.searchForNodeText("country", TextField.class, pane));
        searchArgs.put("postalCode", GUIController.searchForNodeText("zipCode", TextField.class, pane));
        searchArgs.put("residence", GUIController.searchForNodeText("residence", TextField.class, pane));
        
        String difficulty = GUIController.searchForNodeText("gender", MenuButton.class, pane);
        if (!difficulty.equals("Gender")) {
            searchArgs.put("gender", difficulty);
        }
        
        DatePicker date = (DatePicker) pane.lookup("#birthDate");
        if (date.getValue() != null) {
            searchArgs.put("birthDate", date.getValue().toString());
        }
        
        return searchArgs;
    }

    // Generate table function 
    static public void generateTable(TableView<User> table, boolean editable, HashMap<String, String> searchArgs) {
        // Make table columns and add them to the table
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

        // Set the a value factory so the table can get the data from the instance of the class
        name.setCellValueFactory(new PropertyValueFactory<User, String>("name"));
        email.setCellValueFactory(new PropertyValueFactory<User, String>("email"));
        gender.setCellValueFactory(new PropertyValueFactory<User, String>("genderString"));
        dateOfBirth.setCellValueFactory(new PropertyValueFactory<User, String>("dateOfBirth"));

        // Add a event handler to the table so that when we click it it will show us the popup window
        table.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                User user = (User) table.getSelectionModel().getSelectedItem();
                try {
                    user.generatePopupWindow(event, editable);
                } catch (NoSuchMethodException | SecurityException | IllegalAccessException | IllegalArgumentException
                        | InvocationTargetException e) {
                    e.printStackTrace();
                }
            }
        });
                
        // Add the data to the table
        if (searchArgs == null) {
            table.setItems(DatabaseUser.getUserList());
        } else {
            table.setItems(DatabaseUser.readUserSearchAll(searchArgs));
        }
        

    }

    // Function to generate the popup window
    private void generatePopupWindow(MouseEvent event, boolean editable) throws NoSuchMethodException,
            SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        if (event.getClickCount() > 1) {
            AnchorPane pane;

            // Try to get the popup window and load it into pane
            try {
                pane = FXMLLoader.load(getClass().getResource("/com/example/javafx/fxml/User.fxml"));
            } catch (IOException e) {
                e.printStackTrace();
                return;
            }

            // Setup the popup window and the update buttons
            Scene scene = ((Node) event.getSource()).getScene();
            GUIController.setupPopupWindow(pane, (AnchorPane) scene.getRoot());
            GUIController.setupUpdateButton(editable, pane, this);

            // Setup the nodes in the popup window so they contain the values of the instance we opened the window of
            GUIController.setUpNode(TextField.class, editable, name, pane, "name");
            GUIController.setUpNode(TextField.class, editable, email, pane, "email");
            GUIController.setUpNode(TextField.class, editable, address, pane, "address");
            GUIController.setUpNode(TextField.class, editable, residence, pane, "residence");
            GUIController.setUpNode(TextField.class, editable, postalCode, pane, "zipCode");
            GUIController.setUpNode(TextField.class, editable, country, pane, "country");
            GUIController.setUpNode(MenuButton.class, editable, gender, pane, "gender");
            GUIController.setUpNode(DatePicker.class, editable, dateOfBirth, pane, "birthDate");
            
            setupTabs(pane, editable);
        }
    }

    // Setup table tabs
    public void setupTabs(AnchorPane pane, boolean editable) {
        
        ObservableList<Tab> tabs = ((TabPane) pane.lookup("#tables")).getTabs();
        
        for (Tab tab : tabs) {
            // get the pane of the tab and the table
            AnchorPane rootTabPane = (AnchorPane) tab.getContent();
            TableView table = (TableView) rootTabPane.lookup("#table");

            // Make a search arguments hashmap for the table generation methods
            HashMap<String, String> map = new HashMap<String, String>();
            map.put("userEmail", email);

            // Lookup the button
            Button btn = (Button)rootTabPane.lookup("#add");

            if (tab.getId().equals("enrolment")) {
                // Generate the enrolment table
                Enrollment.generateTable(table, editable, map);
                addEnrollment(table, btn, editable, pane);

            } else {
                // Generate the progress table
                Progress.generateTable(table, editable, map);
                addWebCast(table, btn, editable, pane, map);
            }
        }
    }

    public void addWebCast(TableView table, Button btn, boolean editable, AnchorPane pane, HashMap<String, String> map) {
        btn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                GUIController.clearTable(table);
                Webcast.generateTable(table, editable, email);
                btn.setText("view selected webcast");
                btn.setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent event) {
                        Webcast webcast = (Webcast)table.getSelectionModel().getSelectedItem();
                        try {
                            DatabaseProgress.createProgress((int)Math.random()*100, email, webcast.getContentItemId());
                        } catch (AlreadyExistsException e) {
                            e.printStackTrace();
                        }
                        GUIController.clearTable(table);
                        Progress.generateTable(table, editable, map);
                        ((Label)pane.lookup("#errorMessage")).setText("Webcast was successfully added!");
                        btn.setText("Enroll in webcast");
                        addWebCast(table, btn, editable, pane, map);
                    }
                });
            }
        });
        
    }

    // Function to add enrolment
    public void addEnrollment(TableView table, Button btn, boolean editable, AnchorPane pane) {
        // Set the event listener
        btn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                // Generate the table with courses the user is not enrolled in
                GUIController.clearTable(table);
                HashMap<String, String> map = new HashMap<String, String>();
                map.put("userEmail", email);
                Course.generateTable(table, false, map);

                // Change the text of the button
                btn.setText("Enroll in selected course");

                // Reset the event listener of the button
                btn.setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent event) {
                        // Add selected course to the user in the form of an enrolment
                        Course course = (Course)table.getSelectionModel().getSelectedItem();
                        try {
                            DatabaseEnrollment.createEnrollment(email, course.getTitle());
                        } catch (AlreadyExistsException e) {
                            e.printStackTrace();
                        }

                        // Regenerate the table
                        GUIController.clearTable(table);
                        Enrollment.generateTable(table, editable, map);

                        // Reset the button and tell the user the change was successful
                        ((Label)pane.lookup("#errorMessage")).setText("Enrolment was successful");
                        btn.setText("Enroll in course");
                        addEnrollment(table, btn, editable, pane);
                    }
                });  
            } 
        });
    }
}



