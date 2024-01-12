package com.example.javafx;


import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.time.LocalDate;
import java.util.HashMap;

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
import com.example.overview.Overview;
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
import javafx.stage.Stage;

public class GUIController {
    private Stage stage;
    private Scene scene;
    private Parent root;
    private boolean skip = false;
    static private boolean update = false;
    static private boolean overview = false;

    // Switch page function
    public void switchPage(ActionEvent event) throws IOException {
        // Try to get the fxml path and load it into root
        try {
            root = FXMLLoader.load(getClass().getResource(getFXMLPath(event.getSource())));
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }

        // Load the root into the scene
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    // Switch tab function
    public void switchTab(Event event) throws IOException {
        // Switch tab is called twice when a tab is closed and opened so i skip it so it only
        // fires when we open a tab so we have skip variable
        if (!skip) {
            skip = true;

            // Get the root Anchor pane of the tab
            AnchorPane pane = (AnchorPane) ((Tab) event.getSource()).getContent();
            
            // For each menu button, normal button (in overview) and table we set it up
            for (Node node : pane.getChildren()) {
                
                if (node instanceof MenuButton) {
                    setMenuButtonActions((MenuButton) node, true);
                }

                if (node instanceof TableView) {
                    refreshTable((TableView)node, pane);
                }

                if (node instanceof Button && overview) {
                    Overview.setupOverviewButton((Button)node, pane);
                }
            }
        } else {
            skip = false;
        }
    }

    // Refresh the table
    static private void refreshTable(TableView table, AnchorPane pane) {
        
        clearTable(table);
        
        // based on the id of the pane we generate a table without any search arguments
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

            case "threeWebcast": 
                Overview.generateTop3Webcasts(table);
                break;

            case "ThreeCourse":
                Overview.generateTop3Course(table);
                break;

            default:
                if (table.getId().equals("courseTable")) {
                    Course.generateTable(table, false, null);
                } else if (table.getId().equals("userTable")) {
                    User.generateTable(table, false, null);
                }
                break;
        }
    }

    // get the fxml path based on the objects id we give it
    private String getFXMLPath(Object obj) {

        Button clickedButton = (Button) obj;

        switch (clickedButton.getId()) {
            
            case ("overviewsButton"):
                overview = true;
                return "/com/example/javafx/fxml/Overviews.fxml";
            
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
                overview = false;
                return "/com/example/javafx/fxml/Start.fxml";
            
            case ("newButton"):
                return "/com/example/javafx/fxml/Start.fxml";
        }

        return "";
    }

    // Function to clear a given table
    public static void clearTable(TableView table) {
        table.getItems().clear();
        table.getColumns().clear();
    }

    // search function
    public void search(ActionEvent event) throws IOException, NoSuchMethodException,
            SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        // get the root node of the tab and the table in the root
        AnchorPane tabRootNode = (AnchorPane) ((Node) event.getSource()).getParent();
        TableView table = (TableView) tabRootNode.lookup("#table");
        clearTable(table);

