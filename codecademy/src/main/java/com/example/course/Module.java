package com.example.course;

import java.io.IOException;
import java.time.LocalDate;

import com.example.javafx.GUIController;
import com.example.user.Progress;

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
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.util.Callback;

public class Module extends ContentItem{
    private String version;
    private String contactPersonName;
    private String contactPersonEmail;

    public void setVersion(String version) {
        this.version = version;
    }
    public String getVersion() {
        return version;
    }

    public void setContactPersonName(String contactPersonName) {
        this.contactPersonName = contactPersonName;
    }
    public String getContactPersonName() {
        return contactPersonName;
    }

    public void setContactPersonEmail(String contactPersonEmail) {
        this.contactPersonEmail = contactPersonEmail;
    }
    public String getContactPersonEmail() {
        return contactPersonEmail;
    }
    
    public Module(String title, int id, LocalDate publicationDate, Status status, String description, String version, String contactPersonEmail, String contactPersonName){
        super(id, title, publicationDate, status ,description);
        this.version = version;
        this.contactPersonEmail = contactPersonName;
        this.contactPersonName = contactPersonEmail;
    }

    static public void generateTable(TableView<ContentItem> table, boolean editable) {
        generateContentTable(table);
        
        TableColumn<ContentItem, String> version = new TableColumn<ContentItem, String>("Version");

        final ObservableList<TableColumn<ContentItem, ?>> columns = FXCollections.observableArrayList();
        columns.add(version);
        table.getColumns().addAll(columns);

        Callback<TableColumn.CellDataFeatures<ContentItem, String>, ObservableValue<String>> moduleCallback;
        moduleCallback = cellDataFeatures -> {
            Module m = (Module)cellDataFeatures.getValue();
            String versionString = m.getVersion();
            ObservableValue<String> titleObservableValue = new SimpleStringProperty(versionString);
            return titleObservableValue;
        };

        version.setCellValueFactory(moduleCallback);

        table.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                Module module = (Module)table.getSelectionModel().getSelectedItem();
                module.generatePopupWindow(event, editable);
            }
        });

        final ObservableList<ContentItem> data = FXCollections.observableArrayList(
            new Module("test", 0, LocalDate.now(), Status.ACTIVE, "test", "test", "test", "test")
        );

        table.setItems(data);
    }

    @Override
    public void generatePopupWindow(MouseEvent event, boolean editable) {
        if (event.getClickCount()>1) {
            AnchorPane pane;
            
            try {
                pane = FXMLLoader.load(getClass().getResource("/com/example/javafx/fxml/module.fxml"));
            } catch (IOException e) {
                e.printStackTrace();
                return;
            }
            
            Scene scene = ((Node)event.getSource()).getScene();
            GUIController.setupPopupWindow(editable, pane, (AnchorPane)scene.getRoot());

            TextField title = (TextField)pane.lookup("#title");
            title.setEditable(editable);
            title.setText(this.title);

            TextField version = (TextField)pane.lookup("#version");
            version.setEditable(editable);
            version.setText(this.version);

            TextField contactPersonName = (TextField)pane.lookup("#contactPersonName");
            contactPersonName.setEditable(editable);
            contactPersonName.setText(this.contactPersonName);

            TextField contactPersonEmail = (TextField)pane.lookup("#contactPersonEmail");
            contactPersonEmail.setEditable(editable);
            contactPersonEmail.setText(this.contactPersonEmail);

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

            ObservableList<Tab> tabs = ((TabPane)pane.lookup("#tables")).getTabs();
            for (Tab tab : tabs) {
                AnchorPane rootTabPane = (AnchorPane)tab.getContent();
                TableView table = (TableView)rootTabPane.lookup("#table");
                if (tab.getId().equals("course")) {
                    Course.generateTable(table, editable);
                }
            }
        }
    }
}
