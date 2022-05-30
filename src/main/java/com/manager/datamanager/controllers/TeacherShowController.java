package com.manager.datamanager.controllers;

import com.manager.datamanager.Main;
import com.manager.datamanager.SQLConnection;
import entities.Teacher;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

public class TeacherShowController {
    private Parent root;
    private Stage stage;
    private Scene scene;


    @FXML
    private TextField nameTextField = new TextField(teachers.get(0).getName());
    @FXML
    private TextField lastnameTextField;
    @FXML
    private TextField emailTextField;
    @FXML
    private TextField genderTextField;
    @FXML
    private TextField ageTextField;
    @FXML
    private TextField headOfTextField;
    @FXML
    private TextArea subjectTextArea;
    @FXML
    private Label teacherNumber;

    private static List<Teacher> teachers = SQLConnection.getAllTeachers();
    private int current = -1;


    public void adminChangeTeacherBack(ActionEvent event) throws IOException {
        root = FXMLLoader.load(Main.class.getResource("admin.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public void showNextTeacher(ActionEvent event){
        showNextTeacher(true);

    }
    public void showPrevTeacher(ActionEvent event){
        showNextTeacher(false);

    }
    public void keyDefiner(KeyEvent keyEvent){
        if(keyEvent.getCode() == KeyCode.RIGHT){
            showNextTeacher(true);
        }
        else if(keyEvent.getCode() == KeyCode.LEFT){
            showNextTeacher(false);
        }
    }

    private void showNextTeacher(boolean direction){
        shiftCurrent(direction);
        Teacher currentTeacher =  teachers.get(current);
        teacherNumber.setText(Integer.toString(current+1));
        nameTextField.setText(currentTeacher.getName());
        lastnameTextField.setText(currentTeacher.getLastname());
        emailTextField.setText(currentTeacher.getEmail());
        headOfTextField.setText(currentTeacher.getHeadOf());
        genderTextField.setText(currentTeacher.getGender());
        ageTextField.setText(Integer.toString(currentTeacher.getAge()));
        subjectTextArea.setText(currentTeacher.getSubject().stream().collect(Collectors.joining("\n")));

    }
    private void shiftCurrent(boolean direction){
        if(direction) {
            current = (current == teachers.size() - 1) ? 0 : ++current;
        } else {
            current = (current == 0) ? teachers.size()-1 : --current;
        }
    }

    public void deleteTeacher(ActionEvent event){
        if(current >=0) {
            SQLConnection.deleteTeacher(teachers.get(current).getName(), teachers.get(current).getLastname());
            teachers.remove(current);
            showNextTeacher(true);
        }
    }

}
