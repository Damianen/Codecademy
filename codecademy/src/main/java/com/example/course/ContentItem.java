package com.example.course;

import java.lang.reflect.InvocationTargetException;
import java.time.LocalDate;
import java.util.HashMap;

import com.example.database.DatabaseContentItem;
import com.example.database.DatabaseModule;
import com.example.database.DatabaseWebcast;
import com.example.javafx.GUIController;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.scene.control.DatePicker;
import javafx.scene.control.MenuButton;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;

public abstract class ContentItem {
    protected final int contentItemId;
    protected String title;
    protected LocalDate publicationDate;
    protected String description;
    protected Status status;

    public enum Status {
        CONCEPT,
        ACTIVE,
        ARCHIVED
    }

    public ContentItem(int id, String title, LocalDate publicationDate, Status status, String description) {
        this.contentItemId = id;
        this.title = title;
        this.publicationDate = publicationDate;
        this.status = status;
        this.description = description;
    }

    public int getContentItemId() {
        return this.contentItemId;
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

    // get the type of a content item so either a module or a webcast
    public String getType() {
        return DatabaseContentItem.getContentItemType(contentItemId);
    }

    // Get all of the attributes from elements in a specific pane and return a hashmap with the values.
    static public HashMap<String, String> getArgsHashMap(AnchorPane pane) throws NoSuchMethodException,
            SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        HashMap<String, String> searchArgs = new HashMap<String, String>();
        
        searchArgs.put("title", GUIController.searchForNodeText("title", TextField.class, pane));
        searchArgs.put("description", GUIController.searchForNodeText("description", TextArea.class, pane));
        searchArgs.put("status", GUIController.searchForNodeText("status",MenuButton.class, pane));
        
        DatePicker date = (DatePicker) pane.lookup("#publicationDate");
        if (date.getValue() != null) {
            searchArgs.put("publicationDate", date.getValue().toString());
        }
        
        return searchArgs;
    }

    // Generate table function where we only set the table columns
    static protected void generateContentTable(TableView<ContentItem> table) {
        // Make table columns and add them to the table
        TableColumn<ContentItem, String> title = new TableColumn<ContentItem, String>("Title");
        TableColumn<ContentItem, String> status = new TableColumn<ContentItem, String>("status");
        TableColumn<ContentItem, String> description = new TableColumn<ContentItem, String>("Description");
        TableColumn<ContentItem, String> type = new TableColumn<ContentItem, String>("Type");

        final ObservableList<TableColumn<ContentItem, ?>> columns = FXCollections.observableArrayList();
        columns.add(title);
        columns.add(status);
        columns.add(description);
        columns.add(type);
        table.getColumns().addAll(columns);

        // Set the a value factory so the table can get the data from the instance of the class
        title.setCellValueFactory(new PropertyValueFactory<ContentItem, String>("title"));
        status.setCellValueFactory(new PropertyValueFactory<ContentItem, String>("status"));
        description.setCellValueFactory(new PropertyValueFactory<ContentItem, String>("description"));
        type.setCellValueFactory(new PropertyValueFactory<ContentItem, String>("type"));
    }

    // Generate table function specifically for the content item tables not for the module or webcast
    static public void generateContentItemTable(TableView<ContentItem> table, boolean editable,
            HashMap<String, String> searchArgs) {
        generateContentTable(table);

        // Add a event handler to the table so that when we click it it will show us the popup window
        table.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                ContentItem contentItem = (ContentItem) table.getSelectionModel().getSelectedItem();
                if (contentItem == null) {
                    return;
                }
                try {
                    contentItem.generatePopupWindow(event, editable);
                } catch (NoSuchMethodException | SecurityException | IllegalAccessException | IllegalArgumentException
                        | InvocationTargetException e) {
                    e.printStackTrace();
                }
            }
        });

        // Add the data to the table
        final ObservableList<ContentItem> data = FXCollections.observableArrayList();

        for (Module module : DatabaseModule.readModuleList()) {
            data.add(module);
        }

        for (Webcast webcast : DatabaseWebcast.readWebcastList()) {
            data.add(webcast);
        }
        

        table.setItems(data);
    }

    abstract public void generatePopupWindow(MouseEvent event, boolean editable) throws NoSuchMethodException,
            SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException;
}
