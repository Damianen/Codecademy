package com.example.course;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.time.LocalDate;
import java.util.HashMap;

import com.example.database.DatabaseModule;
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

    static public void generateTable(TableView<ContentItem> table, boolean editable,
            HashMap<String, String> searchArgs) {
        generateContentTable(table);

        TableColumn<ContentItem, String> speaker = new TableColumn<ContentItem, String>("Speaker");

        final ObservableList<TableColumn<ContentItem, ?>> columns = FXCollections.observableArrayList();
        columns.add(speaker);
        table.getColumns().addAll(columns);

        Callback<TableColumn.CellDataFeatures<ContentItem, String>, ObservableValue<String>> webcastCallback;
        webcastCallback = cellDataFeatures -> {
            Webcast m = (Webcast) cellDataFeatures.getValue();
            String speakerString = m.getSpeaker().getName();
            ObservableValue<String> titleObservableValue = new SimpleStringProperty(speakerString);
            return titleObservableValue;
        };

        speaker.setCellValueFactory(webcastCallback);

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

        final ObservableList<ContentItem> data = FXCollections.observableArrayList(
                new Webcast(0, "test", LocalDate.now(), Status.ACTIVE, "test", 0, "test", 0));

        table.setItems(data);
    }

    @Override
    public void generatePopupWindow(MouseEvent event, boolean editable) throws NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        if (event.getClickCount() > 1) {
            AnchorPane pane;

            try {
                pane = FXMLLoader.load(getClass().getResource("/com/example/javafx/fxml/webcast.fxml"));
            } catch (IOException e) {
                e.printStackTrace();
                return;
            }

            Scene scene = ((Node) event.getSource()).getScene();
            GUIController.setupPopupWindow(pane, (AnchorPane) scene.getRoot());
            GUIController.setupUpdateButton(editable, pane, this);

            GUIController.setUpNode(TextField.class, editable, title, pane, "title");
            GUIController.setUpNode(TextField.class, editable, url, pane, "url");
            GUIController.setUpNode(DatePicker.class, editable, publicationDate, pane, "publicationDate");
            GUIController.setUpNode(TextArea.class, editable, description, pane, "description");
            GUIController.setUpNode(MenuButton.class, editable, status, pane, "status");

            setupTabs(pane, editable);
        }
    }

    public void setupTabs(AnchorPane pane, boolean editable) {
        ObservableList<Tab> tabs = ((TabPane) pane.lookup("#tables")).getTabs();
        for (Tab tab : tabs) {
            AnchorPane rootTabPane = (AnchorPane) tab.getContent();
            TableView table = (TableView) rootTabPane.lookup("#table");
            Button btn = (Button) rootTabPane.lookup("#change");
            btn.setVisible(editable);
            Speaker.generateTable(table, false, speaker.getId());
            changeSpeaker(btn, editable, table, pane);
        }
    }

    private void changeSpeaker(Button btn, boolean editable, TableView table, AnchorPane pane) {
        btn.setOnAction(new EventHandler<ActionEvent>() {   
            @Override
            public void handle(ActionEvent event) {
                btn.setText("change to selected Speaker");
                GUIController.clearTable(table);
                Speaker.generateTable(table, editable, speaker.getId());
                btn.setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent event) {
                        Speaker speaker = (Speaker)table.getSelectionModel().getSelectedItem();
                        if (speaker == null) {return;}
                        btn.setText("change Speaker");
                        DatabaseWebcast.updateWebcast(id, title, publicationDate, 
                        status, description, url, 
                        speaker.getId());
                        speaker = (Speaker)table.getSelectionModel().getSelectedItem();
                        GUIController.clearTable(table);
                        Speaker.generateTable(table, false, speaker.getId());
                        changeSpeaker(btn, editable, table, pane);
                        ((Label)pane.lookup("#errorMessage")).setText("Change was successful");
                    }
                });
            }
        });
    }
}
