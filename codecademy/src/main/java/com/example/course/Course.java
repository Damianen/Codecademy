package com.example.course;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Random;

import com.example.ValidateFunctions.ValidateFunctions;
import com.example.database.DatabaseCourse;
import com.example.database.DatabaseModule;
import com.example.database.DatabaseOverview;
import com.example.database.DatabaseProgress;
import com.example.database.DatabaseUser;
import com.example.javafx.GUIController;
import com.example.user.Enrollment;
import com.example.user.Progress;
import com.example.user.User;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.MenuButton;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;

public class Course {

    public enum DifficultyLevel {
        BEGINNER,
        ADVANCED,
        EXPERT
    }

    private String title;
    private String subject;
    private String introText;
    private DifficultyLevel difficultyLevel;
    private ObservableList<ContentItem> modules;

    public Course(String title, String subject, String introText, DifficultyLevel difficulty) {
        this.title = title;
        this.subject = subject;
        this.introText = introText;
        this.difficultyLevel = difficulty;
        this.modules = DatabaseModule.getCourseModules(title);
    }

    public String getTitle() {
        return title;
    }

    public String getSubject() {
        return subject;
    }

    public String getDifficultyLevelString() {
        return difficultyLevel.toString().toLowerCase();
    }

    public String getIntroText() {
        return introText;
    }

    public ObservableList<ContentItem> getModules() {
        return modules;
    }

    public void setModules(ObservableList<ContentItem> modules) {
        this.modules = modules;
    }

    public boolean addModule(Module module) {

        // get next order number
        int orderNumber = DatabaseModule.getCourseModules(this.title).size() + 1;

        // add the module to the course
        if (DatabaseModule.addModuleToCourse(module.getId(), orderNumber, this.title) == false) {
            return false;
        }

        // get all users enrolled in this course
        ObservableList<User> enrolledUsers = DatabaseUser.getEnrolledUsersForCourse(this);

        // automaticly create progresses for all users with the new module
        for (User user : enrolledUsers) {

            Random rand = new Random();
            int randomNumber = rand.nextInt(101);

            if (ValidateFunctions.isValidPercentage(randomNumber) != true) {
                throw new IllegalArgumentException(randomNumber + " is not a valid percentage");
            }

            try {
                DatabaseProgress.createProgress(randomNumber, user.getEmail(), module.getContentItemId());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        // add to this.modules
        modules.add(module);

        return true;
    }

    public boolean removeModule(Module module) {

        // update the remaining order numbers
        if (DatabaseModule.updateOrderNumbersRemoveFromCourse(module.getId(), module.getOrderNumber(),
                this.title) == false) {
            return false;
        }

        // get all user enrolled in this course
        ObservableList<User> enrolledUsers = DatabaseUser.getEnrolledUsersForCourse(this);

        // automaticly delete all progresses for that module
        for (User user : enrolledUsers) {

            Progress progress = DatabaseProgress.getProgressWithUserAndContentItem(user.getEmail(),
                    module.getContentItemId());
            DatabaseProgress.deleteProgress(progress.getId());
        }

        // remove module from this.modules
        modules.remove(module);

        return true;
    }

    public boolean updateModuleOrderNumber(Module module, int newOrderNumber) {

        try {
            // update orderNumber
            DatabaseModule.updateOrderNumbersInCourse(module.getId(), newOrderNumber, this.getTitle());

            // reset module arangement
            this.modules = DatabaseModule.getCourseModules(this.title);

            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }

    }

    public void generateRecommendedCourses(TableView<Course> table) {
        // Generate the table so it has the columns
        Course.generateTable(table, false, null);

        // Clear the table so it still has the columns but not the data
        table.getItems().clear();

        // Add the correct data to the table
        table.setItems(DatabaseOverview.getRecommendedCourses(title));
    }

    // Get all of the all attributes from elements in a specific pane and return a
    // hashmap with the values.
    static public HashMap<String, String> getArgsHashMap(AnchorPane pane) throws NoSuchMethodException,
            SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {

        HashMap<String, String> searchArgs = new HashMap<String, String>();

        searchArgs.put("title", GUIController.searchForNodeText("title", TextField.class, pane));
        searchArgs.put("introText", GUIController.searchForNodeText("introText", TextArea.class, pane));
        searchArgs.put("subject", GUIController.searchForNodeText("subject", TextField.class, pane));

        String difficulty = GUIController.searchForNodeText("difficultyLevel", MenuButton.class, pane);
        if (!difficulty.equals("Difficulty Level")) {
            searchArgs.put("difficultyLevel", difficulty);
        }

        return searchArgs;
    }

    // Generate table function
    static public void generateTable(TableView<Course> table, boolean editable, HashMap<String, String> searchArgs) {
        // Make table columns and add them to the table
        TableColumn<Course, String> title = new TableColumn<Course, String>("Title");
        TableColumn<Course, String> subject = new TableColumn<Course, String>("Subject");
        TableColumn<Course, String> difficultyLevel = new TableColumn<Course, String>("Difficulty level");

        final ObservableList<TableColumn<Course, ?>> columns = FXCollections.observableArrayList();
        columns.add(title);
        columns.add(subject);
        columns.add(difficultyLevel);
        table.getColumns().addAll(columns);

        // Set the a value factory so the table can get the data from the instance of
        // the class
        title.setCellValueFactory(new PropertyValueFactory<Course, String>("title"));
        subject.setCellValueFactory(new PropertyValueFactory<Course, String>("subject"));
        difficultyLevel.setCellValueFactory(new PropertyValueFactory<Course, String>("difficultyLevelString"));

        // Add a event handler to the table so that when we click it it will show us the
        // popup window
        table.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                Course course = (Course) table.getSelectionModel().getSelectedItem();
                if (course == null) {
                    return;
                }
                try {
                    course.generatePopupWindow(event, editable);
                } catch (NoSuchMethodException | SecurityException | IllegalAccessException | IllegalArgumentException
                        | InvocationTargetException e) {
                    e.printStackTrace();
                }
            }
        });

