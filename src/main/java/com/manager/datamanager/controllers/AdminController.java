package com.manager.datamanager.controllers;

import com.manager.datamanager.Main;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class AdminController {
    private Stage stage;
    private Scene scene;
    private Parent root;


    public void adminChangeTeacher(ActionEvent event) throws IOException {
        root = FXMLLoader.load(Main.class.getResource("change-teacher.fxml"));
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



    public void exit(ActionEvent event){
        System.out.println("Вихід");
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        stage.close();
    }




}
