package com.manager.datamanager.controllers;

import com.manager.datamanager.EncryptionMD5;
import com.manager.datamanager.Main;
import com.manager.datamanager.SQLConnection;
import entities.Teacher;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
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
    @FXML
    private Button editTeacherBtn;
    @FXML
    private Button cancelEditBtn;
    @FXML
    private Label subjectStatus;
    @FXML
    private Button newSubjectButton;
    @FXML
    private Button deleteTeacherBtn;
    @FXML
    private Button deleteSubjectBtn;
    @FXML
    private Button confirmEditingBtn;
    @FXML
    private Button nextTeacherBtn;
    @FXML
    private Button prevTeacherBtn;
    @FXML
    private TextField subjectTextField;

    private static List<Teacher> teachers = SQLConnection.getAllTeachers();
    private int current = -1;
    private List<String> teacherEditSubject = new ArrayList<>();


    public void adminChangeTeacherBack(ActionEvent event) throws IOException {
        root = FXMLLoader.load(Main.class.getResource("admin.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public void showNextTeacher(){
        showNextTeacher(true);

    }
    public void showPrevTeacher(){
        showNextTeacher(false);

    }
    public void keyDefiner(KeyEvent keyEvent){
        if(keyEvent.isShiftDown()) {
            if (keyEvent.getCode() == KeyCode.RIGHT) {
                showNextTeacher(true);
            } else if (keyEvent.getCode() == KeyCode.LEFT) {
                showNextTeacher(false);
            }
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

    private void swapFieldsMode(boolean editable){
        editTeacherBtn.setVisible(!editable);
        cancelEditBtn.setVisible(editable);
        subjectTextArea.setVisible(!editable);
        newSubjectButton.setVisible(editable);
        deleteSubjectBtn.setVisible(editable);
        subjectStatus.setVisible(editable);
        subjectTextField.setVisible(editable);
        deleteTeacherBtn.setVisible(!editable);
        confirmEditingBtn.setVisible(editable);
        nextTeacherBtn.setVisible(!editable);
        prevTeacherBtn.setVisible(!editable);

        nameTextField.setEditable(editable);
        lastnameTextField.setEditable(editable);
        emailTextField.setEditable(editable);
        genderTextField.setEditable(editable);
        ageTextField.setEditable(editable);
        headOfTextField.setEditable(editable);




    }

    public void editTeacher(){
        swapFieldsMode(true);
        teacherNumber.setText("editing");


        teacherEditSubject.addAll(teachers.get(current).getSubject());

        subjectStatus.setText(Integer.toString(teacherEditSubject.size()));
        Tooltip tooltip = new Tooltip(teachers.get(current).getSubject().stream().collect(Collectors.joining("\n")));
        subjectStatus.setTooltip(tooltip);
    }

    public void cancelEdit(){
        teacherEditSubject.clear();
        swapFieldsMode(false);
        showNextTeacher(true);
        showNextTeacher(false);
    }

    public void subjectHandler(){
        if(!subjectTextField.getText().isEmpty() && !teacherEditSubject.contains(subjectTextField.getText().trim()))
        {
            teacherEditSubject.add(subjectTextField.getText().trim());
            subjectTextField.clear();
            Tooltip tooltip = new Tooltip(teacherEditSubject.stream().collect(Collectors.joining("\n")));
            subjectStatus.setTooltip(tooltip);
            subjectStatus.setText(Integer.toString(teacherEditSubject.size()));
            subjectStatus.setStyle("-fx-text-fill: green;");
            subjectTextField.setStyle("-fx-border-color: null;");
        } else {
            subjectStatus.setText("ERR");
            subjectStatus.setStyle("-fx-text-fill: red;");
            subjectTextField.setStyle("-fx-border-color: red;");
        }


    }

    public void subjectDeleteOne(){

        if( teacherEditSubject.size()>0 && !teacherEditSubject.remove(subjectTextField.getText().trim())){
            teacherEditSubject.remove(teacherEditSubject.size()-1);
        }
        subjectTextField.clear();
        Tooltip tooltip = new Tooltip(teacherEditSubject.stream().collect(Collectors.joining("\n")));
        subjectStatus.setTooltip(tooltip);
        subjectStatus.setText(Integer.toString(teacherEditSubject.size()));
    }

    public void confirmEdit(ActionEvent event) {
        headOfTextField.setText(headOfTextField.getText().trim().isEmpty()? "none":headOfTextField.getText().trim());
        if (dataValidation()){
            Teacher editedTeacher = new Teacher(nameTextField.getText().trim(),
                    lastnameTextField.getText().trim(),
                    emailTextField.getText().trim(),
                    genderTextField.getText().trim().toLowerCase(Locale.ROOT),
                    Integer.parseInt(ageTextField.getText().trim()),
                    "",
                    teacherEditSubject,
                    headOfTextField.getText().trim());
            if(SQLConnection.updateTeacher(editedTeacher,teachers.get(current).getName(),teachers.get(current).getLastname())){
                teachers.add(current,editedTeacher);
                teachers.remove(current+1);
            }
            cancelEdit();
        } else {
            teacherNumber.setText("ERR");
        }

    }
    private boolean dataValidation(){
        boolean pass = true;
        String errorStyle = String.format("-fx-border-color: red; ");
        String successStyle = String.format("-fx-border-color: green; ");
        Tooltip genderTip = new Tooltip("чоловіча | жіноча");

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
        if( teacherEditSubject.isEmpty()){
            pass = false;
        }
        return pass;
    }
}
