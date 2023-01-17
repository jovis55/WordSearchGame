package com.example.wordsearchgame1.controller;


import com.example.wordsearchgame1.MainGame;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

/**
 *
 * @author Diego Alejandro P., Jhoan Esteban S., Johana Paola P., Juan Samuel A.
 */
public class SceneController {

    private Stage stage;
    private Scene scene;
    private Parent root;
    private String soup = "";



    public void showThemes(ActionEvent event) throws IOException{ //Gestor de pantalla de seleccion de temas
        FXMLLoader loader = new FXMLLoader(MainGame.class.getResource("soupType.fxml"));
        root= loader.load();
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();

    }
    @FXML
    public void showSoupFruit(ActionEvent event) throws IOException{ //Gestor de sopa de Frutas
        soup = "frutas";

        FXMLLoader loader = new FXMLLoader(MainGame.class.getResource("soup.fxml"));
        root= loader.load();
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        SoupsController controllerTypesSoups = loader.getController();
        controllerTypesSoups.setGrid(soup);
        stage.setScene(scene);
        stage.show();

    }
    @FXML
    public void showSoupAnimal(ActionEvent event) throws IOException{ //Gestor de sopa de Animales
        soup = "animales";
        FXMLLoader loader = new FXMLLoader(MainGame.class.getResource("soup.fxml"));
        root= loader.load();
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        SoupsController controllerTypesSoups = loader.getController();
        controllerTypesSoups.setGrid(soup);
        stage.setScene(scene);
        stage.show();

    }
    @FXML
    public void showSoupPaises(ActionEvent event) throws IOException{ //Gestor de sopa de Paises
        soup = "paises";
        FXMLLoader loader = new FXMLLoader(MainGame.class.getResource("soup.fxml"));
        root= loader.load();
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        SoupsController controllerTypesSoups = loader.getController();
        controllerTypesSoups.setGrid(soup);
        stage.setScene(scene);
        stage.show();

    }
    @FXML
    public void showIntro(ActionEvent event) throws IOException{ //Gestor de portada
        FXMLLoader loader = new FXMLLoader(MainGame.class.getResource("introGame.fxml"));
        root= loader.load();
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();

    }
    @FXML
    public void showInfo(ActionEvent event) throws IOException{ //Gestor de info
        FXMLLoader loader = new FXMLLoader(MainGame.class.getResource("infoGame.fxml"));
        root= loader.load();
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();

    }
}
