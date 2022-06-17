package com.manager.datamanager.controllers;

import entities.Student;
import entities.Teacher;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;

public class LoginedExample {
    @FXML
    private Label nameLabel;

    public void welcome(Teacher teacher){
        nameLabel.setText(teacher.getName() + " " + teacher.getLastname());
    }
    public void welcome(Student student){
        nameLabel.setText(student.getName() + " " + student.getLastname());
    }

}