        // Add the data to the table
        if (searchArgs == null) {
            table.setItems(DatabaseCourse.getCourseList());
        } else if (searchArgs.containsKey("userEmail")) {
            table.setItems(DatabaseCourse.getNotEnrolledCourseForUser(searchArgs.get("userEmail")));
        } else if (searchArgs.containsKey("enrolmentTitle")) {
            ObservableList<Course> list = FXCollections.observableArrayList();
            list.add(DatabaseCourse.readCourse(searchArgs.get("enrolmentTitle")));
            table.setItems(list);
        } else {
            table.setItems(DatabaseCourse.readCourseSearchAll(searchArgs));
        }

    }

    // Function to generate the popup window
    private void generatePopupWindow(MouseEvent event, boolean editable) throws NoSuchMethodException,
            SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        if (event.getClickCount() > 1) {
            AnchorPane pane;

            // Try to get the popup window and load it into pane
            try {
                pane = FXMLLoader.load(getClass().getResource("/com/example/javafx/fxml/course.fxml"));
            } catch (IOException e) {
                e.printStackTrace();
                return;
            }

            // Setup the popup window and the update buttons
            Scene scene = ((Node) event.getSource()).getScene();
            GUIController.setupPopupWindow(pane, (AnchorPane) scene.getRoot());
            GUIController.setupUpdateButton(editable, pane, this);

            // Setup the nodes in the popup window so they contain the values of the
            // instance we opened the window of
            GUIController.setUpNode(TextField.class, editable, title, pane, "title");
            GUIController.setUpNode(TextField.class, editable, subject, pane, "subject");
            GUIController.setUpNode(TextArea.class, editable, introText, pane, "introText");
            GUIController.setUpNode(MenuButton.class, editable, difficultyLevel, pane, "difficultyLevel");

            setupTabs(pane, editable);
        }
    }

    // Setup table tabs
    public void setupTabs(AnchorPane pane, boolean editable) {
        ObservableList<Tab> tabs = ((TabPane) pane.lookup("#tables")).getTabs();

        for (Tab tab : tabs) {
            // Get the pane of the tab and the table
            AnchorPane rootTabPane = (AnchorPane) tab.getContent();
            TableView table = (TableView) rootTabPane.lookup("#table");

            if (tab.getId().equals("module")) {

                if (!editable) {
                    table.setPrefHeight(320);
                }

                // Generate module table with all modules from course
                HashMap<String, String> map = new HashMap<String, String>();
                map.put("courseTitle", title);
                Module.generateTable(table, editable, map);

                // Set an event handler for the button to call setAddModule
                Button btn = (Button) rootTabPane.lookup("#add");
                btn.setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent event) {
                        setAddModule(table, btn, editable, pane);
                    }
                });

                Button removeBtn = (Button) rootTabPane.lookup("#remove");
                removeBtn.setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent event) {
                        setRemoveModule(table, btn, editable, pane);
                    }
                });
            } else {

                // Generate enrollments table for the course
                HashMap<String, String> map = new HashMap<String, String>();
                map.put("courseTitle", title);
                Enrollment.generateTable(table, editable, map);
            }
        }
    }

    // Setup the add module button when clicked
    public void setAddModule(TableView table, Button btn, boolean editable, AnchorPane pane) {
        // Edit add button and generate the table with modules that don't have a course
        btn.setText("Add selected module");
        GUIController.clearTable(table);
        HashMap<String, String> map = new HashMap<String, String>();
        map.put("courseTitleNew", title);
        Module.generateTable(table, editable, map);

        // Add event listener to the button
        btn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                // Add selected module to course
                Module module = (Module) table.getSelectionModel().getSelectedItem();
                if (module == null) {
                    return;
                }
                addModule(module);
                btn.setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent event) {
                        setAddModule(table, btn, editable, pane);
                    }
                });

                // Set the table back to original state
                GUIController.clearTable(table);
                HashMap<String, String> map = new HashMap<String, String>();
                map.put("courseTitle", title);
                Module.generateTable(table, editable, map);
                btn.setText("Add module");

                // Tell user the module was successfully added
                ((Label) pane.lookup("#errorMessage")).setText("Module successfully added!");
            }
        });
    }

    // Setup the remove module button when clicked
    public void setRemoveModule(TableView table, Button btn, boolean editable, AnchorPane pane) {
        // Remove selected module to course
        Module module = (Module) table.getSelectionModel().getSelectedItem();
        if (module == null) {
            return;
        }
        removeModule(module);

        // Set the table back to original state
        GUIController.clearTable(table);
        HashMap<String, String> map = new HashMap<String, String>();
        map.put("courseTitle", title);
        Module.generateTable(table, editable, map);
        btn.setText("Remove module");

        // Tell user the module was successfully removed
        ((Label) pane.lookup("#errorMessage")).setText("Module successfully deleted!");
    }
}
