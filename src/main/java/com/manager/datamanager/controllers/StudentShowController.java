package com.manager.datamanager.controllers;

import com.manager.datamanager.Main;
import com.manager.datamanager.SQLConnection;
import entities.Student;
import entities.Teacher;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseButton;
import javafx.stage.Stage;

import java.io.IOException;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class StudentShowController {
    private Parent root;
    private Stage stage;
    private Scene scene;


    @FXML
    private TextField nameTextField;
    @FXML
    private TextField lastnameTextField;
    @FXML
    private TextField emailTextField;
    @FXML
    private TextField genderTextField;
    @FXML
    private TextField ageTextField;
    @FXML
    private TextField groupNumTextField;
    @FXML
    private DatePicker educationStartDatePicker;
    @FXML
    private Label studentNumber;
    @FXML
    private Button editStudentBtn;
    @FXML
    private Button cancelEditBtn;
    @FXML
    private Button deleteStudentBtn;
    @FXML
    private Button confirmEditingBtn;
    @FXML
    private Button nextStudentBtn;
    @FXML
    private Button prevStudentBtn;
    @FXML
    private TextField durationTextField;

    private static List<Student> students = new ArrayList<>();
    private int current = -1;


    public void adminChangeStudentBack(ActionEvent event) throws IOException {
        root = FXMLLoader.load(Main.class.getResource("admin.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public void showNextStudent(){
        showNextStudent(true);

    }
    public void showPrevStudent(){
        showNextStudent(false);

    }
    public void keyDefiner(KeyEvent keyEvent){
        if(keyEvent.isShiftDown()) {
            if (keyEvent.getCode() == KeyCode.RIGHT) {
                showNextStudent(true);
            } else if (keyEvent.getCode() == KeyCode.LEFT) {
                showNextStudent(false);
            }
        }
    }

    private void showNextStudent(boolean direction){
        shiftCurrent(direction);
        Student currentStudent =  students.get(current);
        studentNumber.setText(Integer.toString(current+1) + "/" + Integer.toString(students.size()));
        nameTextField.setText(currentStudent.getName());
        lastnameTextField.setText(currentStudent.getLastname());
        emailTextField.setText(currentStudent.getEmail());
        groupNumTextField.setText(currentStudent.getGroupNum());
        genderTextField.setText(currentStudent.getGender());
        ageTextField.setText(Integer.toString(currentStudent.getAge()));
        educationStartDatePicker.setValue(Instant.ofEpochMilli(currentStudent.getStartEducation().getTime())
                .atZone(ZoneId.systemDefault())
                .toLocalDate());
        durationTextField.setText(Integer.toString(currentStudent.getDurationOfEducation()));
    }
    private void shiftCurrent(boolean direction){
        if(direction) {
            current = (current == students.size() - 1) ? 0 : ++current;
        } else {
            current = (current == 0) ? students.size()-1 : --current;
        }
    }

    public void deleteStudent(ActionEvent event){
        if(students.size() !=1) {
            SQLConnection.delete(students.get(current).getName(), students.get(current).getLastname(),"student");
            students.remove(current);
            current--;
            showNextStudent(true);
        }
    }

    private void swapFieldsMode(boolean editable){
        editStudentBtn.setVisible(!editable);
        cancelEditBtn.setVisible(editable);
        deleteStudentBtn.setVisible(!editable);
        confirmEditingBtn.setVisible(editable);
        nextStudentBtn.setVisible(!editable);
        prevStudentBtn.setVisible(!editable);

        nameTextField.setEditable(editable);
        lastnameTextField.setEditable(editable);
        emailTextField.setEditable(editable);
        genderTextField.setEditable(editable);
        ageTextField.setEditable(editable);
        groupNumTextField.setEditable(editable);
        durationTextField.setEditable(editable);
        educationStartDatePicker.setEditable(editable);



    }

    public void editStudent(){
        swapFieldsMode(true);
        studentNumber.setText("editing");
    }

    public void cancelEdit(){
        swapFieldsMode(false);
        showNextStudent(true);
        showNextStudent(false);
    }

    public void confirmEdit(ActionEvent event) {
        if (dataValidation()){
            LocalDate date = educationStartDatePicker.getValue();
            Student editedStudent = new Student(nameTextField.getText().trim(),
                    lastnameTextField.getText().trim(),
                    emailTextField.getText().trim(),
                    genderTextField.getText().trim().toLowerCase(Locale.ROOT),
                    Integer.parseInt(ageTextField.getText().trim()),
                    "",
                    groupNumTextField.getText().trim(),
                    Integer.parseInt(durationTextField.getText().trim()),
                    Date.from(date.atStartOfDay( ZoneId.systemDefault()).toInstant())
            );
            if(SQLConnection.updateStudent(editedStudent,students.get(current).getName(),students.get(current).getLastname())){
                students.add(current,editedStudent);
                students.remove(current+1);
            }
            cancelEdit();
        } else {
            studentNumber.setText("ERR");
        }

    }
    private boolean dataValidation(){
        boolean pass = true;
        String errorStyle = String.format("-fx-border-color: red; ");
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
        if(!educationStartDatePicker.getEditor().getText().trim().matches("[0-9]{1,2}/[0-9]{1,2}/[1-9][0-9]{3}") || educationStartDatePicker.getEditor().getText().isEmpty())
        {
            pass = false;
                educationStartDatePicker.setStyle(errorStyle);
        } else {
            educationStartDatePicker.setStyle("-fx-border-color: null;");
        }
        return pass;
    }

    @FXML
    private TableView<Student> tableView;
    @FXML
    private TextField searchTextField;
    @FXML
    private TableColumn<Student,String> nameColumn;
    @FXML
    private TableColumn<Student,String> lastnameColumn;
    @FXML
    private TableColumn<Student,Integer> ageColumn;
    @FXML
    private TableColumn<Student,String> startEducationColumn;
    @FXML
    private TableColumn<Student,String> groupNumColumn;
    @FXML
    private TabPane tabPane;

    public void clearSearch(){
        searchTextField.clear();
    }

    public void search(){
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        lastnameColumn.setCellValueFactory(new PropertyValueFactory<>("lastname"));
        ageColumn.setCellValueFactory(new PropertyValueFactory<>("age"));
        startEducationColumn.setCellValueFactory(new PropertyValueFactory<>("startEducation"));
        groupNumColumn.setCellValueFactory(new PropertyValueFactory<>("groupNum"));

        tableView.setItems(searchResult(searchTextField.getText().trim()));
        tableView.setRowFactory(tv -> {
            TableRow<Student> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if (! row.isEmpty() && event.getButton()== MouseButton.PRIMARY
                        && event.getClickCount() == 2) {
                    Student clickedRow = row.getItem();
                    System.out.println(clickedRow);
                    redirectTo(clickedRow);
                }
            });
            return row ;
        });
    }
    public void searchBtn(KeyEvent keyEvent){
        if(keyEvent.getCode() == KeyCode.ENTER){
            search();
        }
    }

    private void redirectTo(Student s){
        current = students.indexOf(s);
        tabPane.getSelectionModel().select(0);
        showNextStudent(true);
        showNextStudent(false);
    }


    private ObservableList<Student> searchResult(String searchRequest){
        List<Student> searchRes = new ArrayList<>();
        for(Student student : students){
            if (student.getName().contains(searchRequest) ||
                    student.getLastname().contains(searchRequest)||
                    Integer.toString(student.getAge()).contains(searchRequest)||
                    student.getStartEducation("yyyy-MM-dd").contains(searchRequest)||
                    student.getGroupNum().contains(searchRequest.toLowerCase())){
                searchRes.add(student);
            }
        }
        return FXCollections.observableArrayList(searchRes);
    }

    public void init(){
        students = SQLConnection.getAllStudents();
    }
}

