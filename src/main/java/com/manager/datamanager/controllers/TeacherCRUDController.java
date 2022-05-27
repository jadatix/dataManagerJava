package com.manager.datamanager.controllers;

import com.manager.datamanager.SQLConnection;
import com.manager.datamanager.Main;
import entities.Teacher;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import com.manager.datamanager.EncryptionMD5;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class TeacherCRUDController {

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
    TextField subjectTextField;
    @FXML
    Label subjectStatus;

    private List<String> list = new ArrayList<>();

    public void subjectHandler(){
        if(!subjectTextField.getText().isEmpty() && !list.contains(subjectTextField.getText().trim()))
        {
            list.add(subjectTextField.getText().trim());
            subjectTextField.clear();
        }
        Tooltip tooltip = new Tooltip(list.stream().collect(Collectors.joining("\n")));
        subjectStatus.setTooltip(tooltip);
        subjectStatus.setText(Integer.toString(list.size()));
        if(list.size() > 0){
            subjectStatus.setStyle("-fx-text-fill: green;");
        }
        else {
            subjectStatus.setText("ERR");
            subjectStatus.setStyle("-fx-text-fill: red;");
        }
    }

    public void subjectDeleteOne(){

        if( list.size()>0 && !list.remove(subjectTextField.getText().trim())){
            list.remove(list.size()-1);
        }
        subjectTextField.clear();
        Tooltip tooltip = new Tooltip(list.stream().collect(Collectors.joining("\n")));
        subjectStatus.setTooltip(tooltip);
        subjectStatus.setText(Integer.toString(list.size()));
    }

    public void addNewTeacher(ActionEvent event){
        System.out.println("Додано нового викладача");
        Teacher teacher = new Teacher(nameTextField.getText(),
                lastnameTextField.getText(),
                emailTextField.getText(),
                genderTextField.getText(),
                Integer.parseInt(ageTextField.getText()),
                EncryptionMD5.encrypt(passwordPField.getText()),
                list,
                headOfTextField.getText()
                );
        SQLConnection.addTeacher(teacher);
        nameTextField.clear();
        lastnameTextField.clear();
        emailTextField.clear();
        genderTextField.clear();
        ageTextField.clear();
        passwordPField.clear();
        headOfTextField.clear();
        list.clear();
        Tooltip tooltip = new Tooltip("Викладач успішно доданий");
        headOfTextField.clear();
        subjectStatus.setText("SUCC");
        subjectStatus.setStyle("-fx-text-fill: green;");
        subjectStatus.setTooltip(tooltip);


        //TODO: data validation
    }
    public void adminChangeTeacherBack(ActionEvent event) throws IOException {
        root = FXMLLoader.load(Main.class.getResource("admin.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

}
