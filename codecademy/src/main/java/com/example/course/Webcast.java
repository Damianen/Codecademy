package com.example.course;

import java.time.LocalDate;

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
    private String url;
    private String speaker;
    private String orginization;

    public void setUrl(String url) {
        this.url = url;
    }
    public String getUrl() {
        return url;
    }

    public void setSpeaker(String speaker) {
        this.speaker = speaker;
    }
    public String getSpeaker() {
        return speaker;
    }

    public void setOrginization(String orginization) {
        this.orginization = orginization;
    }
    public String getOrginization() {
        return orginization;
    }

    public Webcast(String title, int id, LocalDate publicationDate, String status, String description, 
    int trackingNumber, String url, String speaker, String organization){
        super(title, id, publicationDate, status, description, trackingNumber);
        this.url = url;
        this.speaker = speaker;
        this.orginization = organization;
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
            String speakerString = m.getSpeaker();
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
            new Webcast("test", 0, LocalDate.now(), "test", "test", 0, "test", "test", "test")
        );

        table.setItems(data);
    }

    @Override
    public void generatePopupWindow(MouseEvent event, boolean editable) {
        
    }
}
