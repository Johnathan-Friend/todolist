package com.example.todolist;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.*;
import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;

public class todoController implements Initializable {

    private Stage stage;
    private Scene scene;
    private Parent root;

    @FXML
    private Button completeTaskButton;
    @FXML
    private ListView<Task> completedTaskList;
    @FXML
    private DatePicker dateToCompletePrompt;
    @FXML
    private Button deleteListButton;
    @FXML
    private Button deleteTaskButton;
    @FXML
    private ListView<List> listList;
    @FXML
    private Label listNameLabel;
    @FXML
    private TextField listNamePrompt;
    @FXML
    private Button menuButton;
    @FXML
    private Button newListButton;
    @FXML
    private Button newTaskButton;
    @FXML
    private ListView<Task> taskList;
    @FXML
    private TextField taskPrompt;
    @FXML
    private Label listErrorLabel;
    @FXML
    private Label taskErrorLabel;
    private ToDoList toDoList;

    @FXML
    void completeTask(ActionEvent event) {

        try {
            //takes the selected task object removes it from Tasks array list and adds it to the completed task array list
            int listIndex = listList.getSelectionModel().getSelectedIndex();
            int taskIndex = taskList.getSelectionModel().getSelectedIndex();
            toDoList.getListArrayList().get(listIndex).addCompletedTask(toDoList.getListArrayList().get(listIndex).getTaskArrayList().get(taskIndex));
            completedTaskList.getItems().add(toDoList.getListArrayList().get(listIndex).getTaskArrayList().get(toDoList.getListArrayList().get(taskIndex).getTaskArrayList().size() - 1));
            taskList.getItems().remove(taskIndex);
            toDoList.getListArrayList().get(listIndex).getTaskArrayList().remove(taskIndex);
            taskErrorLabel.setText("");
        } catch (Exception e) {
            e.printStackTrace();
            taskErrorLabel.setText("Error Occurred, make sure you have task selected");
        }
    }

    @FXML
    void createNewList(ActionEvent event) {
        try {

            if (listNamePrompt.getText().isBlank()) {
                listErrorLabel.setText("Must give a name to your list");
            } else {
                //creates new list object
                toDoList.addList(listNamePrompt.getText());
                listList.getItems().add(toDoList.getListArrayList().get(toDoList.getListArrayList().size() - 1));
                listNamePrompt.clear();
                listErrorLabel.setText("");
            }
        } catch (Exception e) {
            e.printStackTrace();
            listErrorLabel.setText("Error Occurred, make sure to name your list");
        }
    }

    @FXML
    void createNewTask(ActionEvent event) {

        try {
            //creates new task object
            int index = listList.getSelectionModel().getSelectedIndex();
            toDoList.getListArrayList().get(index).addTask(taskPrompt.getText(), String.valueOf(dateToCompletePrompt.getValue()));
            taskList.getItems().add(toDoList.getListArrayList().get(index).getTaskArrayList().get(toDoList.getListArrayList().get(index).getTaskArrayList().size() - 1));

            //clears the prompts
            taskErrorLabel.setText("");
            taskPrompt.clear();
            dateToCompletePrompt.getEditor().clear();

        }catch (Exception e) {
            e.printStackTrace();
            taskErrorLabel.setText("Error Occurred, make sure you select a list to add to");
        }

    }

    @FXML
    void deleteList(ActionEvent event) {
        try {
            //deletes the selected List object
            int index = listList.getSelectionModel().getSelectedIndex();
            listList.getItems().remove(index);
            toDoList.getListArrayList().remove(index);
            listErrorLabel.setText("");
        } catch (Exception e) {
            e.printStackTrace();
            listErrorLabel.setText("Error Occurred, make sure you have list selected");
        }
    }

    @FXML
    void deleteTask(ActionEvent event) {

        try {
            //deletes the selected task object
            int listIndex = listList.getSelectionModel().getSelectedIndex();
            int taskIndex = taskList.getSelectionModel().getSelectedIndex();
            taskList.getItems().remove(taskIndex);
            toDoList.getListArrayList().get(listIndex).getTaskArrayList().remove(taskIndex);
        } catch (Exception e) {
            e.printStackTrace();
            taskErrorLabel.setText("Error Occurred, make sure you have task selected");
        }
    }

