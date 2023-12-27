package com.example.course;

import java.time.LocalDate;

import com.example.database.DatabaseSpeaker;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
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

    public Speaker getSpeaker() {
        return speaker;
    }

    public int getContentItemId() {
        return super.id;
    }

    public int getId() {
        return id;
    }
    

    public void setUrl(String url) {
        this.url = url;
    }
    public String getUrl() {
        return url;
    }
    
    static public void generateTable(TableView<ContentItem> table, boolean editable, AnchorPane rootPane) {
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
            
        );

        table.setItems(data);
    }

    @Override
    public void generatePopupWindow(MouseEvent event, boolean editable) {
        
    }
}
