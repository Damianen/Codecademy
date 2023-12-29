package com.example.javafx;

import static com.example.course.Course.DifficultyLevel.valueOf;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.time.LocalDate;
import java.util.HashMap;

import com.example.course.ContentItem;
import com.example.course.Course;
import com.example.course.Course.DifficultyLevel;
import com.example.database.DatabaseCourse;
import com.example.exeptions.AlreadyExistsException;
import com.example.exeptions.CannotBeEmptyException;
import com.example.user.User;

import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.Labeled;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.control.Tab;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.TextArea;
import javafx.scene.control.ToolBar;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class GUIController {
    private Stage stage;
    private Scene scene;
    private Parent root;
    private boolean skip = true;

    public void switchPage(ActionEvent event) throws IOException {
        try {
            root = FXMLLoader.load(getClass().getResource(getFXMLPath(event.getSource())));
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public void switchTab(Event event) throws IOException {
        if (!skip) {
            skip = true;
            AnchorPane pane = (AnchorPane) ((Tab) event.getSource()).getContent();
            for (Node node : pane.getChildren()) {
                if (node instanceof MenuButton) {
                    setMenuButtonActions((MenuButton) node, skip);
                }
            }

            if (!((Scene) ((Tab) event.getSource()).getContent().getScene()).getRoot().getId().equals("createRoot")) {
                TableView table = (TableView) pane.lookup("#table");
                boolean editable = false;
                if (!((Scene) ((Tab) event.getSource()).getContent().getScene()).getRoot().getId().equals("readRoot")) {
                    editable = true;
                }

                if (pane.idProperty() == null) {
                    Course.generateTable(table, editable, null);
                    return;
                }

                switch (pane.getId()) {
                    case "course":
                        Course.generateTable(table, editable, null);
                        break;
                }
            }
        } else {
            skip = false;
        }
    }

    private String getFXMLPath(Object obj) {
        Button clickedButton = (Button) obj;
        switch (clickedButton.getId()) {
            case ("updateButton"):
                return "/com/example/javafx/fxml/Update.fxml";
            case ("createButton"):
                return "/com/example/javafx/fxml/Create.fxml";
            case ("deleteButton"):
                return "/com/example/javafx/fxml/Delete.fxml";
            case ("readButton"):
                return "/com/example/javafx/fxml/Read.fxml";
            case ("homeButton"):
                return "/com/example/javafx/fxml/Start.fxml";
            case ("new"):
                return "/com/example/javafx/fxml/Start.fxml";
        }

        return "";
    }

    public void search(ActionEvent event) throws IOException, NoSuchMethodException,
            SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        AnchorPane tabRootNode = (AnchorPane) ((Node) event.getSource()).getParent();
        TableView table = (TableView) tabRootNode.lookup("#table");
        table.getItems().clear();
        table.getColumns().clear();

        boolean editable = false;
        if (tabRootNode.getScene().getRoot().getId().equals("updateRoot")) {
            editable = true;
        }

        switch (tabRootNode.getId()) {
            case "course":
                Course.generateTable(table, editable, Course.getArgsHashMap(tabRootNode));
                break;
            case "contentItem":
                ContentItem.generateContentItemTable(table, editable, new HashMap<String, String>());
                break;
            case "user":
                User.generateTable(table, editable, new HashMap<String, String>());
                break;
        }
    }

    public static String searchForNodeText(String id, Class<?> nodeClass, AnchorPane pane) throws NoSuchMethodException,
            SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        Node node = pane.lookup("#" + id);
        Method method = nodeClass.getMethod("getText");
        return (String) method.invoke(nodeClass.cast(node));
    }

    public static void closePopupWindow(AnchorPane popupPane, Rectangle rect) {
        AnchorPane root = (AnchorPane) popupPane.getScene().getRoot();
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

    public static void setDatePickerActions(DatePicker date, boolean editable, LocalDate value) {
        date.setEditable(editable);
        if (!editable) {
            date.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                    date.setValue(value);
                }
            });
        }
        date.setValue(value);
    }

    public static void setupPopupWindow(AnchorPane popupPane, AnchorPane rootPane) {
        Rectangle rect = new Rectangle(960, 540, Paint.valueOf("#0000008f"));
        rootPane.getChildren().addAll(rect, popupPane);
        popupPane.setLayoutX(180);
        popupPane.setLayoutY(70);

        ToolBar toolbar = (ToolBar) popupPane.lookup("#toolBar");
        ((Button) toolbar.getItems().get(0)).setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                AnchorPane root = (AnchorPane) popupPane.getScene().getRoot();
                root.getChildren().removeAll(popupPane, rect);
            }
        });
    }

    public static void setupUpdateButton(boolean editable, AnchorPane popupPane, Object obj) {
        Button updateButton = (Button) popupPane.lookup("#updateButton");
        updateButton.setVisible(editable);
        updateButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                try {
                    update(event, obj);
                } catch (NoSuchMethodException | SecurityException | IllegalAccessException
                        | IllegalArgumentException | InvocationTargetException | IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public static void setUpNode(Class<?> nodeClass, Boolean editable, Object value, AnchorPane pane, String id)
            throws NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException,
            InvocationTargetException {
        Node node = pane.lookup("#" + id);
        Method methodSetText = nodeClass.getMethod("setText", value.getClass());
        if (nodeClass == MenuButton.class) {
            setMenuButtonActions((MenuButton) node, editable);
            methodSetText.invoke(nodeClass.cast(node), value.getClass().cast(value.toString().toLowerCase()));
        } else if (nodeClass == DatePicker.class) {
            setDatePickerActions((DatePicker) node, editable, (LocalDate) value);
        } else {
            Method methodSetEditable = nodeClass.getMethod("setEditable", boolean.class);
            methodSetEditable.invoke(nodeClass.cast(node), editable);
            methodSetText.invoke(nodeClass.cast(node), value.getClass().cast(value));
        }
    }

    public void create(ActionEvent event) throws IOException, NoSuchMethodException,
            SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        AnchorPane tabRoot = (AnchorPane) ((Node) event.getSource()).getParent();

        try {
            switch (tabRoot.getId()) {
                case "coursePane":
                    HashMap<String, String> map = Course.getArgsHashMap(tabRoot);
                    DatabaseCourse.createCourse(map.get("title"), map.get("subject"), map.get("introText"),
                            DifficultyLevel.valueOf(((String) map.get("difficultyLevel")).toUpperCase()));
                    break;
                case "userPane":
                    break;
                case "modulePane":
                    break;
                case "webcastPane":
                    break;
            }
            switchPage(event);
        } catch (AlreadyExistsException e) {
            ((Label) tabRoot.lookup("#errorMessage")).setText(e.getMessage());
        } catch (CannotBeEmptyException e) {
            ((Label) tabRoot.lookup("#errorMessage")).setText(e.getMessage());
        }

    }

    public void delete(ActionEvent event) throws IOException {
        AnchorPane tabRoot = (AnchorPane) ((Node) event.getSource()).getParent();
        TableView table = (TableView) tabRoot.lookup("#table");

        switch (tabRoot.getId()) {
            case "coursePane":

                break;
            case "userPane":
                break;
            case "contentItem":
                break;
        }
    }

    static public void update(ActionEvent event, Object obj)
            throws IOException, NoSuchMethodException, SecurityException,
            IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        AnchorPane pane = (AnchorPane) ((Node) event.getSource()).getParent();

        try {
            switch (pane.getId()) {
                case "coursePopup":
                    HashMap<String, String> map = Course.getArgsHashMap(pane);
                    Course c = (Course) obj;
                    DatabaseCourse.updateCourse(c.getTitle(), map.get("title"), map.get("subject"),
                            map.get("introText"),
                            DifficultyLevel.valueOf(((String) map.get("difficultyLevel")).toUpperCase()));
                    break;
                case "userPopup":
                    break;
                case "modulePopup":
                    break;
                case "webcastPopup":
                    break;
            }
        } catch (AlreadyExistsException e) {
            // ((Label)tabRoot.lookup("#errorMessage")).setText(e.getMessage());
        }
    }
}
