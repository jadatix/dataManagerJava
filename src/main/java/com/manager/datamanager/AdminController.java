package com.manager.datamanager;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

public class AdminController {
    private Stage stage;
    private Scene scene;
    private Parent root;

    @FXML
    TextField nameTextField;
    @FXML
    TextField lastnameTextField;
    @FXML
    TextField emailTextField;
    @FXML
    PasswordField passwordPField;
    @FXML
    TextField genderTextField;
    @FXML
    TextField ageTextField;
    @FXML
    TextField headOfTextField;
    @FXML
    TextArea subjectTextArea;

    public void adminChangeTeacher(ActionEvent event) throws IOException {
        root = FXMLLoader.load(getClass().getResource("change-teacher.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
    public void adminChangeStudent(ActionEvent event) throws IOException {
//        root = FXMLLoader.load(getClass().getResource("change-teacher.fxml"));
//        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
//        scene = new Scene(root);
//        stage.setScene(scene);
//        stage.show();
        //TODO: create scene and change first line of function
        System.out.println("Студентська сцена");
    }

    public void adminChangeTeacherBack(ActionEvent event) throws IOException {
        root = FXMLLoader.load(getClass().getResource("admin.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public void exit(ActionEvent event){
        System.out.println("Вихід");
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        stage.close();
    }

    public void addNewTeacher(ActionEvent event){
        System.out.println("Додано нового викладача");
        //// TODO: complete the function
    }



}