        // Generate the table based on the id of the root and on the search arguments we give it
        switch (tabRootNode.getId()) {
            case "course":
                Course.generateTable(table, update, Course.getArgsHashMap(tabRootNode));
                break;
            case "contentItem":
                ContentItem.generateContentItemTable(table, update, ContentItem.getArgsHashMap(tabRootNode));
                break;
            case "user":
                User.generateTable(table, update, User.getArgsHashMap(tabRootNode));
                break;
        }
    }

    // method to find the text in a given node
    public static String searchForNodeText(String id, Class<?> nodeClass, AnchorPane pane) throws NoSuchMethodException,
            SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        
        Node node = pane.lookup("#" + id);
        Method method = nodeClass.getMethod("getText");
        return (String) method.invoke(nodeClass.cast(node));
    }

    // method for closing a popup window
    public static void closePopupWindow(AnchorPane popupPane, Rectangle rect) {
        AnchorPane root = (AnchorPane) popupPane.getScene().getRoot();
        root.getChildren().removeAll(popupPane, rect);
    }

    // Setup method for menu buttons since we don't use their base functionality
    public static void setMenuButtonActions(MenuButton menuButton, boolean editable) {
        // for each menu button item we set the on action to null if it is not editable
        for (MenuItem item : menuButton.getItems()) {
            if (!editable) {
                item.setOnAction(null);
            } else {
                // if it is editable we setup the menu button so when you click on the item
                // the text of the menu button changes to the given value
                item.setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent event) {
                        menuButton.setText(item.getText());
                    }
                });
            }
        }
    }

    // Setup for a date picker node
    public static void setDatePickerActions(DatePicker date, boolean editable, LocalDate value) {
        // first we set the edit ability
        date.setEditable(editable);

        // Then we check if its editable if its not we set it so that when it is edited 
        // (Which it can since setEditable only makes it so you can not put in values directly in the field
        // you can still put in the value with the date picker to the right of the field)
        // we set the value back to the original value
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

    // Method for setting up a popup window
    public static void setupPopupWindow(AnchorPane popupPane, AnchorPane rootPane) {
        // First we create a rectangle and add it ass a background so you cannot click anything 
        // behind the popup window and then we add it and the popup window to the root of the scene
        Rectangle rect = new Rectangle(960, 540, Paint.valueOf("#0000008f"));
        rootPane.getChildren().addAll(rect, popupPane);
        popupPane.setLayoutX(180);
        popupPane.setLayoutY(70);

        // We also setup the toolbar in the popup window so the close button works
        ToolBar toolbar = (ToolBar) popupPane.lookup("#toolBar");
        ((Button) toolbar.getItems().get(0)).setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                // when the close button is pressed we remove the rectangle and the popup window
                rootPane.getChildren().removeAll(popupPane, rect);
            }
        });
    }

    // Setup method for update button
    public static void setupUpdateButton(boolean editable, AnchorPane popupPane, Object obj) {
        // Lookup the update button and set the visibility
        Button updateButton = (Button) popupPane.lookup("#updateButton");
        updateButton.setVisible(editable);

        // Add event handler to it so when its pressed we update the database
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

    // Setup function for nodes in popup window
    public static void setUpNode(Class<?> nodeClass, Boolean editable, Object value, AnchorPane pane, String id)
            throws NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException,
            InvocationTargetException {
        // First we lookup the node in the given pane
        Node node = pane.lookup("#" + id);

        if (nodeClass == MenuButton.class) {
            // if its a menu button we get the method set text and invoke it and then setup the menu button
            Method methodSetText = nodeClass.getMethod("setText", String.class);
            methodSetText.invoke(nodeClass.cast(node), value.toString());
            setMenuButtonActions((MenuButton) node, editable);

        } else if (nodeClass == DatePicker.class) {
            // if its a datepicker we this function
            setDatePickerActions((DatePicker) node, editable, (LocalDate) value);

        } else {
            // if it is either a text field or area we first get the setText and setEditable methods
            Method methodSetText = nodeClass.getMethod("setText", String.class);
            Method methodSetEditable = nodeClass.getMethod("setEditable", boolean.class);

            // then we invoke both of those methods
            methodSetEditable.invoke(nodeClass.cast(node), editable);
            methodSetText.invoke(nodeClass.cast(node), value.toString());
        }
    }

    // create method
    public void create(ActionEvent event) throws IOException, NoSuchMethodException,
            SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        
        AnchorPane tabRoot = (AnchorPane) ((Node) event.getSource()).getParent();
        
        // Create a new entry in the database based on the tab root id
        // where we first get the arguments and then put it in the create function
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
            
            // if the create was successful we let the user know
            ((Label)tabRoot.lookup("#errorMessage")).setText("Create was successful");

            // we reset all of the input field
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
            // if the primary key already exists we show the user
            ((Label)tabRoot.lookup("#errorMessage")).setText(e.getMessage());
        } catch (IllegalArgumentException e) {
            // if there is any illegal arguments we tell the user
            if (e.getMessage().contains("No enum constant")) {
                String[] arr = e.getMessage().split("\\.");
                ((Label)tabRoot.lookup("#errorMessage")).setText("Please select a " + arr[arr.length - 2]);
            } else {
                ((Label)tabRoot.lookup("#errorMessage")).setText(e.getMessage());
            }
        } catch (CannotBeEmptyException e) {
            // if any field is empty we tell the user 
            ((Label)tabRoot.lookup("#errorMessage")).setText(e.getMessage());
        }
    }

    // delete method
    public void delete(ActionEvent event) throws IOException {
        // Get the tab root and the table and get the selected object int the table
        AnchorPane tabRoot = (AnchorPane) ((Node) event.getSource()).getParent();
        TableView table = (TableView) tabRoot.lookup("#table");
        Object obj = table.getSelectionModel().getSelectedItem();

        if (obj == null) {
            return;
        }

        // based on the tab root id we delete the selected obj in the table
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

    // update method
    static public void update(ActionEvent event, Object obj)
            throws IOException, NoSuchMethodException, SecurityException,
            IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        
        AnchorPane pane = (AnchorPane) ((Node) event.getSource()).getParent();

        // update the values of a instance based on the arguments and the popup window pane id
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

            // tell the user if it was a success
            ((Label)pane.lookup("#errorMessage")).setText("Update was successful");

            // get the root pane of the scene
            AnchorPane rootPane = (AnchorPane)pane.getScene().getRoot();
            
            // refresh the table in the selected root pane
            for (Node node : rootPane.getChildren()) {
                if (node instanceof TabPane) {
                    Tab tab = ((TabPane)node).getSelectionModel().getSelectedItem();
                    AnchorPane tabPane = (AnchorPane)tab.getContent();
                    refreshTable((TableView)tabPane.lookup("#table"), tabPane);
                }
            }

        } catch (AlreadyExistsException e) {
            // if the primary key already exists we show the user
            ((Label)pane.lookup("#errorMessage")).setText(e.getMessage());
        } catch (IllegalArgumentException e) {
            // if there is any illegal arguments we tell the user
            ((Label)pane.lookup("#errorMessage")).setText(e.getMessage());
        }
    }
}
