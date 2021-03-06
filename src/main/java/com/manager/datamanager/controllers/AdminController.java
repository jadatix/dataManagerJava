package com.manager.datamanager.controllers;

import com.manager.datamanager.Main;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.stage.Stage;

import java.io.IOException;

public class AdminController {
    private Stage stage;
    private Scene scene;
    private Parent root;


    public void adminChangeTeacher(ActionEvent event) throws IOException {
        root = FXMLLoader.load(Main.class.getResource("create-teacher.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public void adminShowTeacher(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(Main.class.getResource("show-teacher.fxml"));
        root = loader.load();
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
        TeacherShowController showController = loader.getController();
        showController.init();
        showController.showNextTeacher();
    }

    public void adminChangeStudent(ActionEvent event) throws IOException {
        root = FXMLLoader.load(Main.class.getResource("create-student.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
        System.out.println("Студентська сцена");
    }



    public void logout(ActionEvent event) throws IOException {
        System.out.println("Вихід");
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Вихід");
        alert.setHeaderText("Ви намагаєтеся вийти");
        alert.setContentText("Впевнені, що хочете вийти?");

        if (alert.showAndWait().get() == ButtonType.OK) {
            root = FXMLLoader.load(Main.class.getResource("login.fxml"));
            stage = (Stage)((Node)event.getSource()).getScene().getWindow();
            scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
            System.out.println("Логаут");
        }
    }


    public void adminShowStudent(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(Main.class.getResource("show-student.fxml"));
        root = loader.load();
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
        StudentShowController showController = loader.getController();
        showController.init();
        showController.showNextStudent();
    }
}
