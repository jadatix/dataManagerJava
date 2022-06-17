package com.manager.datamanager;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class Main extends Application {
    public static void main(String[] args) {
        launch();
    }

    @Override
    public void start(Stage stage) throws Exception {
       try {
            Parent root = FXMLLoader.load(getClass().getResource("login.fxml"));
            Scene scene = new Scene(root);
            stage.setScene(scene);
            try {
                stage.getIcons().add(new Image(Main.class.getResourceAsStream("/images/icons8-students-64.png")));
            } catch (NullPointerException err){
                System.out.println(err.getMessage());
            }

            stage.show();
        }catch (Exception e){
           System.out.println(e);
           e.printStackTrace();
       }

    }
}