    @FXML
    void toMenu(ActionEvent event) throws IOException {

        try {

            //gets file name
            File myObj = new File(toDoList.getName() + ".txt");

            //if file exist clear it before writing to file
            if (myObj.createNewFile()) {
                writeContentsToFile(myObj);
            } else {
                deleteContentsFromFile(myObj);
                writeContentsToFile(myObj);
            }

            //returns to the menu
            Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("menu.fxml")));
            stage = (Stage)((Node)event.getSource()).getScene().getWindow();
            scene = new Scene(root);
            stage.setScene(scene);
            stage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    /**
     * @param username: name of file
     * @param password: password for file
     * sets the new stage name and creates todolist object
     */
    public void setNewStage(String username, String password) {
        listNameLabel.setText(username);
        this.toDoList = new ToDoList(username, password);
    }

    /**
     * @param username: name of the file
     * @param password: password for file
     * reads in the lines from the existing file and creates todolist objects
     */
    public void setExistingStage(String username, String password) {
        listNameLabel.setText(username);
        this.toDoList = new ToDoList(username, password);

        //reads in file contents to update todolist
        try {
            BufferedReader bufferedReader = new BufferedReader(new FileReader(toDoList.getName() + ".txt"));
            String line = bufferedReader.readLine();
            int counter = 0;

            //iterates through entire file until line is null
            while (true) {

                line = bufferedReader.readLine();
                if (line == null) {
                    break;
                }

                //makes List objects
                toDoList.getListArrayList().add(new List(line));
                listList.getItems().add(toDoList.getListArrayList().get(toDoList.getListArrayList().size() - 1));
                line = bufferedReader.readLine();

                if (line == null) {
                    break;
                } else if (Objects.equals(line, "[]")) {
                    counter++;
                    continue;
                }

                //takes the array of task objects apart and makes Task objects
                StringBuilder builder = new StringBuilder(line);
                builder.deleteCharAt(0);
                builder.deleteCharAt(builder.length() - 1);
                line = builder.toString();
                String[] splitLine = line.split(",");

                for (String x: splitLine) {
                    String[] splitLineInner = x.split("//::");
                    for (String y: splitLineInner) {
                        toDoList.getListArrayList().get(counter).getTaskArrayList().add(new Task(splitLineInner[0], splitLineInner[1]));
                    }
                }
                counter++;
            }
            bufferedReader.close();
        } catch(Exception exception) {
            exception.printStackTrace();
        }
    }

    /**
     * @param file: the file to be written to
     * @throws IOException
     * writes the object content to the file
     */
    public void writeContentsToFile(File file) throws IOException {

        //makes new file and writes todolist object content to it
        FileWriter myWriter = new FileWriter(file);
        myWriter.write(toDoList.getPassword() + "\n");
        for (List x: toDoList.getListArrayList()) {
            myWriter.write(x.getName() + "\n[");
            for (Task y: x.getTaskArrayList())
                myWriter.write(y.getName() + "//::" + y.getDate() + ",");
            myWriter.write("]\n");
        }
        myWriter.close();
    }

    /**
     * @param file: the file to have content deleted
     * @throws IOException
     * deletes all the content from the file
     */
    public void deleteContentsFromFile(File file) throws IOException {
        FileWriter myWriter = new FileWriter(file);
        PrintWriter printWriter = new PrintWriter(myWriter, false);
        printWriter.flush();
        printWriter.close();
        myWriter.close();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        //adds change listener to the list of List objects
        listList.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<List>() {

            @Override
            public void changed(ObservableValue<? extends List> observableValue, List list, List t1) {

                //if change in selected List then updates the taskList to show the task objects in that selected list
                int index = listList.getSelectionModel().getSelectedIndex();
                taskList.getItems().clear();
                taskList.getItems().addAll(toDoList.getListArrayList().get(index).getTaskArrayList());
                completedTaskList.getItems().clear();
                completedTaskList.getItems().addAll(toDoList.getListArrayList().get(index).getCompletedTaskArrayList());
            }
        });
    }
}
