package com.example.user;

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
    private float rating;
    private String employeeName;

    public int getId() {
        return id;
    }

    public void setRating(float rating) {
        this.rating = rating;
    }
    public float getRating() {
        return rating;
    }
    
    public void setEmployeeName(String employeeName) {
        this.employeeName = employeeName;
    }
    public String getEmployeeName() {
        return employeeName;
    }

    public Certificate(int id, float rating, String employeeName){
        this.id = id;
        this.rating = rating;
        this.employeeName = employeeName;
    }

    static public void generateTable(TableView<Certificate> table, boolean editable, AnchorPane rootPane) {
        TableColumn<Certificate, Float> employeeName = new TableColumn<Certificate, Float>("employee Name");
        TableColumn<Certificate, String> rating = new TableColumn<Certificate, String>("Rating");     

        final ObservableList<TableColumn<Certificate, ?>> columns = FXCollections.observableArrayList();
        columns.add(employeeName);
        columns.add(rating);
        table.getColumns().addAll(columns);

        employeeName.setCellValueFactory(new PropertyValueFactory<Certificate, Float>("employeeName"));
        rating.setCellValueFactory(new PropertyValueFactory<Certificate, String>("rating"));

        final ObservableList<Certificate> data = FXCollections.observableArrayList(
            new Certificate(0, 0, "test")
        );

        table.setItems(data);
    }
}
