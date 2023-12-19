package com.example.javafx;

import java.io.IOException;

import com.example.course.Course;

import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.control.Tab;
import javafx.scene.control.TableView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

public class GUIController {
    private Stage stage;
    private Scene scene;
    private Parent root;
    private boolean skip = false;

    public void switchPage(ActionEvent event) throws IOException {
        try {
            root = FXMLLoader.load(getClass().getResource(getFXMLPath(event.getSource())));
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public void switchTab(Event event) throws IOException {
        if (!skip) {
            skip = true;
            AnchorPane pane = (AnchorPane)((Tab)event.getSource()).getContent();
            for (Node node : pane.getChildren()) {
                if (node instanceof MenuButton) {
                    setMenuButtonActions((MenuButton)node, skip);
                }
            }
        } else {
            skip = false;
        }
    }

    private String getFXMLPath(Object obj) {
        Button clickedButton = (Button) obj;
        switch (clickedButton.getId()) {
            case ("updateButton"): return "/com/example/javafx/fxml/Update.fxml";
            case ("createButton"): return "/com/example/javafx/fxml/Create.fxml";
            case ("deleteButton"): return "/com/example/javafx/fxml/Delete.fxml";
            case ("readButton"):   return "/com/example/javafx/fxml/Read.fxml";
            case ("homeButton"):   return "/com/example/javafx/fxml/Start.fxml";
        }

        return "";
    }

    public void search(ActionEvent event) throws IOException {
        AnchorPane tabRootNode = (AnchorPane)((Node)event.getSource()).getParent();
        TableView table = (TableView)tabRootNode.lookup("#table");
        table.getItems().clear();
        table.getColumns().clear();

        boolean editable = false;
        if (tabRootNode.getScene().getRoot().getId().equals("updateRoot")) { editable = true; }

        switch (tabRootNode.getId()) {
            case "course":
                Course.generateTable(table, editable, tabRootNode);
                break;
            case "module":
                break;
            case "user":
                break;
            case "enrolment":
                break;
        }
    }

    public static void closePopupWindow(AnchorPane popupPane, Rectangle rect) {
        AnchorPane root = (AnchorPane)popupPane.getScene().getRoot();
        root.getChildren().removeAll(popupPane, rect);
    }

    public static void setMenuButtonActions(MenuButton menuButton, boolean editable) {
        for (MenuItem item : menuButton.getItems()) {
            if (!editable) {
                item.setOnAction(null);
            } else {
                item.setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent event) {
                        menuButton.setText(item.getText());
                    }
                });
            }
        }
    }
    
    public void create(ActionEvent event) throws IOException {
        
    }

    public void delete(ActionEvent event) throws IOException {

    }

    public void Update(ActionEvent event) throws IOException {

    }
}
