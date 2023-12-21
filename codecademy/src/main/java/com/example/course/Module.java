package com.example.course;

import java.io.IOException;

import com.example.javafx.GUIController;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;

public class Module {
    private String title;
    private String version;
    private String contactPersonName;
    private String contactPersonEmail;

    public void setTitle(String title) {
        this.title = title;
    }
    public String getTitle() {
        return title;
    }

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
    
    public Module(){
        this.title = "test";
        this.version = "test";
        this.contactPersonEmail = "test";
        this.contactPersonName = "test";
    }

    static public void generateTable(TableView<Module> table, boolean editable, AnchorPane rootPane) {
        TableColumn<Module, String> title = new TableColumn<Module, String>("Title");
        TableColumn<Module, String> version = new TableColumn<Module, String>("Version");

        final ObservableList<TableColumn<Module, ?>> columns = FXCollections.observableArrayList();
        columns.add(title);
        columns.add(version);
        table.getColumns().addAll(columns);

        title.setCellValueFactory(new PropertyValueFactory<Module, String>("title"));
        version.setCellValueFactory(new PropertyValueFactory<Module, String>("version"));

        table.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                generatePopupWindow(event, editable, (Module)table.getSelectionModel().getSelectedItem());
            }
        });

        final ObservableList<Module> data = FXCollections.observableArrayList(
            new Module(),
            new Module()
        );

        table.setItems(data);
    }

    static public void generatePopupWindow(MouseEvent event, boolean editable, Module module) {
        if (event.getClickCount()>1) {
            AnchorPane pane;
            
            try {
                pane = FXMLLoader.load(module.getClass().getResource("/com/example/javafx/fxml/module.fxml"));
            } catch (IOException e) {
                e.printStackTrace();
                return;
            }
            
            Scene scene = ((Node)event.getSource()).getScene();
            GUIController.setupPopupWindow(editable, pane, (AnchorPane)scene.getRoot());

            TextField title = (TextField)pane.lookup("#title");
            title.setEditable(editable);
            title.setText(module.title);

            TextField version = (TextField)pane.lookup("#version");
            version.setEditable(editable);
            version.setText(module.version);

            TextField contactPersonName = (TextField)pane.lookup("#contactPersonName");
            contactPersonName.setEditable(editable);
            contactPersonName.setText(module.contactPersonName);

            TextField contactPersonEmail = (TextField)pane.lookup("#contactPersonEmail");
            contactPersonEmail.setEditable(editable);
            contactPersonEmail.setText(module.contactPersonEmail);

            ObservableList<Tab> tabs = ((TabPane)pane.lookup("#tables")).getTabs();
            for (Tab tab : tabs) {
                AnchorPane rootTabPane = (AnchorPane)tab.getContent();
                TableView table = (TableView)rootTabPane.lookup("#table");
                if (tab.getId().equals("course")) {
                    Course.generateTable(table, editable, pane);
                }
            }
        }
    }
}
