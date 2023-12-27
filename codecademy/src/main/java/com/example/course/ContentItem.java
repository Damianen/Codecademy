package com.example.course;

import java.time.LocalDate;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;

public abstract class ContentItem {
    protected String title;
    protected final int id;
    protected LocalDate publicationDate;
    protected String status;
    protected String description;
    protected int trackingNumber;

    public void setTitle(String title) {
        this.title = title;
    }
    public String getTitle() {
        return title;
    }

    public int getId() {
        return id;
    }

    public void setPublicationDate(LocalDate publicationDate) {
        this.publicationDate = publicationDate;
    }
    public LocalDate getPublicationDate() {
        return publicationDate;
    }

    public void setStatus(String status) {
        this.status = status;
    }
    public String getStatus() {
        return status;
    }

    public void setDescription(String description) {
        this.description = description;
    }
    public String getDescription() {
        return description;
    }

    public void setTrackingNumber(int trackingNumber) {
        this.trackingNumber = trackingNumber;
    }
    public int getTrackingNumber() {
        return trackingNumber;
    }

    public ContentItem(String title, int id, LocalDate publicationDate, String status, String description, int trackingNumber){
        this.title = title;
        this.id = id;
        this.publicationDate = publicationDate;
        this.status = status;
        this.description = description;
        this.trackingNumber = trackingNumber;
    }

    static protected void generateContentTable(TableView<ContentItem> table) {
        TableColumn<ContentItem, String> title = new TableColumn<ContentItem, String>("Title");
        TableColumn<ContentItem, String> status = new TableColumn<ContentItem, String>("status");
        TableColumn<ContentItem, String> description = new TableColumn<ContentItem, String>("Description");
        
        final ObservableList<TableColumn<ContentItem, ?>> columns = FXCollections.observableArrayList();
        columns.add(title);
        columns.add(status);
        columns.add(description);
        table.getColumns().addAll(columns);

        title.setCellValueFactory(new PropertyValueFactory<ContentItem, String>("title"));
        status.setCellValueFactory(new PropertyValueFactory<ContentItem, String>("status"));
        description.setCellValueFactory(new PropertyValueFactory<ContentItem, String>("description"));

        
    }

    static public void generateContentItemTable(TableView<ContentItem> table, boolean editable) {
        generateContentTable(table);

        table.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                ContentItem contentItem = (ContentItem)table.getSelectionModel().getSelectedItem();
                contentItem.generatePopupWindow(event, editable);
            }
        });

        final ObservableList<ContentItem> data = FXCollections.observableArrayList(
            new Module("test", 0, LocalDate.now(), "test", "test", 0, "test", "test", "test")
        );

        table.setItems(data);
    }

    abstract public void generatePopupWindow(MouseEvent event, boolean editable);
}
