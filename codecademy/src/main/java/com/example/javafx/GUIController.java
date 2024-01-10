package com.example.javafx;

import static com.example.course.Course.DifficultyLevel.valueOf;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Random;

import javax.swing.text.DateFormatter;

import com.example.course.ContactPerson;
import com.example.course.ContentItem;
import com.example.course.Course;
import com.example.course.Module;
import com.example.course.Speaker;
import com.example.course.Webcast;
import com.example.course.ContentItem.Status;
import com.example.course.Course.DifficultyLevel;
import com.example.database.DatabaseContactPerson;
import com.example.database.DatabaseContentItem;
import com.example.database.DatabaseCourse;
import com.example.database.DatabaseModule;
import com.example.database.DatabaseSpeaker;
import com.example.database.DatabaseUser;
import com.example.database.DatabaseWebcast;
import com.example.exeptions.AlreadyExistsException;
import com.example.exeptions.CannotBeEmptyException;
import com.example.user.User;
import com.example.user.User.Gender;

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
import javafx.scene.control.TabPane;
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
    private boolean skip = false;
    static private boolean update = false;

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
                    setMenuButtonActions((MenuButton) node, true);
                }
                if (node instanceof TableView) {
                    refreshTable((TableView)node, pane);
                }
            }
        } else {
            skip = false;
        }
    }

    static private void refreshTable(TableView table, AnchorPane pane) {
        clearTable(table);
        switch (pane.getId()) {
            case "course":
                Course.generateTable(table, update, null);
                break;
            case "user":
                User.generateTable(table, update, null);
                break;
            case "contentItem":
                ContentItem.generateContentItemTable(table, update, null);
                break;
            case "module":
                ContactPerson.generateTable(table, update, null);
                break;
            case "webcast":
                Speaker.generateTable(table, update, null);
                break;
        }
    }

    private String getFXMLPath(Object obj) {
        Button clickedButton = (Button) obj;
        switch (clickedButton.getId()) {
            case ("updateButton"):
                update = true;
                return "/com/example/javafx/fxml/Update.fxml";
            case ("createButton"):
                return "/com/example/javafx/fxml/Create.fxml";
            case ("deleteButton"):
                return "/com/example/javafx/fxml/Delete.fxml";
            case ("readButton"):
                return "/com/example/javafx/fxml/Read.fxml";
            case ("homeButton"):
                update = false;
                return "/com/example/javafx/fxml/Start.fxml";
            case ("newButton"):
                return "/com/example/javafx/fxml/Start.fxml";
        }

        return "";
    }

    public static void clearTable(TableView table) {
        table.getItems().clear();
        table.getColumns().clear();
    }

    public void search(ActionEvent event) throws IOException, NoSuchMethodException,
            SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        AnchorPane tabRootNode = (AnchorPane) ((Node) event.getSource()).getParent();
        TableView table = (TableView) tabRootNode.lookup("#table");
        clearTable(table);

        boolean editable = false;
        if (tabRootNode.getScene().getRoot().getId().equals("updateRoot")) {
            editable = true;
        }

        switch (tabRootNode.getId()) {
            case "course":
                Course.generateTable(table, editable, Course.getArgsHashMap(tabRootNode));
                break;
            case "contentItem":
                ContentItem.generateContentItemTable(table, editable, ContentItem.getArgsHashMap(tabRootNode));
                break;
            case "user":
                User.generateTable(table, editable, User.getArgsHashMap(tabRootNode));
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
                rootPane.getChildren().removeAll(popupPane, rect);
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
        if (nodeClass == MenuButton.class) {
            Method methodSetText = nodeClass.getMethod("setText", String.class);
            setMenuButtonActions((MenuButton) node, editable);
            methodSetText.invoke(nodeClass.cast(node), value.toString());
        } else if (nodeClass == DatePicker.class) {
            setDatePickerActions((DatePicker) node, editable, (LocalDate) value);
        } else {
            Method methodSetText = nodeClass.getMethod("setText", String.class);
            Method methodSetEditable = nodeClass.getMethod("setEditable", boolean.class);
            methodSetEditable.invoke(nodeClass.cast(node), editable);
            methodSetText.invoke(nodeClass.cast(node), value.toString());
        }
    }

    public void create(ActionEvent event) throws IOException, NoSuchMethodException,
            SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        AnchorPane tabRoot = (AnchorPane) ((Node) event.getSource()).getParent();

        try {
            switch (tabRoot.getId()) {
                case "course":
                    HashMap<String, String> map = Course.getArgsHashMap(tabRoot);
                    DatabaseCourse.createCourse(map.get("title"), map.get("subject"), map.get("introText"),
                            DifficultyLevel.valueOf(((String) map.get("difficultyLevel")).toUpperCase()));
                    break;
                case "user":
                    HashMap<String, String> userMap = User.getArgsHashMap(tabRoot);
                    DatabaseUser.createUser(userMap.get("email"), userMap.get("name"), ((DatePicker)tabRoot.lookup("#birthDate")).getValue(),
                        Gender.valueOf(String.valueOf(userMap.get("gender").charAt(0))), userMap.get("address"), userMap.get("postalCode"), 
                        userMap.get("residence"), userMap.get("country"));
                    break;
                case "module":
                    HashMap<String, String> moduleMap = Module.getArgsHashMap(tabRoot);
                    DatabaseModule.createModule(moduleMap.get("title"), ((DatePicker)tabRoot.lookup("#publicationDate")).getValue(), 
                        Status.valueOf(moduleMap.get("status").toUpperCase()), moduleMap.get("description"), 
                        Double.valueOf(moduleMap.get("version")), 0, 
                        ((ContactPerson)((TableView)tabRoot.lookup("#table")).getSelectionModel().getSelectedItem()).getEmail());
                    break;
                case "webcast":
                    HashMap<String, String> webMap = Webcast.getArgsHashMap(tabRoot);
                    DatabaseWebcast.createWebcast(webMap.get("title"), ((DatePicker)tabRoot.lookup("#publicationDate")).getValue(), 
                        Status.valueOf(webMap.get("status").toUpperCase()), webMap.get("description"), 
                        webMap.get("url"), ((Speaker)((TableView)tabRoot.lookup("#table")).getSelectionModel().getSelectedItem()).getId());
                    break;
                case "contactPerson":
                    HashMap<String, String> contactMap = ContactPerson.getArgsHashMap(tabRoot);
                    DatabaseContactPerson.createContactPerson(contactMap.get("email"), contactMap.get("name"));
                    break;
                case "speaker":
                    HashMap<String, String> speakerMap = Speaker.getArgsHashMap(tabRoot);
                    DatabaseSpeaker.createSpeaker(speakerMap.get("name"), speakerMap.get("organization"));
                    break;
            }
            
            ((Label)tabRoot.lookup("#errorMessage")).setText("Create was successful");

            for (Node node : tabRoot.getChildren()) {
                if (node instanceof TextField) {
                    ((TextField)node).setText("");
                } else if (node instanceof TextArea) {
                    ((TextArea)node).setText("");
                } else if (node instanceof DatePicker) {
                    ((DatePicker)node).setValue(null);
                } else if (node instanceof MenuButton) {
                    switch (tabRoot.getId()) {
                        case "user":
                            ((MenuButton)node).setText("gender");
                            break;
                        case "course":
                            ((MenuButton)node).setText("Difficulty level");
                            break;
                        default:
                            ((MenuButton)node).setText("Status");
                            break;      
                    }
                }
            }
        } catch (AlreadyExistsException e) {
            ((Label)tabRoot.lookup("#errorMessage")).setText(e.getMessage());
        } catch (IllegalArgumentException e) {
            if (e.getMessage().contains("No enum constant")) {
                String[] arr = e.getMessage().split("\\.");
                ((Label)tabRoot.lookup("#errorMessage")).setText("Please select a " + arr[arr.length - 2]);
            } else {
                ((Label)tabRoot.lookup("#errorMessage")).setText(e.getMessage());
            }
        } catch (CannotBeEmptyException e) {
            ((Label)tabRoot.lookup("#errorMessage")).setText(e.getMessage());
        }
    }

    public void delete(ActionEvent event) throws IOException {
        AnchorPane tabRoot = (AnchorPane) ((Node) event.getSource()).getParent();
        TableView table = (TableView) tabRoot.lookup("#table");
        Object obj = table.getSelectionModel().getSelectedItem();

        switch (tabRoot.getId()) {
            case "course":
                DatabaseCourse.deleteCourse(((Course)obj).getTitle());
                break;
            case "user":
                DatabaseUser.deleteUser(((User)obj).getEmail());
                break;
            case "contentItem":
                DatabaseContentItem.deleteContentItem(((ContentItem)obj).getContentItemId());
                break;
        }

        refreshTable(table, tabRoot);
    }

    static public void update(ActionEvent event, Object obj)
            throws IOException, NoSuchMethodException, SecurityException,
            IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        AnchorPane pane = (AnchorPane) ((Node) event.getSource()).getParent();

        try {
            switch (pane.getId()) {
                case "coursePopup":
                    HashMap<String, String> courseMap = Course.getArgsHashMap(pane);
                    Course c = (Course) obj;
                    DatabaseCourse.updateCourse(c.getTitle(), courseMap.get("title"), courseMap.get("subject"),
                            courseMap.get("introText"),
                            DifficultyLevel.valueOf(((String) courseMap.get("difficultyLevel")).toUpperCase()));
                    break;
                case "userPopup":
                    HashMap<String, String> userMap = User.getArgsHashMap(pane);
                    User u = (User) obj;
                    DatabaseUser.updateUser(u.getEmail(), userMap.get("email"), userMap.get("name"), ((DatePicker)pane.lookup("#birthDate")).getValue(),
                        Gender.valueOf(String.valueOf(userMap.get("gender").charAt(0))), userMap.get("address"), userMap.get("postalCode"), 
                        userMap.get("residence"), userMap.get("country"));
                    break;
                case "modulePopup":
                    HashMap<String, String> moduleMap = Module.getArgsHashMap(pane);
                    Module m = (Module) obj;
                    DatabaseModule.updateModule(m.getId(), moduleMap.get("title"), 
                        ((DatePicker)pane.lookup("#publicationDate")).getValue(), 
                        Status.valueOf(moduleMap.get("status").toUpperCase()), moduleMap.get("description"), 
                        Double.valueOf(moduleMap.get("version")), m.getOrderNumber(), 
                        m.getContactPerson().getEmail(), DatabaseModule.readModuleCourseTitle(m.getContentItemId()));
                    break;
                case "webcastPopup":
                    HashMap<String, String> webMap = Webcast.getArgsHashMap(pane);
                    Webcast w = (Webcast) obj;
                    DatabaseWebcast.updateWebcast(w.getId(), webMap.get("title"), 
                        ((DatePicker)pane.lookup("#publicationDate")).getValue(), 
                        Status.valueOf(webMap.get("status").toUpperCase()), webMap.get("description"), 
                        webMap.get("url"), w.getSpeaker().getId());
                    break;
            }

            ((Label)pane.lookup("#errorMessage")).setText("Update was successful");

            AnchorPane rootPane = (AnchorPane)pane.getScene().getRoot();
            
            for (Node node : rootPane.getChildren()) {
                if (node instanceof TabPane) {
                    Tab tab = ((TabPane)node).getSelectionModel().getSelectedItem();
                    AnchorPane tabPane = (AnchorPane)tab.getContent();
                    refreshTable((TableView)tabPane.lookup("#table"), tabPane);
                }
            }

        } catch (AlreadyExistsException e) {
            ((Label)pane.lookup("#errorMessage")).setText(e.getMessage());
        } catch (IllegalArgumentException e) {
            ((Label)pane.lookup("#errorMessage")).setText(e.getMessage());
        }
    }
}
