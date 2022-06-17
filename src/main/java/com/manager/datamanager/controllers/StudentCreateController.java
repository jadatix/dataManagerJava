package com.manager.datamanager.controllers;

import com.manager.datamanager.EncryptionMD5;
import com.manager.datamanager.Main;
import com.manager.datamanager.SQLConnection;
import entities.Student;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.Locale;

public class StudentCreateController {

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
    private TextField groupNumTextField;
    @FXML
    private DatePicker educationStartDatePicker;
    @FXML
    private TextField durationTextField;

    public void addNewStudent(ActionEvent event) {;
        if (dataValidation()) {
            LocalDate date = educationStartDatePicker.getValue();
            String formattedDate = date.format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));
            System.out.println(formattedDate);
            System.out.println("Додано нового студента");
            Student student = new Student(nameTextField.getText().trim(),
                    lastnameTextField.getText().trim(),
                    emailTextField.getText().trim(),
                    genderTextField.getText().trim().toLowerCase(Locale.ROOT),
                    Integer.parseInt(ageTextField.getText().trim()),
                    EncryptionMD5.encrypt(passwordPField.getText()),
                    groupNumTextField.getText().trim(),
                    Integer.parseInt(durationTextField.getText().trim()),
                    Date.from(date.atStartOfDay( ZoneId.systemDefault()).toInstant())
            );
            SQLConnection.addStudent(student);
            nameTextField.clear();
            lastnameTextField.clear();
            emailTextField.clear();
            genderTextField.clear();
            ageTextField.clear();
            passwordPField.clear();
            groupNumTextField.clear();
            educationStartDatePicker.getEditor().clear();
            durationTextField.clear();
            Tooltip tooltip = new Tooltip("Викладач успішно доданий");
            headerLabel.setStyle("-fx-text-fill: black");
            headerLabel.setText("Додати студента");
        } else {
            headerLabel.setStyle("-fx-text-fill: red");
            headerLabel.setText("Помилка валідації");
        }


    }
    private boolean dataValidation(){
        boolean pass = true;
        String errorStyle = String.format("-fx-border-color: red; ");
        Tooltip genderTip = new Tooltip("чоловіча | жіноча");

        if(SQLConnection.isExists(nameTextField.getText().trim(),lastnameTextField.getText().trim(),"student")){
            Alert errorAlert = new Alert(Alert.AlertType.ERROR);
            errorAlert.setHeaderText("Студент уже існує");
            errorAlert.setContentText("Студент уже існує в базі");
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
        if (!groupNumTextField.getText().trim().matches("\\d+")){
            groupNumTextField.setStyle(errorStyle);
            groupNumTextField.clear();
            pass = false;
        } else {
            groupNumTextField.setStyle("-fx-border-color: null;");
        }
        if (!durationTextField.getText().trim().matches("\\d+")){
            durationTextField.setStyle(errorStyle);
            durationTextField.clear();
            pass = false;
        } else {
            durationTextField.setStyle("-fx-border-color: null;");
        }
        if(passwordPField.getText().trim().isEmpty() || !educationStartDatePicker.getEditor().getText().trim().matches("[0-9]{1,2}/[0-9]{1,2}/[1-9][0-9]{3}"))
        {
            pass = false;
            if(!passwordPField.getText().trim().isEmpty()){
                educationStartDatePicker.setStyle(errorStyle);
            }
        } else {
            educationStartDatePicker.setStyle("-fx-border-color: null;");
        }
        return pass;
    }



    public void adminChangeStudentBack(ActionEvent event) {
        try {
            root = FXMLLoader.load(Main.class.getResource("admin.fxml"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        stage = (Stage)educationStartDatePicker.getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
}
