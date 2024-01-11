package com.example.course;

import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;

import com.example.database.DatabaseContactPerson;
import com.example.javafx.GUIController;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;

public class ContactPerson {

    private String email;
    private String name;

    public ContactPerson(String email, String name){
        this.email = email;
        this.name = name;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getEmail() {
        return email;
    }

    public void setName(String name) {
        this.name = name;
    }
    
    public String getName() {
        return name;
    }

    static public HashMap<String, String> getArgsHashMap(AnchorPane pane) throws NoSuchMethodException,
            SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        HashMap<String, String> searchArgs = new HashMap<String, String>();
        searchArgs.put("name", GUIController.searchForNodeText("name", TextField.class, pane));
        searchArgs.put("email", GUIController.searchForNodeText("email", TextField.class, pane));
        return searchArgs;
    }

    static public void generateTable(TableView<ContactPerson> table, boolean editable, String ContactEmail) {
        TableColumn<ContactPerson, String> name = new TableColumn<ContactPerson, String>("Name");
        TableColumn<ContactPerson, String> email = new TableColumn<ContactPerson, String>("Email");     

        final ObservableList<TableColumn<ContactPerson, ?>> columns = FXCollections.observableArrayList();
        columns.add(name);
        columns.add(email);
        table.getColumns().addAll(columns);

        name.setCellValueFactory(new PropertyValueFactory<ContactPerson, String>("name"));
        email.setCellValueFactory(new PropertyValueFactory<ContactPerson, String>("email"));

        if (ContactEmail == null) {
            table.setItems(DatabaseContactPerson.getContactPersonListSearch());
        } else if (editable) {
            table.setItems(DatabaseContactPerson.readForNewContactPerson(ContactEmail));
        } else {
            ObservableList<ContactPerson> list = FXCollections.observableArrayList();
            list.add(DatabaseContactPerson.readContactPerson(ContactEmail));
            table.setItems(list);
        }
    }
}
