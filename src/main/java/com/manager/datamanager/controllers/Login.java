package com.manager.datamanager.controllers;

import com.manager.datamanager.EncryptionMD5;
import com.manager.datamanager.Main;
import com.manager.datamanager.SQLConnection;
import entities.Student;
import entities.Teacher;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.List;

public class Login {
    @FXML
    private Label statusLabel;
    @FXML
    private PasswordField passwordPField;
    @FXML
    private TextField emailTextField;



    private Stage stage;
    private Scene scene;
    private Parent root;

    public void handleLogin(ActionEvent event) throws IOException {
        String email = emailTextField.getText().trim();
        String password = EncryptionMD5.encrypt(passwordPField.getText());
        if(password.equals(EncryptionMD5.encrypt("1")) && email.equals("admin")){
            FXMLLoader loader = new FXMLLoader(Main.class.getResource("admin.fxml"));
            root = loader.load();
            stage = (Stage)((Node)event.getSource()).getScene().getWindow();
            scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        }else {
            List<Teacher> teachers = SQLConnection.getAllTeachers();
            for (Teacher teacher : teachers) {
                if (teacher.getPassword().equals(password) && teacher.getEmail().equals(email)) {
                    FXMLLoader loader = new FXMLLoader(Main.class.getResource("logined-page.fxml"));
                    root = loader.load();
                    stage = (Stage)((Node)event.getSource()).getScene().getWindow();
                    scene = new Scene(root);
                    stage.setScene(scene);
                    LoginedExample controller =  loader.getController();
                    controller.welcome(teacher);
                    stage.show();
                    break;
                }
            }
            List<Student> students = SQLConnection.getAllStudents();
            for (Student student : students) {
                if (student.getPassword().equals(password) && student.getEmail().equals(email)) {
                    FXMLLoader loader = new FXMLLoader(Main.class.getResource("logined-page.fxml"));
                    root = loader.load();
                    stage = (Stage)((Node)event.getSource()).getScene().getWindow();
                    scene = new Scene(root);
                    stage.setScene(scene);
                    LoginedExample controller =  loader.getController();
                    controller.welcome(student);
                    stage.show();
                    break;
                }
            }
        }
        statusLabel.setVisible(true);
    }
}
