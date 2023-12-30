package com.example.user;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.time.LocalDate;
import java.util.HashMap;

import com.example.course.Module;
import com.example.course.Webcast;
import com.example.course.ContentItem.Status;
import com.example.database.DatabaseContentItem;
import com.example.database.DatabaseModule;
import com.example.database.DatabaseProgress;
import com.example.database.DatabaseWebcast;
import com.example.javafx.GUIController;

import com.example.course.ContentItem;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.MenuButton;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.util.Callback;

public class Progress {
    private int id;
    private int progressPercentage;
    private ContentItem contentItem;

    public Progress(int id, int progressPercentage, int contentItemID) {

        this.id = id;
        this.progressPercentage = progressPercentage;

        switch (DatabaseContentItem.getContentItemType()) {
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

    static public void generateTable(TableView<Progress> table, boolean editable, HashMap<String, String> searchArgs) {

        TableColumn<Progress, String> contentItemName = new TableColumn<Progress, String>("Content item name");
        TableColumn<Progress, Integer> progressPercentage = new TableColumn<Progress, Integer>("Progress percentage");

        Callback<TableColumn.CellDataFeatures<Progress, String>, ObservableValue<String>> contentItemCallback;
        contentItemCallback = cellDataFeatures -> {
            Progress p = cellDataFeatures.getValue();
            String ciName = p.contentItem.getTitle();
            ObservableValue<String> titleObservableValue = new SimpleStringProperty(ciName);
            return titleObservableValue;
        };

        final ObservableList<TableColumn<Progress, ?>> columns = FXCollections.observableArrayList();
        columns.add(contentItemName);
        columns.add(progressPercentage);
        table.getColumns().addAll(columns);

        contentItemName.setCellValueFactory(contentItemCallback);
        progressPercentage.setCellValueFactory(new PropertyValueFactory<Progress, Integer>("progressPercentage"));

        table.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                Progress progress = (Progress) table.getSelectionModel().getSelectedItem();
                try {
                    progress.contentItem.generatePopupWindow(event, editable);
                } catch (NoSuchMethodException | SecurityException | IllegalAccessException | IllegalArgumentException
                        | InvocationTargetException e) {
                    e.printStackTrace();
                }
            }
        });
    }
}
