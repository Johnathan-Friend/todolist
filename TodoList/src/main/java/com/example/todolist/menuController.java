package com.example.todolist;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class menuController{

    private Stage stage;
    private Scene scene;
    private Parent root;

    @FXML
    private Button loginButton;
    @FXML
    private Label loginErrorLabel;
    @FXML
    private Button makeNewTodoButton;
    @FXML
    private PasswordField passwordText;
    @FXML
    private TextField usernameText;

    @FXML
    void goToTodo(ActionEvent event) throws IOException {

        try {

            //checks if file exists
            Path path = Paths.get(usernameText.getText() + ".txt");
            if (Files.exists(path)) {

                //opens the file and reads the first line
                FileReader fileReader = new FileReader(path.toFile());
                BufferedReader buffer = new BufferedReader(fileReader);
                String line = buffer.readLine();
                fileReader.close();
                buffer.close();

                //checks the password
                if (line.equals(passwordText.getText())) {

                    //adds the file name to the ListNameLabel in todoController and loads next scene
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("todo.fxml"));
                    Parent root = (Parent) loader.load();
                    todoController Controller = loader.getController();
                    Controller.setExistingStage(usernameText.getText(), passwordText.getText());
                    stage = (Stage)((Node)event.getSource()).getScene().getWindow();
                    scene = new Scene(root);
                    stage.setScene(scene);
                    stage.show();

                } else {
                    loginErrorLabel.setText("The Password is not correct");
                }

            } else {
                loginErrorLabel.setText("That Username does not exists");
            }

        } catch (IOException exception){
            exception.printStackTrace();
            loginErrorLabel.setText("Something went wrong opening ToDoList");
        }

    }

    @FXML
    void makeNewTodo(ActionEvent event) throws IOException{

        try {

            //makes new file and checks if file already exists (if exists throws error)
            File myObj = new File(usernameText.getText() + ".txt");
            if (myObj.createNewFile()) {
                if (passwordText.getText().isBlank()) {
                    loginErrorLabel.setText("Please Enter Password");
                } else {
                    //adds the file name to the ListNameLabel in todoController and loads next scene
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("todo.fxml"));
                    Parent root = (Parent) loader.load();
                    todoController Controller = loader.getController();
                    Controller.setNewStage(usernameText.getText(), passwordText.getText());
                    stage = (Stage)((Node)event.getSource()).getScene().getWindow();
                    scene = new Scene(root);
                    stage.setScene(scene);
                    stage.show();
                }
            } else {
                loginErrorLabel.setText("This file name already exists please try again");
            }
        } catch(IOException exception) {
            exception.printStackTrace();
            loginErrorLabel.setText("Error making new project");
        }

    }
}



