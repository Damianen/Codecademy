package com.example.course;

import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;

import com.example.database.DatabaseSpeaker;
import com.example.javafx.GUIController;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;

public class Speaker {
    private int id;
    private String name;
    private String organization;

    public Speaker(int id, String name, String organization) {
        this.id = id;
        this.name = name;
        this.organization = organization;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getOrganization() {
        return organization;
    }

    public void setOrganization(String organization) {
        this.organization = organization;
    }

    static public HashMap<String, String> getArgsHashMap(AnchorPane pane) throws NoSuchMethodException,
            SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        HashMap<String, String> searchArgs = new HashMap<String, String>();
        searchArgs.put("name", GUIController.searchForNodeText("name", TextField.class, pane));
        searchArgs.put("organization", GUIController.searchForNodeText("organization", TextField.class, pane));
        return searchArgs;
    }

    static public void generateTable(TableView<Speaker> table, boolean editable, String Contactorganization) {
        TableColumn<Speaker, String> name = new TableColumn<Speaker, String>("Name");
        TableColumn<Speaker, String> organization = new TableColumn<Speaker, String>("Organization");     

        final ObservableList<TableColumn<Speaker, ?>> columns = FXCollections.observableArrayList();
        columns.add(name);
        columns.add(organization);
        table.getColumns().addAll(columns);

        name.setCellValueFactory(new PropertyValueFactory<Speaker, String>("name"));
        organization.setCellValueFactory(new PropertyValueFactory<Speaker, String>("organization"));

        

        table.setItems(DatabaseSpeaker.getSpeakerListSearch());
    }
}
