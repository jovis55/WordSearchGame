package com.example.wordsearchgame1;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class MainGame extends Application {

    @Override
    public void start(Stage stage) {
        try {

            FXMLLoader loader = new FXMLLoader(MainGame.class.getResource("introGame.fxml"));
            Parent root= loader.load();
            Scene scene = new Scene(root, 1300, 700);
            stage.setScene(scene);
            stage.show();

        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        launch(args);
    }



}