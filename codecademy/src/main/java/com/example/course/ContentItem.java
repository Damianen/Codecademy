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
    protected final int id;
    protected String title;
    protected LocalDate publicationDate;
    protected String description;
    protected Status status;

    public enum Status{
        CONCEPT,
        ACTIVE,
        ARCHIVED
    }

    public ContentItem(int id, String title, LocalDate publicationDate, Status status, String description){
        this.id = id;
        this.title = title;
        this.publicationDate = publicationDate;
        this.status = status;
        this.description = description;
    }

    public int getContentItemId() {
        return id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    public void setPublicationDate(LocalDate publicationDate) {
        this.publicationDate = publicationDate;
    }
    public LocalDate getPublicationDate() {
        return publicationDate;
    }

    public void setStatus(Status status) {
        this.status = status;
    }
    public Status getStatus() {
        return status;
    }

    public void setDescription(String description) {
        this.description = description;
    }
    public String getDescription() {
        return description;
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
            new Module("test", 0, LocalDate.now(), Status.ACTIVE, "test", "test", "test", "test")
        );

        table.setItems(data);
    }

    abstract public void generatePopupWindow(MouseEvent event, boolean editable);
}
