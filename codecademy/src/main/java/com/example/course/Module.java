package com.example.course;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.time.LocalDate;
import java.util.HashMap;

import com.example.database.DatabaseContactPerson;
import com.example.database.DatabaseModule;
import com.example.database.DatabaseSpeaker;
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
import javafx.scene.control.MenuButton;
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

public class Module extends ContentItem {

    private final int id;
    private double version;
    private ContactPerson contactPerson;
    private int orderNumber;

    public Module(int contentItemID, String title, LocalDate publicationDate, Status status, String description, int id,
            double version, String emailContactPerson, int orderNumber) {

        super(contentItemID, title, publicationDate, status, description);

        this.id = id;
        this.version = version;
        this.contactPerson = DatabaseContactPerson.readContactPerson(emailContactPerson);
        this.orderNumber = orderNumber;
    }

    public int getId() {
        return this.id;
    }

    public double getVersion() {
        return version;
    }

    public void setVersion(double version) {
        this.version = version;
    }

    public ContactPerson getContactPerson() {
        return contactPerson;
    }

    public void setContactPerson(ContactPerson contactPerson) {
        this.contactPerson = contactPerson;
    }

    public int getOrderNumber() {
        return orderNumber;
    }

    public void setOrderNumber(int orderNumber) {
        this.orderNumber = orderNumber;
    }

    static public HashMap<String, String> getArgsHashMap(AnchorPane pane) throws NoSuchMethodException,
            SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        HashMap<String, String> searchArgs = ContentItem.getArgsHashMap(pane);
        searchArgs.put("version", GUIController.searchForNodeText("version", TextField.class, pane));
        TableView table = (TableView)pane.lookup("#table");
        ContactPerson person = (ContactPerson)table.getSelectionModel().getSelectedItem();
        searchArgs.put("contactPersonEmail", person.getEmail());
        return searchArgs;
    }

    static public void generateTable(TableView<ContentItem> table, boolean editable,
            HashMap<String, String> searchArgs) {
        generateContentTable(table);

        TableColumn<ContentItem, String> version = new TableColumn<ContentItem, String>("Version");

        final ObservableList<TableColumn<ContentItem, ?>> columns = FXCollections.observableArrayList();
        columns.add(version);
        table.getColumns().addAll(columns);

        Callback<TableColumn.CellDataFeatures<ContentItem, String>, ObservableValue<String>> moduleCallback;
        moduleCallback = cellDataFeatures -> {
            Module m = (Module) cellDataFeatures.getValue();
            double versionString = m.getVersion();
            ObservableValue<String> titleObservableValue = new SimpleStringProperty(Double.toString(versionString));
            return titleObservableValue;
        };

        version.setCellValueFactory(moduleCallback);

        table.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                Module module = (Module) table.getSelectionModel().getSelectedItem();
                try {
                    module.generatePopupWindow(event, editable);
                } catch (NoSuchMethodException | SecurityException | IllegalAccessException | IllegalArgumentException
                        | InvocationTargetException e) {
                    e.printStackTrace();
                }
            }
        });


        //table.setItems(DatabaseModule.readCourseTitleModule(searchArgs.get("courseTitle")));
    }

    @Override
    public void generatePopupWindow(MouseEvent event, boolean editable) throws NoSuchMethodException, SecurityException,
            IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        if (event.getClickCount() > 1) {
            AnchorPane pane;

            try {
                pane = FXMLLoader.load(getClass().getResource("/com/example/javafx/fxml/module.fxml"));
            } catch (IOException e) {
                e.printStackTrace();
                return;
            }

            Scene scene = ((Node) event.getSource()).getScene();
            GUIController.setupPopupWindow(pane, (AnchorPane) scene.getRoot());
            GUIController.setupUpdateButton(editable, pane, this);

            GUIController.setUpNode(TextField.class, editable, title, pane, "title");
            GUIController.setUpNode(TextField.class, editable, version, pane, "version");

            TextField contactPersonName = (TextField) pane.lookup("#contactPersonName");
            contactPersonName.setEditable(editable);
            contactPersonName.setText(this.contactPerson.getName());

            TextField contactPersonEmail = (TextField) pane.lookup("#contactPersonEmail");
            contactPersonEmail.setEditable(editable);
            contactPersonEmail.setText(this.contactPerson.getEmail());

            DatePicker pubDate = (DatePicker) pane.lookup("#publicationDate");
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

            TextArea description = (TextArea) pane.lookup("#description");
            description.setEditable(editable);
            description.setText(this.description);

            setupTabs(pane, editable);
        }
    }

    public void setupTabs(AnchorPane pane, boolean editable) {
        ObservableList<Tab> tabs = ((TabPane) pane.lookup("#tables")).getTabs();
        for (Tab tab : tabs) {
            AnchorPane rootTabPane = (AnchorPane) tab.getContent();
            TableView table = (TableView) rootTabPane.lookup("#table");
            if (tab.getId().equals("course")) {
                HashMap<String, String> map = new HashMap<String, String>();
                Course.generateTable(table, editable, map);
            }
        }
    }
}
