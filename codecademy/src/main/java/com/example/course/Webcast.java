package com.example.course;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.time.LocalDate;
import java.util.HashMap;

import com.example.database.DatabaseSpeaker;
import com.example.database.DatabaseWebcast;
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

public class Webcast extends ContentItem {
    private final int id;
    private String url;
    private Speaker speaker;

    public Webcast(int contentItemID, String title, LocalDate publicationDate, Status status, String description,
            int id, String url, int speakerID) {
        super(contentItemID, title, publicationDate, status, description);
        this.id = id;
        this.url = url;
        this.speaker = DatabaseSpeaker.readSpeaker(speakerID);
    }

    public int getId() {
        return id;
    }

    public Speaker getSpeaker() {
        return speaker;
    }

    public void setSpeaker(Speaker speaker) {
        this.speaker = speaker;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getUrl() {
        return url;
    }

    // Get all of the all attributes from elements in a specific pane and return a hashmap with the values.
    static public HashMap<String, String> getArgsHashMap(AnchorPane pane) throws NoSuchMethodException,
            SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        
        HashMap<String, String> searchArgs = ContentItem.getArgsHashMap(pane);
        
        searchArgs.put("url", GUIController.searchForNodeText("url", TextField.class, pane));
        
        TableView table = (TableView)pane.lookup("#table");
        Speaker speaker = (Speaker)table.getSelectionModel().getSelectedItem();
        if (speaker != null) {
            searchArgs.put("speakerID", String.valueOf(speaker.getId()));
        }
        
        return searchArgs;
    }

    // Generate table function 
    static public void generateTable(TableView<ContentItem> table, boolean editable, String username) {
        generateContentTable(table);

        // Make table columns and add them to the table
        TableColumn<ContentItem, String> speaker = new TableColumn<ContentItem, String>("Speaker");

        final ObservableList<TableColumn<ContentItem, ?>> columns = FXCollections.observableArrayList();
        columns.add(speaker);
        table.getColumns().addAll(columns);

        // Make a callback so the table can get the name of the speaker and show it in the table
        Callback<TableColumn.CellDataFeatures<ContentItem, String>, ObservableValue<String>> webcastCallback;
        webcastCallback = cellDataFeatures -> {
            Webcast m = (Webcast) cellDataFeatures.getValue();
            String speakerString = m.getSpeaker().getName();
            ObservableValue<String> titleObservableValue = new SimpleStringProperty(speakerString);
            return titleObservableValue;
        };

        // Set the a value factory so the table can get the data from the instance of the class
        speaker.setCellValueFactory(webcastCallback);

        // Add a event handler to the table so that when we click it it will show us the popup window
        table.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                Webcast webcast = (Webcast) table.getSelectionModel().getSelectedItem();
                try {
                    webcast.generatePopupWindow(event, editable);
                } catch (NoSuchMethodException | SecurityException | IllegalAccessException | IllegalArgumentException
                        | InvocationTargetException e) {
                    e.printStackTrace();
                }
            }
        });

        // Add the data to the table
        table.setItems(DatabaseWebcast.getNotSeenWebcastListForUser(username));
    }

    // Function to generate the popup window
    @Override
    public void generatePopupWindow(MouseEvent event, boolean editable) throws NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        if (event.getClickCount() > 1) {
            AnchorPane pane;

            // Try to get the popup window and load it into pane
            try {
                pane = FXMLLoader.load(getClass().getResource("/com/example/javafx/fxml/webcast.fxml"));
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
            GUIController.setUpNode(TextField.class, editable, url, pane, "url");
            GUIController.setUpNode(DatePicker.class, editable, publicationDate, pane, "publicationDate");
            GUIController.setUpNode(TextArea.class, editable, description, pane, "description");
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

            // Find and setup the change button
            Button btn = (Button) rootTabPane.lookup("#change");
            btn.setVisible(editable);
            changeSpeaker(btn, editable, table, pane);

            // Generate the window for speaker
            Speaker.generateTable(table, false, speaker.getId());
        }
    }

    // setup the change speaker function
    private void changeSpeaker(Button btn, boolean editable, TableView table, AnchorPane pane) {
        // Set the event listener
        btn.setOnAction(new EventHandler<ActionEvent>() {   
            @Override
            public void handle(ActionEvent event) {
                // Change the text of the button
                btn.setText("change to selected Speaker");

                // Generate the table with the speaker that is currently its own
                GUIController.clearTable(table);
                Speaker.generateTable(table, editable, speaker.getId());
                
                // Reset the event listener of the button
                btn.setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent event) {
                        // Get the selected speaker and add it to the database
                        Speaker speaker = (Speaker)table.getSelectionModel().getSelectedItem();
                        if (speaker == null) {return;}
                        DatabaseWebcast.updateWebcast(id, title, publicationDate, 
                            status, description, url, 
                            speaker.getId());
                        
                        // Regenerate the table
                        GUIController.clearTable(table);
                        Speaker.generateTable(table, false, speaker.getId());

                        // Reset the button and tell the user the change was successful
                        changeSpeaker(btn, editable, table, pane);
                        ((Label)pane.lookup("#errorMessage")).setText("Change was successful");
                        btn.setText("change Speaker");
                    }
                });
            }
        });
    }
}
