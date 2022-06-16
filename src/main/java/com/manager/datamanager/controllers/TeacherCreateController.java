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
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

public class TeacherCreateController {

    private Stage stage;
    private Scene scene;
    private Parent root;

    @FXML
    Label headerLabel;
    @FXML
    private TextField nameTextField;
    @FXML
    private TextField lastnameTextField;
    @FXML
    private TextField emailTextField;
    @FXML
    private PasswordField passwordPField;
    @FXML
    private TextField genderTextField;
    @FXML
    private TextField ageTextField;
    @FXML
    private TextField headOfTextField;
    @FXML
    private TextField subjectTextField;
    @FXML
    private Label subjectStatus;

    private List<String> list = new ArrayList<>();

    public void subjectHandler(){
        if(!subjectTextField.getText().isEmpty() && !list.contains(subjectTextField.getText().trim()))
        {
            list.add(subjectTextField.getText().trim());
            subjectTextField.clear();
            Tooltip tooltip = new Tooltip(list.stream().collect(Collectors.joining("\n")));
            subjectStatus.setTooltip(tooltip);
            subjectStatus.setText(Integer.toString(list.size()));
            subjectStatus.setStyle("-fx-text-fill: green;");
            subjectTextField.setStyle("-fx-border-color: null;");
        } else {
            subjectStatus.setText("ERR");
            subjectStatus.setStyle("-fx-text-fill: red;");
            subjectTextField.setStyle("-fx-border-color: red;");
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

    public void addNewTeacher(ActionEvent event) throws SQLException {
        headOfTextField.setText(headOfTextField.getText().trim().isEmpty()? "none":headOfTextField.getText().trim());
        if (dataValidation()) {
            System.out.println("Додано нового викладача");
            Teacher teacher = new Teacher(nameTextField.getText().trim(),
                    lastnameTextField.getText().trim(),
                    emailTextField.getText().trim(),
                    genderTextField.getText().trim().toLowerCase(Locale.ROOT),
                    Integer.parseInt(ageTextField.getText().trim()),
                    EncryptionMD5.encrypt(passwordPField.getText()),
                    list,
                    headOfTextField.getText().trim()
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
            headerLabel.setStyle("-fx-text-fill: black");
            headerLabel.setText("Додати викладача");
        } else {
            headerLabel.setStyle("-fx-text-fill: red");
            headerLabel.setText("Помилка валідації");
        }

    }
    public void adminChangeTeacherBack(ActionEvent event) throws IOException {
        root = FXMLLoader.load(Main.class.getResource("admin.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }


    private boolean dataValidation(){
        boolean pass = true;
        String errorStyle = String.format("-fx-border-color: red; ");
        String successStyle = String.format("-fx-border-color: green; ");
        Tooltip genderTip = new Tooltip("чоловіча | жіноча");


        if(SQLConnection.isExists(nameTextField.getText().trim(),lastnameTextField.getText().trim(),"teacher")){
            Alert errorAlert = new Alert(Alert.AlertType.ERROR);
            errorAlert.setHeaderText("Викладач уже існує");
            errorAlert.setContentText("Викладач уже існує в базі");
            errorAlert.showAndWait();
        }

        if(!emailTextField.getText().trim().matches("\\D[A-Za-z.+_-]{1,30}[@]\\D[A-Z.a-z]{1,15}[.]\\D[a-z]{1,4}")){
            emailTextField.setStyle(errorStyle);
            emailTextField.clear();
            pass = false;
        }
        else {
            emailTextField.setStyle("-fx-border-color: null;");
        }
        if(!nameTextField.getText().trim().matches("[А-ЯІЙЮЄЇҐ][а-яійюєїґ]+")){
            nameTextField.setStyle(errorStyle);
            nameTextField.clear();
            pass = false;
        } else {
            nameTextField.setStyle("-fx-border-color: null;");
        }
        if(!lastnameTextField.getText().trim().matches("[А-ЯІЙЮЄЇҐ][а-яійюєїґ]+")){
            lastnameTextField.setStyle(errorStyle);
            lastnameTextField.clear();
            pass = false;
        } else {
            lastnameTextField.setStyle("-fx-border-color: null;");
        }
        if (!genderTextField.getText().trim().toLowerCase(Locale.ROOT).matches("чоловіча|жіноча")){
            genderTextField.setStyle(errorStyle+"-fx-cursor: WAIT;");
            genderTextField.clear();
            genderTextField.setTooltip(genderTip);
            pass = false;
        } else {
            genderTextField.setStyle("-fx-border-color: null;");
        }
        if (!ageTextField.getText().trim().matches("\\d+")){
            ageTextField.setStyle(errorStyle);
            ageTextField.clear();
            pass = false;
        } else {
            ageTextField.setStyle("-fx-border-color: null;");
        }
        if(passwordPField.getText().trim().isEmpty()
                || list.isEmpty()){
            pass = false;
        }
        return pass;
    }

}
