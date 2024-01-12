package com.example.course;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.time.LocalDate;
import java.util.HashMap;

import com.example.database.DatabaseContactPerson;
import com.example.database.DatabaseModule;
import com.example.javafx.GUIController;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
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
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.util.Callback;

public class Module extends ContentItem {

    private final int id;
    private double version;
    private ContactPerson contactPerson;
    private int orderNumber;

    public Module(int contentItemID, String title, LocalDate publicationDate, Status status, String description, int id,
            double version, String emailContactPerson, int orderNumber) {

        super(contentItemID, title, publicationDate, status, description);

        this.id = id;
        this.version = version;
        this.contactPerson = DatabaseContactPerson.readContactPerson(emailContactPerson);
        this.orderNumber = orderNumber;
    }

    public int getId() {
        return this.id;
    }

    public double getVersion() {
        return version;
    }

    public void setVersion(double version) {
        this.version = version;
    }

    public ContactPerson getContactPerson() {
        return contactPerson;
    }

    public void setContactPerson(ContactPerson contactPerson) {
        this.contactPerson = contactPerson;
    }

    public int getOrderNumber() {
        return orderNumber;
    }

    public void setOrderNumber(int orderNumber) {
        this.orderNumber = orderNumber;
    }

    // Get all of the all attributes from elements in a specific pane and return a hashmap with the values.
    static public HashMap<String, String> getArgsHashMap(AnchorPane pane) throws NoSuchMethodException,
            SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        
        HashMap<String, String> searchArgs = ContentItem.getArgsHashMap(pane);
        
        searchArgs.put("version", GUIController.searchForNodeText("version", TextField.class, pane));
        
        TableView table = (TableView)pane.lookup("#table");
        ContactPerson person = (ContactPerson)table.getSelectionModel().getSelectedItem();
        if (person != null) {
            searchArgs.put("contactPersonEmail", person.getEmail());
        }
        
        return searchArgs;
    }

    // Generate table function 
    static public void generateTable(TableView<ContentItem> table, boolean editable,
            HashMap<String, String> searchArgs) {
        generateContentTable(table);

        // Make table columns and add them to the table
        TableColumn<ContentItem, String> version = new TableColumn<ContentItem, String>("Version");

        final ObservableList<TableColumn<ContentItem, ?>> columns = FXCollections.observableArrayList();
        columns.add(version);
        table.getColumns().addAll(columns);

        // Make a callback so we can get the version of the module
        Callback<TableColumn.CellDataFeatures<ContentItem, String>, ObservableValue<String>> moduleCallback;
        moduleCallback = cellDataFeatures -> {
            Module m = (Module) cellDataFeatures.getValue();
            double versionString = m.getVersion();
            ObservableValue<String> titleObservableValue = new SimpleStringProperty(Double.toString(versionString));
            return titleObservableValue;
        };

        // Set the a value factory so the table can get the data from the instance of the class
        version.setCellValueFactory(moduleCallback);

        // Add a event handler to the table so that when we click it it will show us the popup window
        table.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                Module module = (Module) table.getSelectionModel().getSelectedItem();
                if (module == null) {
                    return;
                }
                try {
                    module.generatePopupWindow(event, editable);
                } catch (NoSuchMethodException | SecurityException | IllegalAccessException | IllegalArgumentException
                        | InvocationTargetException e) {
                    e.printStackTrace();
                }
            }
        });

        // Add the data to the table
        if (searchArgs.containsKey("courseTitle")) {
            table.setItems(DatabaseModule.getCourseModules(searchArgs.get("courseTitle")));
        } else if (searchArgs.containsKey("courseTitleNew")) {
            table.setItems(DatabaseModule.getModuleWithNoCourseList());
        }
        
    }

    // Function to generate the popup window
    @Override
    public void generatePopupWindow(MouseEvent event, boolean editable) throws NoSuchMethodException, SecurityException,
            IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        if (event.getClickCount() > 1) {
            AnchorPane pane;

            // Try to get the popup window and load it into pane
            try {
                pane = FXMLLoader.load(getClass().getResource("/com/example/javafx/fxml/module.fxml"));
            } catch (IOException e) {
                e.printStackTrace();
                return;
            }

            // Setup the popup window and the update buttons
            Scene scene = ((Node) event.getSource()).getScene();
            GUIController.setupPopupWindow(pane, (AnchorPane) scene.getRoot());
            GUIController.setupUpdateButton(editable, pane, this);

            // Setup the nodes in the popup window so they contain the values of the instance we opened the window of
            GUIController.setUpNode(TextField.class, editable, title, pane, "title");
            GUIController.setUpNode(TextField.class, editable, version, pane, "version");
            GUIController.setUpNode(TextArea.class, editable, description, pane, "description");
            GUIController.setUpNode(DatePicker.class, editable, publicationDate, pane, "publicationDate");
            GUIController.setUpNode(MenuButton.class, editable, status, pane, "status");

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
            
            if (tab.getId().equals("course")) {
                // Generate course table if module has a course
                HashMap<String, String> map = new HashMap<String, String>();
                String title = DatabaseModule.readModuleCourseTitle(contentItemId);
                if (title != null) {
                    map.put("title", title);
                    Course.generateTable(table, editable, map);     
                } 
                
            } else {
                // Generate contact person table
                ContactPerson.generateTable(table, false, contactPerson.getEmail());
                
                // Setup the change button for the contact person
                Button btn = (Button) rootTabPane.lookup("#change");
                btn.setVisible(editable);
                changeContactPerson(btn, editable, table, pane);
            }
        }
    }

    // Setup for the change contact person button
    private void changeContactPerson(Button btn, boolean editable, TableView table, AnchorPane pane) {
        // Set the event listener
        btn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                // Change the text of the button
                btn.setText("change to selected ContentItem");
                
                // Generate the table with the contact person that is currently its own
                GUIController.clearTable(table);
                ContactPerson.generateTable(table, editable, contactPerson.getEmail());
                
                // Reset the event listener of the button
                btn.setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent event) {
                        // Get the selected contact person and add it to the database
                        ContactPerson newContactPerson = ((ContactPerson)table.getSelectionModel().getSelectedItem());
                        if (newContactPerson == null) {return;}
                        DatabaseModule.updateModule(id, title, publicationDate, 
                            status, description, version, orderNumber, 
                            newContactPerson.getEmail(), 
                            DatabaseModule.readModuleCourseTitle(contentItemId));
                        
                        // Regenerate the table
                        contactPerson = newContactPerson;
                        GUIController.clearTable(table);
                        ContactPerson.generateTable(table, false, contactPerson.getEmail());
                        
                        // Reset the button and tell the user the change was successful
                        changeContactPerson(btn, editable, table, pane);
                        ((Label)pane.lookup("#errorMessage")).setText("Change was successful");
                        btn.setText("change Content Person");
                    }
                });
            }
        });
    }
}
