package com.example.course;

import java.io.IOException;
import java.time.LocalDate;

import com.example.database.DatabaseSpeaker;
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
import javafx.scene.control.DatePicker;
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

    public Webcast(int contentItemID, String title, LocalDate publicationDate, Status status, String description, int id, String url, int speakerID){
        super(contentItemID, title, publicationDate, status, description);
        this.id = id;
        this.url = url;
        this.speaker = DatabaseSpeaker.readSpeaker(speakerID);
    }

    public int getId() {
        return id;
    }

    public int getContentItemId() {
        return super.id;
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
    
    static public void generateTable(TableView<ContentItem> table, boolean editable) {
        generateContentTable(table);
        
        TableColumn<ContentItem, String> speaker = new TableColumn<ContentItem, String>("Speaker");

        final ObservableList<TableColumn<ContentItem, ?>> columns = FXCollections.observableArrayList();
        columns.add(speaker);
        table.getColumns().addAll(columns);

        Callback<TableColumn.CellDataFeatures<ContentItem, String>, ObservableValue<String>> webcastCallback;
        webcastCallback = cellDataFeatures -> {
            Webcast m = (Webcast)cellDataFeatures.getValue();
            String speakerString = m.getSpeaker().getName();
            ObservableValue<String> titleObservableValue = new SimpleStringProperty(speakerString);
            return titleObservableValue;
        };

        speaker.setCellValueFactory(webcastCallback);

        table.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                Webcast webcast = (Webcast)table.getSelectionModel().getSelectedItem();
                webcast.generatePopupWindow(event, editable);
            }
        });

        final ObservableList<ContentItem> data = FXCollections.observableArrayList(
            new Webcast(0, "test", LocalDate.now(), Status.ACTIVE, "test", 0, "test", 0)
        );

        table.setItems(data);
    }

    @Override
    public void generatePopupWindow(MouseEvent event, boolean editable) {
        if (event.getClickCount()>1) {
            AnchorPane pane;
            
            try {
                pane = FXMLLoader.load(getClass().getResource("/com/example/javafx/fxml/webcast.fxml"));
            } catch (IOException e) {
                e.printStackTrace();
                return;
            }
            
            Scene scene = ((Node)event.getSource()).getScene();
            GUIController.setupPopupWindow(editable, pane, (AnchorPane)scene.getRoot());

            TextField title = (TextField)pane.lookup("#title");
            title.setEditable(editable);
            title.setText(this.title);

            TextField url = (TextField)pane.lookup("#url");
            url.setEditable(editable);
            url.setText(this.url);

            TextField speaker = (TextField)pane.lookup("#speaker");
            speaker.setEditable(editable);
            speaker.setText(this.speaker.getName());

            TextField organization = (TextField)pane.lookup("#organization");
            organization.setEditable(editable);
            organization.setText(this.speaker.getOranization());

            DatePicker pubDate = (DatePicker)pane.lookup("#publicationDate");
            pubDate.setEditable(editable);
            if (!editable) {
                pubDate.setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent event) {
                        pubDate.setValue(publicationDate);
                    }
                });
            }
            pubDate.setValue(publicationDate);

            TextArea description = (TextArea)pane.lookup("#description");
            description.setEditable(editable);
            description.setText(this.description);
        }
    }
}
