package com.example.user;

import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import com.example.database.DatabaseContentItem;
import com.example.database.DatabaseModule;
import com.example.database.DatabaseProgress;
import com.example.database.DatabaseWebcast;
import com.example.course.ContentItem;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.util.Callback;

public class Progress {
    private int id;
    private int progressPercentage;
    private ContentItem contentItem;

    public Progress(int id, int progressPercentage, int contentItemID) {

        this.id = id;
        this.progressPercentage = progressPercentage;

        switch (DatabaseContentItem.getContentItemType(contentItemID)) {
            case "Webcast":
                this.contentItem = DatabaseWebcast.readWebcastWithContentItemID(contentItemID);
                break;

            case "Module":
                this.contentItem = DatabaseModule.readModuleWithContentItemID(contentItemID);
                break;

            default:
                this.contentItem = null;
                break;
        }
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setContentItemID(ContentItem contentItem) {
        this.contentItem = contentItem;
    }

    public ContentItem getContentItemID() {
        return contentItem;
    }

    public void setProgressPercentage(int progressPercentage) {
        this.progressPercentage = progressPercentage;
    }

    public int getProgressPercentage() {
        return progressPercentage;
    }

    // Generate table function 
    static public void generateTable(TableView<Progress> table, boolean editable, HashMap<String, String> searchArgs) {
        // Make table columns and add them to the table
        TableColumn<Progress, String> contentItemName = new TableColumn<Progress, String>("Content item name");
        TableColumn<Progress, Integer> progressPercentage = new TableColumn<Progress, Integer>("Progress percentage");

        final ObservableList<TableColumn<Progress, ?>> columns = FXCollections.observableArrayList();
        columns.add(contentItemName);
        columns.add(progressPercentage);
        table.getColumns().addAll(columns);

        // Make a callback so we can get the title form the content item that is associated with this instance of progress
        Callback<TableColumn.CellDataFeatures<Progress, String>, ObservableValue<String>> contentItemCallback;
        contentItemCallback = cellDataFeatures -> {
            Progress p = cellDataFeatures.getValue();
            String ciName = p.contentItem.getTitle();
            ObservableValue<String> titleObservableValue = new SimpleStringProperty(ciName);
            return titleObservableValue;
        };

        // Set the a value factory so the table can get the data from the instance of the class
        contentItemName.setCellValueFactory(contentItemCallback);
        progressPercentage.setCellValueFactory(new PropertyValueFactory<Progress, Integer>("progressPercentage"));

        // Add a event handler to the table so that when we click it it will show us the popup window
        // Which in this case will link to the associated content item
        table.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                Progress progress = (Progress) table.getSelectionModel().getSelectedItem();
                if (progress == null) {
                    return;
                }
                try {
                    progress.contentItem.generatePopupWindow(event, editable);
                } catch (NoSuchMethodException | SecurityException | IllegalAccessException | IllegalArgumentException
                        | InvocationTargetException e) {
                    e.printStackTrace();
                }
            }
        });

        // Add the data to the table
        table.setItems(DatabaseProgress.getProgressListWithUserEmail(searchArgs.get("userEmail")));
    }
}
