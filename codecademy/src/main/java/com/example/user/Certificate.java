package com.example.user;

import java.util.HashMap;

import com.example.database.DatabaseCertificate;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;

public class Certificate {
    private final int id;
    private int rating;
    private String employeeName;

    public Certificate(int id, int rating, String employeeName) {
        this.id = id;
        this.rating = rating;
        this.employeeName = employeeName;
    }

    public int getId() {
        return id;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public int getRating() {
        return rating;
    }

    public void setEmployeeName(String employeeName) {
        this.employeeName = employeeName;
    }

    public String getEmployeeName() {
        return employeeName;
    }

    // Generate table function 
    static public void generateTable(TableView<Certificate> table, boolean editable, int enrolmentID) {
        // Make table columns and add them to the table
        TableColumn<Certificate, Float> employeeName = new TableColumn<Certificate, Float>("employee Name");
        TableColumn<Certificate, String> rating = new TableColumn<Certificate, String>("Rating");     

        final ObservableList<TableColumn<Certificate, ?>> columns = FXCollections.observableArrayList();
        columns.add(employeeName);
        columns.add(rating);
        table.getColumns().addAll(columns);

        // Set the a value factory so the table can get the data from the instance of the class
        employeeName.setCellValueFactory(new PropertyValueFactory<Certificate, Float>("employeeName"));
        rating.setCellValueFactory(new PropertyValueFactory<Certificate, String>("rating"));

        // Add the data to the table
        final ObservableList<Certificate> data = FXCollections.observableArrayList();
        data.add(DatabaseCertificate.getEnrollmentCertificate(enrolmentID));
        table.setItems(data);
    }
}
